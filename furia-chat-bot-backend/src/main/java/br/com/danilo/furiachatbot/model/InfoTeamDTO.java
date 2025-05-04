package br.com.danilo.furiachatbot.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record InfoTeamDTO(
        @JsonProperty("team_logo")
        String teamLogo,
        @JsonProperty("team_name")
        String teamName,
        @JsonProperty("team_img")
        String country,
        @JsonProperty("valve_rank")
        Integer valveRanking,
        @JsonProperty("world_rank")
        Integer worldRanking,
        @JsonProperty("weeks_in_top_30")
        Integer weeksInTop30,
        @JsonProperty("trophies")
        List<TrophyDTO> trophies,
        @JsonProperty("twitter")
        String twitter,
        @JsonProperty("instagram")
        String instagram,
        @JsonProperty("twitch")
        String twitch,
        @JsonProperty("players")
        List<DetailedPlayerDTO> players,
        @JsonProperty("coach")
        CoachInfoDTO coach
) {
}
