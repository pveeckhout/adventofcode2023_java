package be.pietervaneeckhout.adventofcode.adventofcode._2023.day9;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day9Test {

    public static Stream<Arguments> forwardInterpolationTestCases() {
        return Stream.of(
                Arguments.of(
                        List.of(0L, 3L, 6L, 9L, 12L, 15L), 18L
                ),
                Arguments.of(
                        List.of(1L, 3L, 6L, 10L, 15L, 21L), 28L
                ),
                Arguments.of(
                        List.of(10L, 13L, 16L, 21L, 30L, 45L), 68L
                )
        );
    }

    public static Stream<Arguments> backwardInterpolationTestCases() {
        return Stream.of(
                Arguments.of(
                        List.of(0L, 3L, 6L, 9L, 12L, 15L), -3L
                ),
                Arguments.of(
                        List.of(1L, 3L, 6L, 10L, 15L, 21L), 0L
                ),
                Arguments.of(
                        List.of(10L, 13L, 16L, 21L, 30L, 45L), 5L
                )
        );
    }

    @Test
    void testParseLineNegatives() {
        var result = Day9.parseSingleLine("-6 -7 -7 -6 -4 -1 3 8 14 21 29 38 48 59 71 84 98 113 129 146 164");

        assertEquals(21, result.getPointHistory().size());
    }

    @ParameterizedTest
    @MethodSource("forwardInterpolationTestCases")
    void testForwardInterpolation(List<Long> pointHistory, long expected) {
        Day9.OasisPoint oasisPoint = new Day9.OasisPoint();
        oasisPoint.getPointHistory().addAll(pointHistory);

        var result = oasisPoint.extrapolateForwardPointFromHistory();

        assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("backwardInterpolationTestCases")
    void testBackwardInterpolation(List<Long> pointHistory, long expected) {
        Day9.OasisPoint oasisPoint = new Day9.OasisPoint();
        oasisPoint.getPointHistory().addAll(pointHistory);

        var result = oasisPoint.extrapolateBackwardPointFromHistory();

        assertEquals(expected, result);
    }

    @Test
    void testSumOfForwardExtrapolations(){
        Day9.OasisPoint oasisPoint1 = new Day9.OasisPoint();
        oasisPoint1.getPointHistory().addAll(List.of(0L, 3L, 6L, 9L, 12L, 15L));
        Day9.OasisPoint oasisPoint2 = new Day9.OasisPoint();
        oasisPoint2.getPointHistory().addAll(List.of(1L, 3L, 6L, 10L, 15L, 21L));
        Day9.OasisPoint oasisPoint3 = new Day9.OasisPoint();
        oasisPoint3.getPointHistory().addAll(List.of(10L, 13L, 16L, 21L, 30L, 45L));

        Long result = Stream.of(oasisPoint1, oasisPoint2, oasisPoint3)
                .map(Day9.OasisPoint::extrapolateForwardPointFromHistory)
                .reduce(Long::sum)
                .orElseThrow();

        assertEquals(2L, result);
    }
}