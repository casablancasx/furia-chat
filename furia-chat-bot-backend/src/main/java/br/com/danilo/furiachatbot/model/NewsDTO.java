package br.com.danilo.furiachatbot.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record NewsDTO(
        @JsonProperty("title")
        String title,
        @JsonProperty("source_link")
        String link
) {
}
