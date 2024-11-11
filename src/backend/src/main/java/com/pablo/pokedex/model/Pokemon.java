package com.pablo.pokedex.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "Model representing a Pokémon with its attributes including id, name, description, height, weight, types, and abilities.")
public class Pokemon {

    @ApiModelProperty(notes = "Unique identifier for the Pokémon")
    private Long id;

    @ApiModelProperty(notes = "The name of the Pokémon")
    private String name;

    @ApiModelProperty(notes = "The height of the Pokémon in decimeters")
    private int height;

    @ApiModelProperty(notes = "The weight of the Pokémon in hectograms")
    private int weight;

    @ApiModelProperty(notes = "A description of the Pokémon")
    @Getter
    private String description;

    @JsonProperty("sprites")
    @ApiModelProperty(notes = "Sprites of the Pokémon, including front-facing image")
    private Sprites sprites;

    @JsonProperty("types")
    @ApiModelProperty(notes = "List of types associated with the Pokémon (e.g., Fire, Water, etc.)")
    private List<Type> types;

    @JsonProperty("abilities")
    @ApiModelProperty(notes = "List of abilities associated with the Pokémon (e.g., Overgrow, Chlorophyll, etc.)")
    private List<Ability> abilities;

    public String getImageUrl() {
        return sprites != null ? sprites.getFrontDefault() : null;
    }

    @Setter
    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModel(description = "Represents the sprite images of a Pokémon.")
    public static class Sprites {
        @JsonProperty("front_default")
        @ApiModelProperty(notes = "URL of the default front-facing sprite image of the Pokémon")
        private String frontDefault;
    }

    @Setter
    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModel(description = "Represents the types of a Pokémon.")
    public static class Type {
        private TypeDetail type;

        @Setter
        @Getter
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @ApiModel(description = "Details of a Pokémon type (e.g., Fire, Water, etc.)")
        public static class TypeDetail {
            @ApiModelProperty(notes = "The name of the Pokémon type")
            private String name;
        }
    }

    @Setter
    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModel(description = "Represents the abilities of a Pokémon.")
    public static class Ability {
        private AbilityDetail ability;

        @Setter
        @Getter
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @ApiModel(description = "Details of a Pokémon ability (e.g., Overgrow, Chlorophyll, etc.)")
        public static class AbilityDetail {
            @ApiModelProperty(notes = "The name of the Pokémon ability")
            private String name;
        }
    }
}
