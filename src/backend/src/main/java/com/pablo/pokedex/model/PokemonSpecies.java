package com.pablo.pokedex.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PokemonSpecies {

    @JsonProperty("flavor_text_entries")
    private List<FlavorTextEntry> flavorTextEntries;

    @Setter
    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class FlavorTextEntry {
        @JsonProperty("flavor_text")
        private String flavorText;

        private Language language;

        @Setter
        @Getter
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class Language {
            private String name;
        }
    }

    public String getEnglishFlavorText() {
        if (flavorTextEntries != null) {
            return flavorTextEntries.stream()
                    .filter(entry -> "en".equals(entry.getLanguage().getName()))
                    .map(FlavorTextEntry::getFlavorText)
                    .findFirst()
                    .orElse("Description not available");
        }
        return "Description not available";
    }
}
