import React, { useState } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import HomePage from './FrontEnd/Components/HomePage';
import FavoritesPage from './FrontEnd/Components/FavoritesPage';
import Navbar from './FrontEnd/Components/Navbar';
import { Pokemon } from './FrontEnd/Components/Types/types';

const App: React.FC = () => {
  const [favorites, setFavorites] = useState<Pokemon[]>([]);

  return (
    <Router>
      <Navbar />
      <Routes>

        <Route
          path="/"
          element={<HomePage favorites={favorites} setFavorites={setFavorites} />} 
        />

        <Route
          path="/favorites"
          element={<FavoritesPage favorites={favorites} setFavorites={setFavorites} />}
        />
      </Routes>
    </Router>
  );
};

export default App;
