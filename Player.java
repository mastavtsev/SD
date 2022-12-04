package org.example;

import java.util.HashSet;


/**
 * Класс игрока.
 */
public class Player {
    public HashSet<Position> positions;

    public String name;
    public Boolean skyNet;

    public Player(String name, Boolean skyNet) {
        this.name = name;
        this.skyNet = skyNet;
    }
}
