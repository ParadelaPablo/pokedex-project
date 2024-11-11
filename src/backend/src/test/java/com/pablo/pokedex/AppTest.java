package com.pablo.pokedex;

import com.pablo.pokedex.repository.PokeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class AppTest {

    @Autowired
    private PokeRepository pokeRepository;

}
