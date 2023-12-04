package be.pietervaneeckhout.adventofcode.adventofcode._2023.day4;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day4Test {

    public static Stream<Arguments> testParseLineTestCases() {
        return Stream.of(
                Arguments.of(
                        "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53", new Day4.Card(1, Set.of(41, 48, 83, 86, 17), Set.of(83, 86, 6, 31, 17, 9, 48, 53)),
                        "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19", new Day4.Card(2, Set.of(13, 32, 20, 16, 61), Set.of(61, 30, 68, 82, 17, 32, 24, 19)),
                        "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1", new Day4.Card(3, Set.of(1, 21, 53, 59, 44), Set.of(69, 82, 63, 72, 16, 21, 14, 1)),
                        "Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83", new Day4.Card(4, Set.of(41, 92, 73, 84, 69), Set.of(59, 84, 76, 51, 58, 5, 54, 83)),
                        "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36", new Day4.Card(5, Set.of(87, 83, 26, 28, 32), Set.of(88, 30, 70, 12, 93, 22, 82, 36)),
                        "Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11", new Day4.Card(6, Set.of(31, 18, 13, 56, 72), Set.of(74, 77, 10, 23, 35, 67, 36, 11))
                )
        );
    }

    public static Stream<Arguments> testCardMatchingNumbersTestCases() {
        return Stream.of(
                Arguments.of(
                        "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53", Set.of(83, 86, 17, 48),
                        "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19", Set.of(32, 61),
                        "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1", Set.of(21, 1),
                        "Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83", Set.of(84),
                        "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36", Set.of(),
                        "Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11", Set.of()
                )
        );
    }

    public static Stream<Arguments> testCardPointsTestCases() {
        return Stream.of(
                Arguments.of(
                        "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53", 8,
                        "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19", 2,
                        "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1", 2,
                        "Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83", 1,
                        "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36", 0,
                        "Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11", 0
                )
        );
    }

    @ParameterizedTest
    @MethodSource("testParseLineTestCases")
    void testParseLine(String line, Day4.Card expected) {
        var result = Day4.parseLine(line);

        assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("testCardMatchingNumbersTestCases")
    void testCardMatchingNumbers(String line, Set<Integer> expected) {
        var result = Day4.parseLine(line).getMatchingNumbers();

        assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("testCardPointsTestCases")
    void testCardPoints(String line, int expected) {
        var result = Day4.parseLine(line).getScore();

        assertEquals(expected, result);
    }

    @Test
    void testPileScore() {
        String input = """
                Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
                Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
                Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
                Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
                Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
                Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11
                """;

        var result = Day4.getPileScore(input);

        assertEquals(13, result);
    }
}