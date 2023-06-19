package com.example.demo.repository;

import com.example.demo.model.Game;
import org.springframework.data.repository.CrudRepository;

public interface GameRepository extends CrudRepository<Game, String> {
}
