package org.example;

import java.util.Scanner;

public class Menu {

    /**
     * Создаёт игроков в зависимости от режима игры.
     * @param mode Режим игры
     * @return Пару player1 и player2
     */
    public static Pair<Player, Player> setPlayers(Mode mode) {
        Player player1;
        Player player2;
        String name1 = getNameFromConsole("Player1, please, enter your name!");
        String name2;

        boolean skyNet;

        if (mode == Mode.Human) {
            name2 = getNameFromConsole("Player2, please, enter your name!");
            skyNet = false;
        } else {
            name2 = "Computer";
            skyNet = true;
        }

        player1 = new Player(name1, false);
        player2 = new Player(name2, skyNet);
        return new Pair<>(player1, player2);
    }

    /**
     * Считываниет имя игрока из консоли.
     * @param phrase Фраза перед вводом имени.
     * @return Имя
     */
    private static String getNameFromConsole(String phrase) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(phrase);
        System.out.print("Name:  ");

        return scanner.nextLine();
    }
}
