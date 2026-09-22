package io.github.kazikw.boisgo.service;

import io.github.kazikw.boisgo.entity.*;
import io.github.kazikw.boisgo.dto.request.CreateReservationRequest;
import io.github.kazikw.boisgo.dto.response.ReservationResponse;
import io.github.kazikw.boisgo.exception.*;
import io.github.kazikw.boisgo.mapper.ReservationMapper;
import io.github.kazikw.boisgo.repository.FacilityRepository;
import io.github.kazikw.boisgo.repository.ReservationParticipantRepository;
import io.github.kazikw.boisgo.repository.ReservationRepository;
import io.github.kazikw.boisgo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

//import java.nio.file.AccessDeniedException;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final FacilityRepository facilityRepository;
    private final UserRepository userRepository;
    private final ReservationMapper reservationMapper;

    // TODO: tymczasowy mock - do wymiany, gdy dojdzie Spring Security
    private static final Long MOCK_USER_ID = 1L;
    private final ReservationParticipantRepository reservationParticipantRepository;

    @Transactional
    public ReservationResponse createReservation(CreateReservationRequest request) {

        // 1. Pobierz Facility po ID - albo rzuć wyjątek
        Facility facility = facilityRepository.findById(request.facilityId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Nie znaleziono boiska o id: " + request.facilityId()));

        // 2. Sprawdź, czy termin jest wolny
        boolean conflict = reservationRepository.existsConflictingReservation(
                request.facilityId(), request.date(), request.startTime());



        if (conflict) {
            throw new ReservationConflict(
                    "Termin " + request.date() + " " + request.startTime() + " jest już zajęty dla tego boiska");
//            throw new IllegalStateException(
//                    "Termin " + request.date() + " " + request.startTime() + " jest już zajęty dla tego boiska");
        }

        // 3. Mock użytkownika (na razie bez Security)
        User owner = userRepository.findById(MOCK_USER_ID)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Nie znaleziono użytkownika testowego o id: " + MOCK_USER_ID));

        // 4. MapStruct: Request -> Encja (bez facility, owner, status - te MapStruct ignoruje)
        Reservation reservation = reservationMapper.toEntity(request);
        reservation.setFacility(facility);
        reservation.setReservationOwner(owner);
        reservation.setStatus(ReservationStatus.PENDING);

        // 5. Zapis w bazie
        Reservation saved = reservationRepository.save(reservation);
        ReservationParticipant reservationParticipant = new ReservationParticipant();
        reservationParticipant.setReservation(saved);
        reservationParticipant.setParticipant(owner);
        reservationParticipantRepository.save(reservationParticipant);
        // 6. MapStruct: Encja -> Response
        return reservationMapper.toResponse(saved);
    }

    public void deleteReservation(Long reservationId) {
        Reservation reservationToDelete = reservationRepository.findById(reservationId).orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono reserwacji!"));
        if (!reservationToDelete.isOwner(MOCK_USER_ID)){
            throw new AccessDeniedException("Tylko własciciel może anulować rezerwację!");
        }
        if (reservationToDelete.isFinished()){
            throw new ReservationAlreadyFinishedException("Rezerwacja już się odbyła!");
        }
                reservationRepository.delete(reservationToDelete);
    }

    public List<ReservationResponse> getReservationsForFacilityByDate(Long facilityId, LocalDate date) {
        List<Reservation> reservationList =  reservationRepository.findByFacilityIdAndDate(facilityId, date);
        return reservationList.stream()
                .map(reservationMapper::toResponse)
                .toList();
    }

    public Page<ReservationResponse> getMyReservations(Pageable pageable) {
        Page <Reservation> reservationPage = reservationRepository.findFutureReservationsByOwnerId(MOCK_USER_ID, pageable);

        return reservationPage.map(reservationMapper::toResponse);
    }

    public Page<ReservationResponse> getJoinableReservationsByTown(Pageable pageable, String city) {
        Page<Reservation> reservationPage = reservationRepository.findJoinableByCity(city, MOCK_USER_ID, pageable);
        return reservationPage.map(reservationMapper::toResponse);

    }

    public Page<ReservationResponse> getParticipantReservation(Pageable pageable) {
        Page<Reservation> reservationPage = reservationRepository.findParticipantSorted(LocalDate.now(), MOCK_USER_ID, pageable);
        return reservationPage.map(reservationMapper::toResponse);
    }
}