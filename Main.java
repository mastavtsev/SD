package org.example;

import java.util.Scanner;

/**
 * Основной класс программы
 */
public class Main {

    /**
     * Создаёт объекты типов Game и Menu для очередной игры в рамках
     * одной сессии.
     * Происходит выбор режима игры.
     */
    public static void main(String[] args) {
        Player player1;
        Player player2;

        int bestSessionResult = 0;

        do {
            System.out.println("""
                Select game mode:\s
                1. vs Human\s
                2. vs Light Computer\s
                Type number of mode in console:
                """);

            Scanner scanner = new Scanner(System.in);
            int modeNum;


            try {
                do {
                    System.out.print("Mode: ");
                    modeNum = scanner.nextInt();
                } while (modeNum < 0 || modeNum > 2);
            } catch (Throwable e) {
                System.out.println("\n\u001B[31mIncorrect input!\u001B[0m");
                return;
            }


            Mode mode = null;

            switch (modeNum) {
                case 1 -> mode = Mode.Human;
                case 2 -> mode = Mode.ComputerLight;
            }

            var players = Menu.setPlayers(mode);
            player1 = players.val1;
            player2 = players.val2;

            Game game = new Game(player1, player2);

            game.makeSteps();


            System.out.println("If you want to continue, write \"YES\" in console! Otherwise secession will be finished!");

            if (!scanner.nextLine().equals("YES")) {
                break;
            } else {
                bestSessionResult =  !player1.skyNet ? Math.max(bestSessionResult, player1.positions.size()) : bestSessionResult;
                bestSessionResult =  !player2.skyNet ? Math.max(bestSessionResult, player2.positions.size()) : bestSessionResult;
            }
        } while (true);


        System.out.println("\n\n Best secessions result:   " + bestSessionResult);

    }
}