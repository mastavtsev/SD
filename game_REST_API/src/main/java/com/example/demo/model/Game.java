package com.example.demo.model;

import jakarta.persistence.*;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Data
@Entity
public final class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ElementCollection
    private List<String> playersId;

    private State state;
    private Integer score;

    @ElementCollection()
    private List<String> gameActions;

    private Integer moves;

    public Game() {
        playersId = new ArrayList<>();
        gameActions = new ArrayList<>();
    }

    public void updateState(String action) {
        gameActions.add(action);
        moves += 1;

        if (!Objects.equals(action, "FinalMove")) {
            if (moves == 1) {
                state = State.STARTED;
            } else {
                state = State.IN_PROGRESS;
            }
        } else {
            state = State.ENDED;
        }

    }

    public enum State {
        STARTED,
        IN_PROGRESS,
        ENDED
    }
}
