import React, { useState } from 'react';
import { Pokemon } from './Types/types';
import axios from 'axios';

interface SearchBarProps {
onSearchResult: (results: Pokemon[]) => void;
searchRef: React.RefObject<HTMLInputElement>; 
}

const SearchBar: React.FC<SearchBarProps> = ({ onSearchResult, searchRef }) => {
const [query, setQuery] = useState('');

const handleSearch = async () => {
if (query.trim() !== '') {
    try {
    const response = await axios.get<Pokemon>(`http://localhost:8080/pokemon/${query.toLowerCase()}`);
    onSearchResult([response.data]);
    } catch (error) {
    console.error('Error al buscar el Pokémon:', error);
    onSearchResult([]); 
    }
}
};

return (
<div className="flex space-x-2 mt-4 justify-center">
    <input
    ref={searchRef} 
    type="text"
    placeholder="Search Pokémon..."
    value={query}
    onChange={(e) => setQuery(e.target.value)}
    className="border border-gray-300 p-2 rounded"
    />
    <button onClick={handleSearch} className="bg-blue-500 text-white p-2 rounded hover:bg-blue-700">
    OK
    </button>
</div>
);
};

export default SearchBar;
