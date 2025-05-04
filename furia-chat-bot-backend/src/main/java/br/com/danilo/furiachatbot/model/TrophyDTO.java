package br.com.danilo.furiachatbot.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TrophyDTO(
        @JsonProperty("event_name")
        String eventName,
        @JsonProperty("trophy_img")
        String trophyImage,
        @JsonProperty("hltv_event_link")
        String hltvEventLink
) {}
