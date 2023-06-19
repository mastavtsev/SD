package com.example.demo.model;

import lombok.Data;

@Data
public record GameStatistic(Game game, Player winingPlayer) {
}
