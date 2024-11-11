import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';

interface Pokemon {
id: number;
name: string;
image: string;
type: string;
abilities: string[];
height: string;
weight: string;
}

const PokemonDetail: React.FC = () => {
const { id } = useParams<{ id: string }>();
const [pokemon, setPokemon] = useState<Pokemon | null>(null);
const [loading, setLoading] = useState(true);
const [error, setError] = useState<string | null>(null);

useEffect(() => {
if (id) {
    axios.get(`http://localhost:8080/pokemons/${id}`)
    .then(response => {
        setPokemon(response.data);
        setLoading(false);
    })
    .catch(err => {
        setError('Error al obtener el Pokémon');
        setLoading(false);
    });
}
}, [id]);

if (loading) {
return <div className="text-center mt-4">Loading...</div>;
}

if (error) {
return <div className="text-center mt-4">{error}</div>;
}

if (!pokemon) {
return <div className="text-center mt-4">Pokemon not found</div>;
}

return (
<div className="p-4 border rounded shadow mt-4 max-w-md mx-auto">
    <h1 className="text-2xl font-bold mb-4 text-center">{pokemon.name}</h1>
    <img src={pokemon.image} alt={pokemon.name} className="w-full h-64 object-contain mb-4 mx-auto" />
    <p><strong>Type:</strong> {pokemon.type}</p>
    <p><strong>Abilities:</strong> {pokemon.abilities.join(', ')}</p>
    <p><strong>Height:</strong> {pokemon.height}</p>
    <p><strong>Weight:</strong> {pokemon.weight}</p>
</div>
);
};

export default PokemonDetail;
