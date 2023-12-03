package be.pietervaneeckhout.adventofcode.adventofcode._2023.day1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day1Test {

    public static Stream<Arguments> getCalibrationValueForLineTestCases() {
        return Stream.of(
                Arguments.of("1abc2", 12),
                Arguments.of("pqr3stu8vwx", 38),
                Arguments.of("a1b2c3d4e5f", 15),
                Arguments.of("treb7uchet", 77),
                Arguments.of("two1nine", 29),
                Arguments.of("eightwothree", 83),
                Arguments.of("abcone2threexyz", 13),
                Arguments.of("xtwone3four", 24),
                Arguments.of("4nineeightseven2", 42),
                Arguments.of("zoneight234", 14),
                Arguments.of("7pqrstsixteen", 76)
        );
    }

    public static Stream<Arguments> getCalibrationValueForDocumentTestCases() {
        return Stream.of(
                Arguments.of("""
                                1abc2
                                pqr3stu8vwx
                                a1b2c3d4e5f
                                treb7uchet
                                """
                        , 142),
                Arguments.of("""        
                        two1nine
                        eightwothree
                        abcone2threexyz
                        xtwone3four
                        4nineeightseven2
                        zoneight234
                        7pqrstsixteen
                        """, 281)

        );
    }

    @ParameterizedTest
    @MethodSource("getCalibrationValueForLineTestCases")
    public void testGetCalibrationValueForLine(String input, int expected) {
        Assertions.assertEquals(expected, new Day1().getCalibrationValueForLine(input));
    }

    @ParameterizedTest
    @MethodSource("getCalibrationValueForDocumentTestCases")
    public void testGetCalibrationValueForDocument(String input, int expected) {
        assertEquals(expected, new Day1().getCalibrationValueForDocument(input));
    }
}