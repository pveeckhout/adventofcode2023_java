package be.pietervaneeckhout.adventofcode.adventofcode._2023.day7;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day7Test {

    public static Stream<Arguments> calculateTypeRankTestCases() {
        return Stream.of(
                Arguments.of(
                        Map.of('A', 5), new BigInteger("1000000")
                ),
                Arguments.of(
                        Map.of('A', 4, 'J', 1), new BigInteger("1000000")
                ),
                Arguments.of(
                        Map.of('A', 3, 'J', 2), new BigInteger("1000000")
                ),
                Arguments.of(
                        Map.of('A', 2, 'J', 3), new BigInteger("1000000")
                ),
                Arguments.of(
                        Map.of('A', 1, 'J', 4), new BigInteger("1000000")
                ),
                Arguments.of(
                        Map.of('J', 5), new BigInteger("1000000")
                ),
                Arguments.of(
                        Map.of('A', 4, 'K', 1), new BigInteger("100000")
                ),
                Arguments.of(
                        Map.of('A', 3, 'K', 2), new BigInteger("10000")
                ),
                Arguments.of(
                        Map.of('A', 3, 'K', 1, '3', 1), new BigInteger("1000")
                ),
                Arguments.of(
                        Map.of('A', 2, 'K', 2, '3', 1), new BigInteger("100")
                ),
                Arguments.of(
                        Map.of('A', 2, 'K', 1, '3', 1, 'T', 1), new BigInteger("10")
                ),
                Arguments.of(
                        Map.of('A', 1, 'K', 1, '3', 1, 'T', 1, '4', 1), new BigInteger("1")
                )
        );
    }

    public static Stream<Arguments> CalculatedSequenceRankTestCases() {
        return Stream.of(
                Arguments.of(
                        new char[]{'1'}, new BigInteger("101")
                ),
                Arguments.of(
                        new char[]{'1', '2'}, new BigInteger("10102")
                ),
                Arguments.of(
                        new char[]{'1', '2', '3'}, new BigInteger("1010203")
                ),
                Arguments.of(
                        new char[]{'1', '2', '3', '4'}, new BigInteger("101020304")
                ),
                Arguments.of(
                        new char[]{'1', '2', '3', '4', '5'}, new BigInteger("10102030405")
                ),
                Arguments.of(
                        new char[]{'1', '2', '3', '4', '5', '6'}, new BigInteger("1010203040506")
                ),
                Arguments.of(
                        new char[]{'1', '2', '3', '4', '5', '6', '7'}, new BigInteger("101020304050607")
                ),
                Arguments.of(
                        new char[]{'1', '2', '3', '4', '5', '6', '7', '8'}, new BigInteger("10102030405060708")
                ),
                Arguments.of(
                        new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9'}, new BigInteger("1010203040506070809")
                ),
                Arguments.of(
                        new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9', 'T'}, new BigInteger("101020304050607080910")
                ),
                Arguments.of(
                        new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J'}, new BigInteger("10102030405060708091001")
                ),
                Arguments.of(
                        new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q'}, new BigInteger("1010203040506070809100112")
                ),
                Arguments.of(
                        new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K'}, new BigInteger("101020304050607080910011213")
                ),
                Arguments.of(
                        new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A'}, new BigInteger("10102030405060708091001121314")
                )
        );
    }

    @ParameterizedTest
    @MethodSource("calculateTypeRankTestCases")
    void testCalculateTypeRank(Map<Character, Integer> testCounts, BigInteger expected) {
        BigInteger result = Day7.CamelHand.calculateTypeRank(testCounts);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("CalculatedSequenceRankTestCases")
    void testCalculatedSequenceRank(char[] characters, BigInteger expected) {
        BigInteger result = Day7.CamelHand.calculatedSequenceRank(characters);
        assertEquals(expected, result);
    }

    @Test
    void testCalculateCharacterCounts() {
        // Arrange
        var testCharacters = new char[]{'2', '3', '2', '5', '2'};

        // Act
        var result = Day7.CamelHand.calculateCharacterCounts(testCharacters);

        // Assert
        assertEquals(3, result.get('2'));
        assertEquals(1, result.get('5'));
        assertEquals(1, result.get('3'));
    }

    @Test
    void testRanking() {
        List<Day7.CamelHand> camelHands = List.of(
                new Day7.CamelHand(new char[]{'K', 'K', 'K', 'K', 'K'}, "0"), //5
                new Day7.CamelHand(new char[]{'J', '2', 'J', '2', '2'}, "1"), //5
                new Day7.CamelHand(new char[]{'A', 'A', '3', 'J', 'A'}, "2"), //4
                new Day7.CamelHand(new char[]{'A', 'J', '2', 'A', 'A'}, "3"), //4
                new Day7.CamelHand(new char[]{'3', '3', 'J', '2', '2'}, "4"), //f
                new Day7.CamelHand(new char[]{'2', '2', '3', 'J', '3'}, "5"), //f
                new Day7.CamelHand(new char[]{'A', '2', '3', '3', 'J'}, "6"), //t
                new Day7.CamelHand(new char[]{'7', '2', 'J', 'A', 'A'}, "7"), //t
                new Day7.CamelHand(new char[]{'Q', '2', '3', '3', '2'}, "8"), //2p
                new Day7.CamelHand(new char[]{'2', '2', '3', '3', 'Q'}, "9"), //2p
                new Day7.CamelHand(new char[]{'A', 'Q', '1', 'J', '2'}, "10"), //1p
                new Day7.CamelHand(new char[]{'Q', 'Q', '1', '3', '2'}, "11"), //1p
                new Day7.CamelHand(new char[]{'A', 'Q', '1', 'K', '2'}, "12"), //h
                new Day7.CamelHand(new char[]{'4', 'Q', '1', 'K', '2'}, "13") //h
        );

        List<BigInteger> betList = camelHands.stream().sorted()
                .map(Day7.CamelHand::getBet)
                .toList();

        for (int i = 0; i < betList.size(); i++) {
            assertEquals(new BigInteger(i + ""), betList.get(betList.size() - 1 - i));
        }
    }

    @Test
    void parseInput() {
        String input = """
                32T3K 765
                T55J5 684
                KK677 28
                KTJJT 220
                QQQJA 483
                """;

        var result = Day7.parseInput(input);

        assertEquals(5, result.size());
    }

    @Test
    void testGetTotalWinningsPart1() {
        String input = """
                32T3K 765
                T55J5 684
                KK677 28
                KTJJT 220
                QQQJA 483
                """;

        var result = Day7.getTotalWinnings(input);

        assertEquals(new BigInteger("5905"), result);
    }
}
