import React from 'react';
import { Pokemon } from './Types/types';

interface PokemonListProps {
pokemons: Pokemon[];
onAddToFavorites: (pokemon: Pokemon) => void;
expandedPokemon: number | null;
setExpandedPokemon: React.Dispatch<React.SetStateAction<number | null>>;
}

const PokemonList: React.FC<PokemonListProps> = ({ 
pokemons, 
onAddToFavorites, 
expandedPokemon, 
setExpandedPokemon 
}) => {

const toggleExpand = (id: number) => {
setExpandedPokemon(expandedPokemon === id ? null : id);
};

const closeCard = () => {
setExpandedPokemon(null); // Cierra la tarjeta actual
};

return (
<div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4 mt-4">
    {pokemons.map((pokemon) => (
    <div
        key={pokemon.id}
        className="pokemon-card p-4 border rounded shadow hover:shadow-lg transition cursor-pointer relative"
        onClick={() => toggleExpand(pokemon.id)}
    >
        <h3 className="text-lg font-bold mb-2">
        {pokemon.name.charAt(0).toUpperCase() + pokemon.name.slice(1)}
        </h3>
        <img
        src={pokemon.sprites?.front_default}
        alt={pokemon.name}
        className="w-full h-32 object-contain"
        />
        {expandedPokemon === pokemon.id && (
        <div className="mt-4">
            {/* Agregar el botón para cerrar la card */}
            <button 
            onClick={closeCard}
            className="absolute top-2 right-2 bg-red-500 text-white p-2 rounded-full hover:bg-red-700"
            >
            X
            </button>
            <p><strong>Height:</strong> {pokemon.height}</p>
            <p><strong>Weight:</strong> {pokemon.weight}</p>
            {pokemon.types ? (
            <p><strong>Types:</strong> {pokemon.types.map(t => t.type.name).join(', ')}</p>
            ) : (
            <p><strong>Types:</strong> N/A</p>
            )}
            {pokemon.abilities ? (
            <p><strong>Abilities:</strong> {pokemon.abilities.map(a => a.ability.name).join(', ')}</p>
            ) : (
            <p><strong>Abilities:</strong> N/A</p>
            )}
        </div>
        )}
        <button
        onClick={(e) => {
            e.stopPropagation(); // Evita que el clic también expanda la tarjeta
            onAddToFavorites(pokemon);
        }}
        className="mt-4 bg-yellow-500 text-white p-2 rounded hover:bg-yellow-700"
        >
        Agregar a favoritos
        </button>
    </div>
    ))}
</div>
);
};

export default PokemonList;
