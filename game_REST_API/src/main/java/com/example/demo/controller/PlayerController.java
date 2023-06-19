package com.example.demo.controller;

import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Game;
import com.example.demo.model.Player;
import com.example.demo.repository.GameRepository;
import com.example.demo.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/players")
public class PlayerController {

    @Autowired
    private PlayerRepository playerRepository;

    public PlayerController(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @GetMapping("/{id}")
    public Player getPlayer(@PathVariable String id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Player not found with ID: " + id));
    }

    @PostMapping
    public Player createPlayer(@RequestBody Player player) {
        return playerRepository.save(player);
    }


}