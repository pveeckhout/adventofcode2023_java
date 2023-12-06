package be.pietervaneeckhout.adventofcode.adventofcode._2023.day6;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Day6Test {

    @Test
    void solveInequality() {
        var result = Day6.solveInequality(9, 7, 1);

        assertEquals(4, result.size());
    }

    @Test
    void parseRacesFromInput() {
        String input = """
                Time:      7  15   30
                Distance:  9  40  200
                """;

        var result = Day6.parseRacesFromInput(input);

        assertEquals(3, result.size());
        assertTrue(result.containsAll(List.of(
                new Day6.Race(7, 9),
                new Day6.Race(15, 40),
                new Day6.Race(30, 200)
        )));
    }
}