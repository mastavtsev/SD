package com.example.demo.controller;

import com.example.demo.dto.ArrayListRequestDTO;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Game;
import com.example.demo.repository.GameRepository;
import com.example.demo.repository.PlayerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/games")
public class GameController {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerRepository userRepository;

    public GameController(GameRepository gameRepository, PlayerRepository userRepository) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/{id}")
    public Optional<Game> getGame(@PathVariable String id) {
        return gameRepository.findById(id);
    }

    @PostMapping
    public ResponseEntity createGame(@RequestBody ArrayListRequestDTO requestDTO) {
        Game game = new Game();

        var playerIds = requestDTO.getItems();

        for (var playerID : playerIds) {
            if (!userRepository.existsById(playerID) && (log.isInfoEnabled())) {
                    log.info("Player with ID {} does not exist", playerID);
                    return ResponseEntity.badRequest().build();
            } else {
                game.getPlayersId().add(playerID);
            }
        }

        gameRepository.save(game);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public Game updateGameState(@PathVariable String id, @RequestBody String action) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Game not found with ID: " + id));

        game.updateState(action);
        return gameRepository.save(game);
    }

}
