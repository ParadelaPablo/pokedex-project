package com.pablo.pokedex.controller;

import com.pablo.pokedex.PokemonEntity;
import com.pablo.pokedex.model.Pokemon;
import com.pablo.pokedex.service.PokeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/pokemon")
@Api(value = "Pokedex API", description = "API to manage favorite Pokémon")
public class PokeController {

    private final PokeService service;

    @Autowired
    public PokeController(PokeService service) {
        this.service = service;
    }

    @ApiOperation(value = "Get Pokémon by name", notes = "Returns a Pokémon searched by its name", response = Pokemon.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Pokémon found", response = Pokemon.class),
            @ApiResponse(code = 404, message = "Pokémon not found"),
            @ApiResponse(code = 400, message = "Invalid argument")
    })
    @GetMapping(value = "/{name}", produces = "application/json")
    public ResponseEntity<Pokemon> getPokemonByName(@PathVariable String name) {
        try {
            Pokemon pokemon = service.getPokemonByName(name);
            if (pokemon != null) {
                return ResponseEntity.ok(pokemon);
            } else {
                System.out.println("Pokémon not found: " + name);
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid argument: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (NullPointerException e) {
            System.err.println("Null value encountered: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @ApiOperation(value = "Get Pokémon by ID", notes = "Returns a Pokémon searched by its ID", response = Pokemon.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Pokémon found", response = Pokemon.class),
            @ApiResponse(code = 404, message = "Pokémon not found"),
            @ApiResponse(code = 400, message = "Invalid ID format")
    })
    @GetMapping(value = "/id/{id}", produces = "application/json")
    public ResponseEntity<Pokemon> getPokemonById(@PathVariable Long id) {
        try {
            return service.getPokemonById(id)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> {
                        System.out.println("Pokémon not found with ID: " + id);
                        return ResponseEntity.notFound().build();
                    });
        } catch (NumberFormatException e) {
            System.err.println("Invalid number format for ID: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @ApiOperation(value = "Get all Pokémon", notes = "Returns a list of all Pokémon", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of Pokémon", response = List.class),
            @ApiResponse(code = 500, message = "Internal error")
    })
    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<List<Pokemon>> getAllPokemons() {
        try {
            List<Pokemon> pokemons = service.getAllPokemons();
            return ResponseEntity.ok(pokemons);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @ApiOperation(value = "Get favorite Pokémon", notes = "Returns a list of all favorite Pokémon", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of favorite Pokémon", response = List.class),
            @ApiResponse(code = 204, message = "No favorite Pokémon found"),
            @ApiResponse(code = 500, message = "Internal error")
    })
    @GetMapping("/favorites")
    public ResponseEntity<List<PokemonEntity>> getAllFavouritePokemon() {
        try {
            List<PokemonEntity> favouritePokemons = service.getAllFavouritePokemon();
            if (favouritePokemons.isEmpty()) {
                return ResponseEntity.noContent().build(); // If no favorites, return 204 No Content
            }
            return ResponseEntity.ok(favouritePokemons); // If there are favorites, return 200 OK with data
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Return 500 if error occurs
        }
    }

    @ApiOperation(value = "Save a favorite Pokémon", notes = "Allows saving a Pokémon as a favorite", response = PokemonEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Favorite Pokémon saved", response = PokemonEntity.class),
            @ApiResponse(code = 400, message = "Invalid argument"),
            @ApiResponse(code = 500, message = "Internal error")
    })
    @PostMapping("/favorites")
    public ResponseEntity<PokemonEntity> saveFavouritePokemon(@RequestBody PokemonEntity pokemonEntity) {
        try {
            PokemonEntity savedPokemon = service.saveFavouritePokemon(pokemonEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPokemon);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid argument: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @ApiOperation(value = "Update favorite Pokémon by name", notes = "Allows updating a favorite Pokémon by its name", response = PokemonEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Favorite Pokémon updated", response = PokemonEntity.class),
            @ApiResponse(code = 404, message = "Pokémon not found"),
            @ApiResponse(code = 400, message = "Invalid argument"),
            @ApiResponse(code = 500, message = "Internal error")
    })
    @PutMapping("/favorites/name/{name}")
    public ResponseEntity<PokemonEntity> updateFavouriteByName(@PathVariable String name, @RequestBody PokemonEntity updatedPokemon) {
        try {
            return service.updateFavouriteByName(name, updatedPokemon)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid argument: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @ApiOperation(value = "Delete favorite Pokémon by name", notes = "Allows deleting a favorite Pokémon by its name")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Favorite Pokémon deleted"),
            @ApiResponse(code = 404, message = "Pokémon not found"),
            @ApiResponse(code = 400, message = "Invalid argument"),
            @ApiResponse(code = 500, message = "Internal error")
    })
    @DeleteMapping("/favorites/name/{name}")
    public ResponseEntity<Void> deleteFavouriteByName(@PathVariable String name) {
        try {
            boolean deleted = service.deleteFavouriteByName(name);
            if (deleted) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid argument: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
