import React, { useEffect, useState } from 'react';
import { Pokemon } from './Types/types';
import PokemonList from './PokemonList';
import axios from 'axios';
const Spinner = () => (
    <div className="flex justify-center items-center h-screen">
    <div className="animate-spin border-t-4 border-blue-500 border-solid w-16 h-16 rounded-full"></div>
</div>
);

interface FavoritesPageProps {
    favorites: Pokemon[];
    setFavorites: React.Dispatch<React.SetStateAction<Pokemon[]>>;
}

const FavoritesPage: React.FC<FavoritesPageProps> = ({ favorites, setFavorites }) => {
const [loading, setLoading] = useState<boolean>(true);

useEffect(() => {
    const fetchFavorites = async () => {
    try {
        const response = await axios.get('http://localhost:8080/pokemon/favorites');
        setFavorites(response.data);  
    } catch (error) {
        console.error('Error al cargar los favoritos:', error);
        setLoading(false);
    }
    };

    fetchFavorites(); 
}, [setFavorites]);

return (
    <div className="container mx-auto p-4">
    <h2 className="text-2xl font-bold mb-4">Pokémon Favoritos</h2>
    {loading ? (
        <Spinner /> 
    ) : favorites.length > 0 ? (
        <PokemonList 
            pokemons={favorites} 
            onAddToFavorites={() => {}} 
            expandedPokemon={null} 
            setExpandedPokemon={() => {}} 
        />
    ) : (
        <p>No favorites pokemon yet.</p>
    )}
    </div>
);
};

export default FavoritesPage;