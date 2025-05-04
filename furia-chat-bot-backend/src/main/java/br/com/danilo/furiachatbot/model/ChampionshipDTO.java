package br.com.danilo.furiachatbot.model;

import br.com.danilo.furiachatbot.enums.StatusEvent;

import java.time.LocalDate;

public record ChampionshipDTO(
        Integer daysUntilEvent,
        String eventName,
        String eventImage,
        String eventLink,
        LocalDate startDate,
        LocalDate endDate,
        StatusEvent status
) {
}
