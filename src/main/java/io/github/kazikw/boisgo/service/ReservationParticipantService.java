package io.github.kazikw.boisgo.service;

import io.github.kazikw.boisgo.dto.response.ReservationParticipantResponse;
import io.github.kazikw.boisgo.entity.Reservation;
import io.github.kazikw.boisgo.entity.ReservationParticipant;
import io.github.kazikw.boisgo.entity.User;
import io.github.kazikw.boisgo.exception.*;
import io.github.kazikw.boisgo.mapper.ReservationParticipantMapper;
import io.github.kazikw.boisgo.repository.FriendRelationsRepository;
import io.github.kazikw.boisgo.repository.ReservationParticipantRepository;
import io.github.kazikw.boisgo.repository.ReservationRepository;
import io.github.kazikw.boisgo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationParticipantService {

    private static final Long MOCK_USER_ID = 1L;
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final ReservationParticipantRepository reservationParticipantRepository;
    private final FriendRelationsRepository friendRelationsRepository;
    private final ReservationParticipantMapper reservationParticipantMapper;

   boolean isParticipant(Reservation reservation, User user){
        return reservationParticipantRepository
                .findByReservationAndUser(reservation.getId(), user.getId())
                .isPresent();
    }
    boolean friends(User user, User user1){
       return friendRelationsRepository.friends(user.getId(), user1.getId());
    }
    boolean friendsOrPublic(Reservation reservation, User user){
        if (reservation.isPublic()){
            return  true;
        }
        return (friends(user, reservation.getReservationOwner()));
    }
    boolean isFullBooked(Reservation reservation){
        Long bookedPerson= reservationParticipantRepository.countAllByReservation_Id(reservation.getId());
        return reservation.getCapacity()<=bookedPerson;
    }
//    static boolean finishedReservation(Reservation reservation){
//       if (reservation.getDate().isBefore(LocalDate.now())){
//           return true;
//        };
//       return (reservation.getDate().equals(LocalDate.now()) && reservation.getStartTime().isBefore(LocalTime.now()));
//
//    }
//    todo Uwazac bo mamy mockowego usera w serwisach!
    @Transactional
    public ReservationParticipantResponse createReservationParticipant(Long reservationId){

        Reservation reservation = reservationRepository.findById(reservationId)
                 .orElseThrow(()-> new ResourceNotFoundException("Nie znaleziono rezerwacji o id:" + reservationId));
        User user = userRepository.findById(MOCK_USER_ID)
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono usera o id:" + MOCK_USER_ID));

        if (isParticipant(reservation, user)){
            throw new AlreadyParticipantException("Nie można dołaczyć do rezerwacji będąc jej członkiem!");
            //
        }
        if(!friendsOrPublic(reservation, user)){
            throw new AccessDeniedToReservationException("Rezerwacja nie jest publiczna!");
        }

        if (isFullBooked(reservation)){
            throw new ReservationFullException("Rezerwacja jest już pełna!");
        }
        //ToDO MA STATUS POTWIERZONA
        if (reservation.isFinished()){
            throw new ReservationAlreadyFinishedException("Rezerwacja już się odbyła!");
        }

        //coś jeszcze może dodam
        ReservationParticipant newreservationParticipant = ReservationParticipant.builder().participant(user).reservation(reservation).build();
        //NIE WIEM CO JEST LEPSZE. Builder czy reczne uzycie setterow
//        ReservationParticipant newreservationParticipant = new ReservationParticipant();
//        newreservationParticipant.setParticipant(user);
//        newreservationParticipant.setReservation(reservation);

        ReservationParticipant reservationParticipant = reservationParticipantRepository.save(newreservationParticipant);

        return reservationParticipantMapper.toResponse(reservationParticipant);
    }

    public void deleteReservationParticipant(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).
                orElseThrow(()-> new ResourceNotFoundException("Wybrana rezerwacja nie istnieje!"));

        if (reservation.isOwner(MOCK_USER_ID)){
           throw new OwnerException("Nie możesz usunąć uczestnictwa w rezerwacji będąc jej założycielem. Usuń całą rezerwację lub przezkaż uprawnienia innemu uczestnikowi rezerwacji.");
       }
        ReservationParticipant reservationParticipant = reservationParticipantRepository.findByReservationAndUser(reservationId, MOCK_USER_ID).
                orElseThrow(()-> new ResourceNotFoundException("Nie jesteś uczestnikiem tej rezerwacji!"));
        if (reservation.isFinished()){
           throw new ReservationAlreadyFinishedException("Nie możesz anulować członkowstwa dla rezerwacji z przeszłości!");
       }
       reservationParticipantRepository.delete(reservationParticipant);
   }
}


