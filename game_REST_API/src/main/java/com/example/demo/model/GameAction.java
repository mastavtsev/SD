package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.apache.catalina.User;

import java.util.UUID;

@Data
public class GameAction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private final User doneBy;
    private final Action action;

    public enum Action {
        Move1,
        Move2,
        Move3,
        FinalMove
    }
}
