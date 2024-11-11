// src/FrontEnd/Components/Types/types.ts
export interface Pokemon {
    id: number;
    name: string;
    height: number;
    weight: number;
    sprites: {
        front_default: string;
    };
    types: {
        type: {
            name: string;
        };
    }[];
    abilities: {
        ability: {
            name: string;
        };
    }[];
}
