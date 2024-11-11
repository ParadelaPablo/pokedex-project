package com.pablo.pokedex.repository;

import com.pablo.pokedex.PokemonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import java.util.Optional;

@Repository
@Api(tags = "Pokemon Repository", description = "Repository for accessing and managing Pokemon entities in the database")
public interface PokeRepository extends JpaRepository<PokemonEntity, Long> {

    @ApiOperation(value = "Find a Pokémon by name (case insensitive)",
            notes = "This method fetches a Pokémon entity from the database by name, ignoring case.",
            response = PokemonEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched Pokémon by name"),
            @ApiResponse(code = 404, message = "Pokémon not found")
    })
    @Query("SELECT p FROM PokemonEntity p WHERE LOWER(p.name) = LOWER(:name)")
    Optional<PokemonEntity> findPokemonByNameIgnoreCase(@Param("name") String name);

    @ApiOperation(value = "Find all Pokémon entities",
            notes = "This method returns a list of all Pokémon entities stored in the database.",
            response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched all Pokémon"),
            @ApiResponse(code = 204, message = "No Pokémon found")
    })
    List<PokemonEntity> findAll();

    @ApiOperation(value = "Find Pokémon entities by name (case insensitive)",
            notes = "Fetch Pokémon entities by name, ignoring case.",
            response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched Pokémon entities by name"),
            @ApiResponse(code = 204, message = "No Pokémon found")
    })
    List<PokemonEntity> findAllByNameIgnoreCase(String name);

    @ApiOperation(value = "Delete Pokémon by name (case insensitive)",
            notes = "Deletes all Pokémon entities with the given name, ignoring case.",
            response = Void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted Pokémon entities"),
            @ApiResponse(code = 404, message = "Pokémon not found")
    })
    void deleteAllByNameIgnoreCase(String name);

    @ApiOperation(value = "Find a Pokémon by ID",
            notes = "Fetches a Pokémon entity from the database by ID.",
            response = PokemonEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched Pokémon by ID"),
            @ApiResponse(code = 404, message = "Pokémon not found")
    })
    Optional<PokemonEntity> findPokemonById(Long id);

    @ApiOperation(value = "Check if Pokémon exists by name",
            notes = "Checks if a Pokémon exists in the database by name, ignoring case.",
            response = Boolean.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully checked if Pokémon exists"),
            @ApiResponse(code = 404, message = "Pokémon not found")
    })
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN TRUE ELSE FALSE END FROM PokemonEntity p WHERE LOWER(p.name) = LOWER(:name)")
    boolean existsByNameIgnoreCase(@Param("name") String name);
}
