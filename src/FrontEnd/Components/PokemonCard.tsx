import React from 'react';

interface PokemonCardProps {
pokemon: {
name: string;
imageUrl?: string;
descripcion?: string;
};
}

const PokemonCard: React.FC<PokemonCardProps> = ({ pokemon }) => {
return (
<div className="p-4 bg-white shadow-md rounded-md hover:shadow-lg transition transform hover:scale-105">
    <h2 className="text-lg font-bold capitalize text-center">{pokemon.name}</h2>
    {pokemon.imageUrl ? (
    <img src={pokemon.imageUrl} alt={pokemon.name} className="w-full h-32 object-contain" />
    ) : (
    <p>Imagen no disponible</p>
    )}
    <p className="text-center">{pokemon.descripcion}</p>
</div>
);
};

export default PokemonCard;
