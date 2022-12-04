package org.example;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Game {

    public HashSet<Position> freePositions;
    Player player1;
    Player player2;
    Integer steps;

    Stack<HashSet<Position>> player1PrevPositions;
    Stack<HashSet<Position>> player2PrevPositions;
    Stack<HashSet<Position>> freePositionsPrev;

    /**
     * Констркутор класса Game, инициализирует необходиммые поля.
     * @param player1 Игрок1
     * @param player2 Игрок2
     */
    public Game(Player player1, Player player2) {
        startPositions(player1, 1);
        startPositions(player2, 2);

        this.player1 = player1;
        this.player2 = player2;

        player1PrevPositions = new Stack<>();
        player2PrevPositions = new Stack<>();
        freePositionsPrev = new Stack<>();

        steps = 0;
    }

    /**
     * Формирует стратовую позицию в игре. Добавляет данные позиции каждому игроку.
     * @param player   Игрок
     * @param numberOfPlayer  Номер игрока
     */
    private void startPositions(Player player, int numberOfPlayer) {
        player.positions = new HashSet<Position>();

        if (numberOfPlayer == 1) {
            player.positions.add(new Position(3, 4));
            player.positions.add(new Position(4, 3));
        } else {
            player.positions.add(new Position(3, 3));
            player.positions.add(new Position(4, 4));
        }

        freePositions = new HashSet<>();
        startNewTable(freePositions);
    }

    /**
     * Создаётся новая игровая доска. Выделяются свободные позиции.
     * @param freePositions Свободные позиции.
     */
    private void startNewTable(HashSet<Position> freePositions) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (!(i == 3 && (j == 3 || j == 4) || i == 4 && (j == 3 || j == 4))) {
                    freePositions.add(new Position(i, j));
                }
            }
        }
    }

    /**
     * Формирование игровок доски перед печатью в консоль и вывод в консоль.
     * @param possiblePositions Подсказки для возможного хода игрока.
     */
    public void formGameTable(Set<Position> possiblePositions) {
        String leftAlignFormat = "\u001B[32m%d\u001B[0m  |%4s |%4s |%4s |%4s |%4s |%4s |%4s |%4s |%n";

        ArrayList<String> lines = fillLines(leftAlignFormat, possiblePositions);

        printGameTable(lines);
    }


    /**
     * Формирование игровой доски
     * @param leftAlignFormat   Формат для строк.
     * @param possiblePositions Подсказки для возможного хода игрока.
     * @return  Массив строк - содержимое игровой доски.
     */
    private ArrayList<String> fillLines(String leftAlignFormat,
                                        Set<Position> possiblePositions) {

        ArrayList<String> lines = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            String[] lineChars = new String[8];
            for (int j = 0; j < 8; j++) {
                Position position = new Position(i, j);

                // for player1
                if (player1.positions.contains(position))
                    lineChars[j] = "  \u001B[34m0\u001B[0m ";
                else
                    lineChars[j] = "";

                // for player2 - здесь не нужно else потому что пустыми строки становятся на прошлом шаге
                if (player2.positions.contains(position))
                    lineChars[j] = "  \u001B[31m0\u001B[0m ";

                if (possiblePositions != null) {
                    if (possiblePositions.contains(position)) {
                        if (steps % 2 == 0)
                            lineChars[j] = " \u001B[44m   \u001B[0m";
                        else
                            lineChars[j] = " \u001B[41m   \u001B[0m";
                    }
                }

            }

            String line = leftAlignFormat.formatted(i + 1, lineChars[0], lineChars[1],
                    lineChars[2], lineChars[3], lineChars[4], lineChars[5], lineChars[6], lineChars[7]);
            lines.add(line);
        }
        return lines;
    }

    /**
     * Вывод игровой доски в консоль.
     * @param lines Содержимое игровой доски.
     */
    private void printGameTable(ArrayList<String> lines) {
        System.out.println();
        System.out.println("Score:\t\t\t\u001B[34m" + player1.name + ": " + player1.positions.size()
                + "\t\t\u001B[0m\u001B[31m" + player2.name + ": " + player2.positions.size() + "\u001B[0m");
        System.out.println();
        System.out.format("   \u001B[32m|  1  |  2  |  3  |  4  |  5  |  6  |  7  |  8  |%n\u001B[0m");
        System.out.format("\u001B[32m---\u001B[0m+-----+-----+-----+-----+-----+-----+-----+-----+%n");

        for (int i = 0; i < 8; i++) {
            if (i != 0)
                System.out.format("\u001B[32m---\u001B[0m|-----|-----|-----|-----|-----|-----|-----|-----|%n");
            System.out.format(lines.get(i));

        }
        System.out.format("\u001B[32m---\u001B[0m+-----+-----+-----+-----+-----+-----+-----+-----+%n");
    }

    /**
     * Лежит ли позиция на краю доски.
     * @param position Позиция для проверки.
     * @return Вердикт.
     */
    Boolean isEdging(Position position) {
        return (position.i() % 7 == 0 || position.j() % 7 == 0);
    }

    /**
     * Является ли позиция угловой.
     * @param position Позиция для проверки.
     * @return Вердикт.
     */
    Boolean isAngular(Position position) {
        return (position.i() % 7 == 0 && position.j() % 7 == 0);
    }

    /**
     * Оценка замыкаемых позиций.
     * @param position Позиция для оценки.
     * @return Оценка.
     */
    Double rateClosingPosition(Position position) {
        return isEdging(position) ? 2.0 : 1.0;
    }

    /**
     * Оценка занимаемой позиции.
     * @param position Позиция для оценки.
     * @return Оценка.
     */
    Double rateStandingPosition(Position position) {
        return isAngular(position) ? 0.8 : isEdging(position) ? 0.4 : 0;
    }


    /**
     * Обновление значения словаря - замыкаемых позиций для возможной позиции.
     * @param possiblePositions Возможные для хода позиции.
     * @param position          Позиция, которая замыкает.
     * @param closedPos         Замыкаемые позиции.
     * @param countB            Знечение функции R.
     */
    private void updateMapValue(HashMap<Position, Pair<HashSet<Position>, Double>> possiblePositions, Position position,
                                HashSet<Position> closedPos, Double countB) {
        var closedPosPrev = possiblePositions.get(position).val1;
        closedPosPrev.addAll(closedPos);

        var countBPrev = possiblePositions.get(position).val2;
        countBPrev += countB;

        possiblePositions.put(position, new Pair<>(closedPosPrev, countBPrev));
    }

    /**
     * Поиск возможных для замыкания позиция на диагонале.
     * @param nearPositions         Свободные позиции рядом с позициями противника.
     * @param possiblePositions     Возможные для хода противники.
     * @param playerA               Игрок, которые сейчас ходит.
     * @param playerB               Игрок, чьи позиции могут быть щамкнуты.
     * @param ICond                 Условие прохода - по "главной" или пободной диагонале совершается обход.
     */
    private void findOnDiagonal(@NotNull HashSet<Position> nearPositions,
                                HashMap<Position, Pair<HashSet<Position>, Double>> possiblePositions, Player playerA, Player playerB, IConditional ICond) {
        for (var nearPosition : nearPositions) {

            boolean flag_A_Main = false;
            boolean flag_B_Main = false;
            boolean reachedNP = false;
            boolean first_reachedNP = false;

            Double countB = (double) 0;
            HashSet<Position> closedPos = new HashSet<>();


            for (int i = 0; i < 8; ++i) {
                for (int j = 0; j < 8; ++j) {
                    if (ICond.condition(i, j, nearPosition.i(), nearPosition.j())) {
                        Position position = new Position(i, j);

                        if (position.equals(nearPosition)) {
                            reachedNP = true;
                            first_reachedNP = !flag_A_Main;
                        } else if (playerA.positions.contains(position)) {
                            flag_A_Main = !flag_A_Main || !flag_B_Main;
                        } else if (playerB.positions.contains(position) && (reachedNP || flag_A_Main)) {
                            countB += rateClosingPosition(position);
                            closedPos.add(position);
                            flag_B_Main = true;
                        } else {
                            flag_A_Main = false;
                            flag_B_Main = false;
                            reachedNP = false;
                        }

                        if (flag_A_Main && flag_B_Main && reachedNP) {
                            countB += rateStandingPosition(nearPosition);
                            if (possiblePositions.containsKey(nearPosition)) {
                                updateMapValue(possiblePositions, nearPosition, closedPos, countB);
                            } else {
                                possiblePositions.put(nearPosition, new Pair<>(new HashSet<>(closedPos), countB));
                            }
                            flag_A_Main = false;
                            flag_B_Main = false;
                            reachedNP = false;
                            first_reachedNP = false;
                            closedPos.clear();
                        } else if (reachedNP && flag_A_Main) {
                            flag_A_Main = false;
                            if (first_reachedNP) {
                                reachedNP = false;
                                first_reachedNP = false;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Поиск возможных для замыкания позиция на линии - горизонтальной или вертикальной.
     * @param nearPositions         Свободные позиции рядом с позициями противника.
     * @param possiblePositions     Возможные для хода противники.
     * @param playerA               Игрок, которые сейчас ходит.
     * @param playerB               Игрок, чьи позиции могут быть щамкнуты.
     * @param lineType              Условие прохода - по горизонтале или вертекале совершается обход.
     */
    private void findOnLines(@NotNull HashSet<Position> nearPositions,
                             HashMap<Position, Pair<HashSet<Position>, Double>> possiblePositions, Player playerA, Player playerB, int lineType) {

        for (var nearPosition : nearPositions) {

            boolean flag_A_Main = false;
            boolean flag_B_Main = false;
            boolean reachedNP = false;
            boolean first_reachedNP = false;

            Double countB = (double) 0;
            HashSet<Position> closedPos = new HashSet<>();


            Position position;
            for (int i = 0; i < 8; ++i) {

                if (lineType == 0) // Поиск по горизонатали
                    position = new Position(nearPosition.i(), i);
                else               // Поиск по вертекали
                    position = new Position(i, nearPosition.j());

                if (position.equals(nearPosition)) {
                    reachedNP = true;
                    first_reachedNP = !flag_A_Main;
                } else if (playerA.positions.contains(position)) {
                    flag_A_Main = !flag_A_Main || !flag_B_Main;
                } else if (playerB.positions.contains(position) && (reachedNP || flag_A_Main)) {
                    countB += rateClosingPosition(position);
                    closedPos.add(position);
                    flag_B_Main = true;
                } else {
                    flag_A_Main = false;
                    flag_B_Main = false;
                    reachedNP = false;
                }

                if (flag_A_Main && flag_B_Main && reachedNP) {
                    countB += rateStandingPosition(nearPosition);
                    if (possiblePositions.containsKey(nearPosition)) {
                        updateMapValue(possiblePositions, nearPosition, closedPos, countB);
                    } else {
                        possiblePositions.put(nearPosition, new Pair<>(new HashSet<>(closedPos), countB));
                    }
                    flag_A_Main = false;
                    flag_B_Main = false;
                    reachedNP = false;
                    first_reachedNP = false;
                    closedPos.clear();
                } else if (reachedNP && flag_A_Main) {
                    flag_A_Main = false;
                    if (first_reachedNP) {
                        reachedNP = false;
                        first_reachedNP = false;
                    }
                }

            }
        }
    }

    /**
     * Поиск возможных позиций для хода - на линиях и на диагоналях.
     * @param playerA   Игрок, который совершает ход.
     * @param playerB   Игрок, чьи позиции могут быть замкнуты.
     * @return          Возможные для ходв позиции - ключ. Значение - замыкаемые позиции и значении функции R.
     */
    public HashMap<Position, Pair<HashSet<Position>, Double>> findPossiblePositions(Player playerA, Player playerB) {
        HashSet<Position> nearPositions = findNearPositions(playerB);
        HashMap<Position, Pair<HashSet<Position>, Double>> possiblePositions = new HashMap<>();


        IConditional ICondMainDiagonal = (i, j, npI, npJ) -> (j - i == npJ - npI);
        findOnDiagonal(nearPositions, possiblePositions, playerA, playerB, ICondMainDiagonal);

        IConditional ICondSecondaryDiagonal = (i, j, npI, npJ) -> (j + i == npJ + npI);
        findOnDiagonal(nearPositions, possiblePositions, playerA, playerB, ICondSecondaryDiagonal);

        findOnLines(nearPositions, possiblePositions, playerA, playerB, 0);
        findOnLines(nearPositions, possiblePositions, playerA, playerB, 1);


        return possiblePositions;
    }


    /**
     * Поиск позиций, которые лежат возле позиций противника.
     * @param player Противник, позиции вокруг которого просматриваются позиции.
     * @return       Свободные позиции, возле позиций противника.
     */
    public HashSet<Position> findNearPositions(Player player) {
        HashSet<Position> nearPositions = new HashSet<>();

        for (var position : player.positions) {
            for (int k = -1; k <= 1; k++) {
                for (int l = -1; l <= 1; l++) {
                    Position newPosition = new Position(position.i() + k, position.j() + l);
                    if (freePositions.contains(newPosition)) {
                        nearPositions.add(newPosition);
                    }
                }
            }
        }

        return nearPositions;
    }


    /**
     * Проверка соблюдения условия для далььнейшей игры.
     * @return True - если играть можно, false - иначе.
     */
    public Boolean checkConditionToPlay() {

        if (freePositions.isEmpty())
            return false;

        // Первому и второму игрокам некуда ходить
        return !findPossiblePositions(player1, player2).isEmpty() ||
                !findPossiblePositions(player2, player1).isEmpty();
    }


    /**
     * Завершение игры.
     */
    public void finalizeGame() {
        if (player1.positions.size() > player2.positions.size()) {
            System.out.println("The game is over! The winner is " + player1.name);
        } else if (player1.positions.size() < player2.positions.size()) {
            System.out.println("The game is over! The winner is " + player2.name);
        } else {
            System.out.println("The game is over! And it's a draw between players " +
                    player1.name + " and " + player2.name);
        }
    }


    /**
     * Вывод вохможных для хода позиций в консоль.
     * @param possiblePositions Возможные для хода позиции.
     */
    private void printPossiblePosition(Set<Position> possiblePositions) {
        if (possiblePositions != null) {
            System.out.println("\nPossible positions:");
            if (steps % 2 == 0)
                System.out.print("\u001B[34m");
            else
                System.out.print("\u001B[31m");

            for (var position : possiblePositions) {
                System.out.println((position.i() + 1) + "  " + (position.j() + 1));
            }
            System.out.print("\u001B[0m");
        }
    }

    /**
     * Считывание позиции для хода из консоли.
     * @param playerA   Игрок, который совершает ход.
     * @param playerB   Игрок, чьи позиции могут быть замкнуты.
     * @return          Позиция, на которую ходит игрок и замыкаемые позиции противника.
     */
    private Pair<Position, HashSet<Position>> getPositionFromConsole(Player playerA, Player playerB) {

        Position position = null;
        HashMap<Position, Pair<HashSet<Position>, Double>> possiblePositions = findPossiblePositions(playerA, playerB);

        if (!possiblePositions.isEmpty()) {
            formGameTable(possiblePositions.keySet());
            printPossiblePosition(possiblePositions.keySet());

            do {
                System.out.println("""
                        Pick your position or make a "step back"! (Step back is only available when playing with computer)
                        Possible ones are highlighted. Input i and j indexes in console.\s
                        You should write two numbers in one line in case to input a position\s
                        or write "step back" to make a return to previous step.""");

                System.out.print("Position: ");
                Scanner scanner = new Scanner(System.in);

                String str = scanner.nextLine();

                if (str.equals("step back")) {
                    takeStepBack();
                    return null;
                } else {
                    try {
                        String[] strNums = str.split(" ");
                        int i = Integer.parseInt(strNums[0]) - 1;
                        int j = Integer.parseInt(strNums[1]) - 1;
                        position = new Position(i, j);
                    } catch (Throwable t) {
                        System.out.println("\n\u001B[31mIncorrect input!\u001B[0m");
                        return null;
                    }

                }

            } while (!possiblePositions.containsKey(position));

        } else {
            System.out.println("Player " + playerA.name + " skips a turn! There are no possible positions to move.");
        }

        var closedPositions = possiblePositions.get(position).val1;

        return new Pair<>(position, closedPositions);
    }


    /**
     * Выбор позиции на основе значения функции R.
     * @param playerA   Игрок, который совершает ход.
     * @param playerB   Игрок, чьи позиции могут быть замкнуты.
     * @return          Позиция, на которую ходит игрок и замыкаемые позиции противника.
     */
    private Pair<Position, HashSet<Position>> calculatePositionLight(Player playerA, Player playerB) {
        Position maxPosition = null;
        double R_x_y_value = 0.0;
        var possiblePositions = findPossiblePositions(playerA, playerB);
        HashSet<Position> closedPositions = null;

        if (!possiblePositions.isEmpty()) {
            formGameTable(null);

            for (Map.Entry<Position, Pair<HashSet<Position>, Double>> position : possiblePositions.entrySet()) {
                if (position.getValue().val2 >= 1 && position.getValue().val2 > R_x_y_value) {
                    R_x_y_value = position.getValue().val2;
                    maxPosition = position.getKey();
                    closedPositions = position.getValue().val1;
                }
            }
        } else {
            System.out.println("Player " + playerA.name + " skips a turn! There are no possible positions to move.");
        }

        return new Pair<>(maxPosition, closedPositions);
    }


    /**
     * Замыкание позиций противника.
     * @param playerA   Игррок, которые замыкает позиции.
     * @param playerB   Игрок, чьи позиции замыкаются.
     * @param closedPositions   Замыкаемые позиции.
     */
    private void closePositions(Player playerA, Player playerB, HashSet<Position> closedPositions) {
        for (var position : closedPositions) {
            playerA.positions.add(position);
            playerB.positions.remove(position);
        }
    }


    /**
     * Совершение очередного хода в зависимости от ипа игрока.
     * @param playerA Игрок, которые совершает ход.
     * @param playerB Игрок, чьи позиции могут быть замкнуты.
     */
    public void getStepFromPlayer(Player playerA, Player playerB) {
        Pair<Position, HashSet<Position>> record;

        if (!playerA.skyNet)
            record = getPositionFromConsole(playerA, playerB);
        else
            record = calculatePositionLight(playerA, playerB);

        if (record != null) {

            player1PrevPositions.push(new HashSet<>(player1.positions));
            player2PrevPositions.push(new HashSet<>(player2.positions));
            freePositionsPrev.push(new HashSet<>(freePositions));

            playerA.positions.add(record.val1);
            if (playerA.skyNet) {
                System.out.println("\u001B[31mComputer's step: " + (record.val1.i() + 1) + " " + (record.val1.j() + 1) + "\u001B[0m");
            }
            freePositions.remove(record.val1);
            closePositions(playerA, playerB, record.val2);
        } else {
            steps--;
        }
    }

    /**
     * Совершает шаг назад.
     */
    public void takeStepBack() {

        if (!player1.skyNet && !player2.skyNet) {
            System.out.println("\u001B[31m\nCan not make a step back! \u001B[0m");
        } else {
            player1PrevPositions.pop();
            player1.positions = player1PrevPositions.lastElement();
            player1PrevPositions.pop();

            player2PrevPositions.pop();
            player2.positions = player2PrevPositions.lastElement();
            player2PrevPositions.pop();

            freePositionsPrev.pop();
            freePositions = freePositionsPrev.lastElement();
            freePositionsPrev.pop();

        }
    }

    /**
     * Совершает очередной ход в игре и заканчивает её при невыполнении услоовия продолжения.
     */
    public void makeSteps() {

        while (checkConditionToPlay()) {
            if (steps % 2 == 0) {
                getStepFromPlayer(player1, player2);
            } else {
                getStepFromPlayer(player2, player1);
            }

            steps++;
            System.out.println("\n \n \n");
        }

        finalizeGame();
    }
}
