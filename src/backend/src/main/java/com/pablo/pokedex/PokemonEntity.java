package com.pablo.pokedex;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

@Setter
@Getter
@Entity
@ApiModel(description = "Entity representing a Pokémon, including its name, description, image, types, abilities, height, and weight.")
public class PokemonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "Unique identifier for the Pokémon entity")
    private Long id;

    @Column(name = "pokemon_name", unique = true)
    @ApiModelProperty(notes = "The name of the Pokémon (must be unique)")
    private String name;

    @Column(name = "descripcion")
    @ApiModelProperty(notes = "A description of the Pokémon")
    private String descripcion;

    @Column(name = "image_url")
    @ApiModelProperty(notes = "URL to the image of the Pokémon")
    private String imageUrl;

    @ElementCollection
    @CollectionTable(name = "pokemon_types", joinColumns = @JoinColumn(name = "pokemon_id"))
    @Column(name = "type")
    @ApiModelProperty(notes = "List of types associated with the Pokémon (e.g., Fire, Water, etc.)")
    private List<String> types;

    @ElementCollection
    @CollectionTable(name = "pokemon_abilities", joinColumns = @JoinColumn(name = "pokemon_id"))
    @Column(name = "ability")
    @ApiModelProperty(notes = "List of abilities associated with the Pokémon (e.g., Overgrow, Chlorophyll, etc.)")
    private List<String> abilities;

    @Column(name = "height")
    @ApiModelProperty(notes = "The height of the Pokémon in decimeters")
    private int height;

    @Column(name = "weight")
    @ApiModelProperty(notes = "The weight of the Pokémon in hectograms")
    private int weight;

    public PokemonEntity() {
    }

    public PokemonEntity(String name, String descripcion, String imageUrl, List<String> types, List<String> abilities, int height, int weight) {
        this.name = name;
        this.descripcion = descripcion;
        this.imageUrl = imageUrl;
        this.types = types;
        this.abilities = abilities;
        this.height = height;
        this.weight = weight;
    }
}
