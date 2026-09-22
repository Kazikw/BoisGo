package io.github.kazikw.boisgo.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.ToString;


public record CreateNewFacilityRequest(
        @NotNull
        String address,
    @NotNull
    String city
){
    @Override
    public String toString() {
        return address+ " " +city;
    }//toDO BEZ NADPISANEGO TO STRINGA. TO STRING NIE WYSWIETLAL SIE W LOGACH!
}
