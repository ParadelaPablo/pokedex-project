import React from 'react';
import { Link } from 'react-router-dom';

function Navbar() {
    return (
        <nav className="bg-red-600 text-white p-4 flex justify-between items-center">
            <Link to="/" className="text-2xl font-bold">Pokedex</Link>
            <div className="hidden md:flex space-x-4">
                <Link to="/favorites" className="text-lg">Favoritos</Link>
            </div>
            {/* Menu Hamburguesa para dispositivos pequeños */}
            <div className="md:hidden">
                <button className="text-lg">☰</button>
            </div>
        </nav>
    );
}

export default Navbar;
