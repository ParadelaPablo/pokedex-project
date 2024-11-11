package com.pablo.pokedex.service;

import com.pablo.pokedex.PokemonEntity;
import com.pablo.pokedex.model.Pokemon;
import com.pablo.pokedex.model.PokemonListResponse;
import com.pablo.pokedex.model.PokemonSpecies;
import com.pablo.pokedex.model.SearchHistory;
import com.pablo.pokedex.repository.PokeRepository;
import com.pablo.pokedex.repository.SearchHistoryRepository;
import exception.PokemonNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Api(value = "Pokemon Service", description = "Operations pertaining to Pokémon in the Pokedex")
public class PokeService {

    private static final Logger logger = LoggerFactory.getLogger(PokeService.class);

    private final WebClient webClient;
    private final PokeRepository repo;
    private final SearchHistoryRepository searchHistoryRepo;

    @Autowired
    public PokeService(WebClient.Builder webClientBuilder, PokeRepository repo, SearchHistoryRepository searchHistoryRepo) {
        this.webClient = webClientBuilder.baseUrl("https://pokeapi.co/api/v2").build();
        this.repo = repo;
        this.searchHistoryRepo = searchHistoryRepo;
    }

    @ApiOperation(value = "Get a Pokemon by name", notes = "Fetches a Pokémon by its name from the external API and returns it with its description.")
    @Cacheable(value = "pokemons", key = "#name")
    public Pokemon getPokemonByName(String name) {
        try {
            Pokemon pokemon = webClient.get()
                    .uri("/pokemon/" + name.toLowerCase())
                    .retrieve()
                    .bodyToMono(Pokemon.class)
                    .block();

            if (pokemon != null) {
                String description = webClient.get()
                        .uri("/pokemon-species/" + name.toLowerCase())
                        .retrieve()
                        .bodyToMono(PokemonSpecies.class)
                        .map(PokemonSpecies::getEnglishFlavorText)
                        .block();

                pokemon.setDescription(description);
            }

            return pokemon;
        } catch (WebClientResponseException.NotFound e) {
            logger.error("No se encontró el Pokémon con el nombre: {}", name);
            throw new PokemonNotFoundException("Pokémon no encontrado: " + name);
        } catch (Exception e) {
            logger.error("Error inesperado al buscar el Pokémon: {}", e.getMessage());
            throw e;
        }
    }

    @ApiOperation(value = "Get a Pokemon by ID", notes = "Fetches a Pokémon by its ID from the external API and stores it if not already in the database.")
    @Cacheable(value = "pokemons", key = "#id")
    public Optional<Pokemon> getPokemonById(Long id) {
        SearchHistory searchHistory = new SearchHistory();
        searchHistory.setPokemonId(id);
        searchHistory.setSearchDate(LocalDateTime.now());
        searchHistoryRepo.save(searchHistory);

        Optional<Pokemon> optionalPokemon = webClient.get()
                .uri("/pokemon/" + id)
                .retrieve()
                .onStatus(
                        status -> status.isError(),
                        response -> {
                            logger.error("Error fetching Pokémon data: {}", response.statusCode());
                            return response.createException().flatMap(Mono::error);
                        }
                )
                .bodyToMono(Pokemon.class)
                .blockOptional();
        optionalPokemon.ifPresent(pokemon -> {
            if (!repo.existsById(pokemon.getId())) {
                PokemonEntity pokemonEntity = new PokemonEntity(
                        pokemon.getName(),
                        pokemon.getDescription(),
                        pokemon.getImageUrl(),
                        pokemon.getTypes().stream().map(type -> type.getType().getName()).collect(Collectors.toList()),
                        pokemon.getAbilities().stream().map(ability -> ability.getAbility().getName()).collect(Collectors.toList()),
                        pokemon.getHeight(),
                        pokemon.getWeight()
                );
                pokemonEntity.setId(pokemon.getId());
                repo.save(pokemonEntity);
            }
        });

        return optionalPokemon;
    }

    @ApiOperation(value = "Save a Pokémon as favorite", notes = "Saves a Pokémon as a favorite to the database.")
    public PokemonEntity saveFavouritePokemon(PokemonEntity pokemonEntity) {
        return repo.save(pokemonEntity);
    }

    @ApiOperation(value = "Get all favorite Pokémon", notes = "Fetches all Pokémon saved as favorites in the database.")
    @Cacheable(value = "favorites")  // Caché de los favoritos
    public List<PokemonEntity> getAllFavouritePokemon() {
        List<PokemonEntity> allFavorites = repo.findAll();
        System.out.println("Total de favoritos en la base de datos: " + allFavorites.size());
        if (allFavorites.isEmpty()) {
            System.out.println("No hay Pokémon favoritos en la base de datos");
        }
        return allFavorites;
    }

    @ApiOperation(value = "Update a Pokémon favorite by name", notes = "Updates a Pokémon saved as a favorite in the database based on its name.")
    public Optional<PokemonEntity> updateFavouriteByName(String name, PokemonEntity updatedPokemon) {
        Optional<PokemonEntity> existingPokemon = repo.findPokemonByNameIgnoreCase(name);
        existingPokemon.ifPresent(pokemonToUpdate -> {
            pokemonToUpdate.setName(updatedPokemon.getName());
            pokemonToUpdate.setDescripcion(updatedPokemon.getDescripcion());
            repo.save(pokemonToUpdate);
            logger.info("Updated Pokémon: {} to {}, with description: {}", name, updatedPokemon.getName(), updatedPokemon.getDescripcion());
        });
        return existingPokemon;
    }

    @ApiOperation(value = "Delete a Pokémon favorite by name", notes = "Deletes a Pokémon from the favorites in the database based on its name.")
    public boolean deleteFavouriteByName(String name) {
        try {
            List<PokemonEntity> existingPokemons = repo.findAllByNameIgnoreCase(name);
            if (!existingPokemons.isEmpty()) {
                repo.deleteAll(existingPokemons);
                logger.info("Deleted Pokémon with name: {}", name);
                return true;
            } else {
                logger.warn("No Pokémon found with name: {}", name);
                return false;
            }
        } catch (Exception e) {
            logger.error("Error while deleting Pokémon with name: {}", name, e);
            throw e;
        }
    }

    @ApiOperation(value = "Get all Pokémon", notes = "Fetches a list of Pokémon from the external API and returns them.")
    @Cacheable(value = "pokemons", key = "'all-pokemons'")
    public List<Pokemon> getAllPokemons() {
        try {
            return webClient.get()
                    .uri("/pokemon?limit=50")
                    .retrieve()
                    .bodyToMono(PokemonListResponse.class)
                    .block()
                    .getResults()
                    .stream()
                    .map(summary -> {
                        return getPokemonByName(summary.getName());
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error al obtener la lista de Pokémon: {}", e.getMessage());
            throw e;
        }
    }
}
