package be.pietervaneeckhout.adventofcode.adventofcode._2023.day2;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day2Test {

    private static final String input = """
            Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
            Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
            Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
            Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
            Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
            """;

    private static final Map<String, Integer> config = Map.of(
            "red", 12,
            "green", 13,
            "blue", 14
    );

    @Test
    void getSumOfIdsForValidGames() {
        var idSum = input.lines()
                .map(line -> Day2.parseGameFromLine(line, config))
                .filter(Day2.Game::isValid)
                .map(Day2.Game::getId).mapToInt(Integer::intValue)
                .sum();

        assertEquals(8, idSum);
    }

    @Test
    void isGameValidForLine() {
        input.lines()
                .map(line -> Day2.parseGameFromLine(line, config))
                .map(Day2.Game::isValid)
                .forEach(System.out::println);
    }

    @Test
    void parseGameFromLine() {
        input.lines().forEach(line -> System.out.println(Day2.parseGameFromLine(line, config)));
    }

    @Test
    void getMaxPullsPerColor() {
        input.lines().map(line -> Day2.parseGameFromLine(line, config))
                .map(Day2.Game::getMaxAmountPerColor)
                .forEach(System.out::println);
    }

    @Test
    void getGamePower() {
        input.lines().map(line -> Day2.parseGameFromLine(line, config))
                .map(Day2.Game::getGamePower)
                .forEach(System.out::println);
    }
}