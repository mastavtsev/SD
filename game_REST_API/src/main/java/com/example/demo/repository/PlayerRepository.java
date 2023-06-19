package com.example.demo.repository;

import com.example.demo.model.Game;
import com.example.demo.model.Player;
import org.springframework.data.repository.CrudRepository;

public interface PlayerRepository extends CrudRepository<Player, String> {
}
