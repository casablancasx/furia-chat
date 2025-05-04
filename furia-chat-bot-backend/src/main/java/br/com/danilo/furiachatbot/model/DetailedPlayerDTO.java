package br.com.danilo.furiachatbot.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DetailedPlayerDTO(
        @JsonProperty("player_img")
        String playerImage,
        @JsonProperty("nickname")
        String nickname,
        @JsonProperty("nationality")
        String nationality,
        @JsonProperty("status")
        String status,
        @JsonProperty("rating")
        Double rating,
        @JsonProperty("maps_played")
        Integer mapsPlayed
) {
}
