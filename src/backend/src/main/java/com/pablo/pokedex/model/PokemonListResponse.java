package com.pablo.pokedex.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PokemonListResponse {

    @JsonProperty("results")
    private List<PokemonSummary> results;

    @Setter
    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class PokemonSummary {
        private String name;
        private String url;
    }
}
