package br.com.danilo.furiachatbot.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record TransferDTO(
        @JsonProperty("player_img")
        String playerImage,
        @JsonProperty("nickname")
        String nickname,
        //TODO: Change to enum
        @JsonProperty("role")
        String role,
        //TODO: Change to enum
        @JsonProperty("action")
        String action,
        @JsonProperty("from_team")
        String fromTeam,
        @JsonProperty("from_team_img")
        String fromTeamImage,
        @JsonProperty("to_team")
        String toTeam,
        @JsonProperty("to_team_img")
        String toTeamImage,
        @JsonProperty("date")
        LocalDate date
) {
}
