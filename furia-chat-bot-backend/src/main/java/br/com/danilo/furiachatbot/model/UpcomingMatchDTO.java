package br.com.danilo.furiachatbot.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record UpcomingMatchDTO(
        @JsonProperty("team1_logo")
        String team1Logo,
        @JsonProperty("team1_name")
        String team1Name,
        @JsonProperty("team2_logo")
        String team2Logo,
        @JsonProperty("team2_name")
        String team2Name,
        @JsonProperty("match_date")
        LocalDate matchDate,
        @JsonProperty("event_name")
        String eventName
) {
}
