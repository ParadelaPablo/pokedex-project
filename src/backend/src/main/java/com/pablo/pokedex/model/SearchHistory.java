package com.pablo.pokedex.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class SearchHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pokemon_name", nullable = false)
    private String pokemonName;

    @Column(name = "pokemon_id")
    private Long pokemonId;

    @Column(name = "search_date", nullable = false)
    private LocalDateTime searchDate;

    public SearchHistory() {
    }

    public SearchHistory(String pokemonName, Long pokemonId, LocalDateTime searchDate) {
        this.pokemonName = pokemonName;
        this.pokemonId = pokemonId;
        this.searchDate = searchDate;
    }
}
