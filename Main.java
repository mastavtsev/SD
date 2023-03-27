package org.example;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        GameCompany gameCompany = new GameCompany();

        Developer developer1 = new Developer("Andrew", gameCompany);
        Developer developer2 = new Developer("Ivan", gameCompany);

        Gamer gamer1 = new Gamer("Dan", gameCompany);
        Gamer gamer2 = new Gamer("Roma", gameCompany);
        Gamer gamer3 = new Gamer("Alex", gameCompany);

        Journalist journalist = new Journalist("Yuri", gameCompany);

        gameCompany.setUpdate(new News("Title1", "Game1",
                Arrays.asList("Achievement1", "Achievement2", "Achievement3"), "C++"));
    }
}