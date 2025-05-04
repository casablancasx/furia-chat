package br.com.danilo.furiachatbot.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CoachInfoDTO(
        @JsonProperty("coach_img")
        String coachImage,
        @JsonProperty("nickname")
        String nickname,
        @JsonProperty("maps_played")
        Integer mapsPlayed,
        @JsonProperty("trophies_won")
        Integer trophiesWon,
        @JsonProperty("time_on_team")
        String timeOnTeam,
        @JsonProperty("winrate")
        String winrate
) {
}
