import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Pokemon } from './Types/types';
import PokemonList from './PokemonList';
import Pagination from './Pagination';
import SearchBar from './SearchBar';
import { ClipLoader } from 'react-spinners';

interface HomePageProps {
favorites: Pokemon[];
setFavorites: React.Dispatch<React.SetStateAction<Pokemon[]>>;
}

const HomePage: React.FC<HomePageProps> = ({ favorites, setFavorites }) => {
const [allPokemons, setAllPokemons] = useState<Pokemon[]>([]);
const [searchResults, setSearchResults] = useState<Pokemon[]>([]);
const [currentPage, setCurrentPage] = useState(1);
const [loading, setLoading] = useState<boolean>(true);
const [expandedPokemon, setExpandedPokemon] = useState<number | null>(null);
const pokemonsPerPage = 12;

useEffect(() => {
const fetchAllPokemons = async () => {
    try {
    const response = await axios.get<Pokemon[]>('http://localhost:8080/pokemon/all');
    setAllPokemons(response.data);
    setLoading(false);
    } catch (error) {
    console.error('Error al cargar los Pokémon:', error);
    setLoading(false);
    }
};

fetchAllPokemons();
}, []);

const handleAddToFavorites = (pokemon: Pokemon) => {
const pokemonEntity = {
    name: pokemon.name,
    imageUrl: pokemon.sprites.front_default,
    types: pokemon.types.map((type) => type.type.name),
    abilities: pokemon.abilities.map((ability) => ability.ability.name),
    height: pokemon.height,
    weight: pokemon.weight,
};

axios
    .post('http://localhost:8080/pokemon/favorites', pokemonEntity)
    .then((response) => {
    console.log('Pokémon agregado a favoritos:', response.data);
    setFavorites((prevFavorites) => [...prevFavorites, response.data]);
    })
    .catch((error) => {
    console.error('Error al agregar el Pokémon a favoritos:', error);
    });
};

const indexOfLastPokemon = currentPage * pokemonsPerPage;
const indexOfFirstPokemon = indexOfLastPokemon - pokemonsPerPage;
const currentPokemons = searchResults.length > 0 ? searchResults : allPokemons.slice(indexOfFirstPokemon, indexOfLastPokemon);

const handlePageChange = (page: number) => {
setCurrentPage(page);
};

const searchRef = React.createRef<HTMLInputElement>();

return (
<div>
    <SearchBar onSearchResult={setSearchResults} searchRef={searchRef} />

    {loading ? (
    <div className="flex justify-center items-center mt-20">
        <ClipLoader size={50} color="#0000ff" loading={loading} />
    </div>
    ) : (
    <>
        <PokemonList
        pokemons={currentPokemons}
        onAddToFavorites={handleAddToFavorites}
        expandedPokemon={expandedPokemon}
        setExpandedPokemon={setExpandedPokemon}
        />
        {searchResults.length === 0 && allPokemons.length > 0 && (
        <Pagination
            currentPage={currentPage}
            totalPages={Math.ceil(allPokemons.length / pokemonsPerPage)}
            onPageChange={handlePageChange}
        />
        )}
    </>
    )}
</div>
);
};

export default HomePage;
