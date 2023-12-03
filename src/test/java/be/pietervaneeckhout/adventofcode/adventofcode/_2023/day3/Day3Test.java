package be.pietervaneeckhout.adventofcode.adventofcode._2023.day3;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day3Test {

    public static Stream<Arguments> testIsValidTestCases() {
        return Stream.of(
                Arguments.of(new Day3.SchematicPart("123", 0, 1), false),
                Arguments.of(new Day3.SchematicPart("123", 0, 2), false),
                Arguments.of(new Day3.SchematicPart("123", 0, 3), false),
                Arguments.of(new Day3.SchematicPart("123", 0, 4), false),
                Arguments.of(new Day3.SchematicPart("123", 0, 5), false),
                Arguments.of(new Day3.SchematicPart("123", 0, 6), false),
                Arguments.of(new Day3.SchematicPart("123", 0, 7), false),

                Arguments.of(new Day3.SchematicPart("123", 1, 1), false),
                Arguments.of(new Day3.SchematicPart("123", 1, 2), true),
                Arguments.of(new Day3.SchematicPart("123", 1, 3), true),
                Arguments.of(new Day3.SchematicPart("123", 1, 4), true),
                Arguments.of(new Day3.SchematicPart("123", 1, 5), true),
                Arguments.of(new Day3.SchematicPart("123", 1, 6), true),
                Arguments.of(new Day3.SchematicPart("123", 1, 7), false),

                Arguments.of(new Day3.SchematicPart("123", 2, 1), false),
                Arguments.of(new Day3.SchematicPart("123", 2, 2), true),
                Arguments.of(new Day3.SchematicPart("123", 2, 3), true),
                Arguments.of(new Day3.SchematicPart("123", 2, 4), true),
                Arguments.of(new Day3.SchematicPart("123", 2, 5), true),
                Arguments.of(new Day3.SchematicPart("123", 2, 6), true),
                Arguments.of(new Day3.SchematicPart("123", 2, 7), false),

                Arguments.of(new Day3.SchematicPart("123", 3, 1), false),
                Arguments.of(new Day3.SchematicPart("123", 3, 2), true),
                Arguments.of(new Day3.SchematicPart("123", 3, 3), true),
                Arguments.of(new Day3.SchematicPart("123", 3, 4), true),
                Arguments.of(new Day3.SchematicPart("123", 3, 5), true),
                Arguments.of(new Day3.SchematicPart("123", 3, 6), true),
                Arguments.of(new Day3.SchematicPart("123", 3, 7), false),

                Arguments.of(new Day3.SchematicPart("123", 4, 1), false),
                Arguments.of(new Day3.SchematicPart("123", 4, 2), false),
                Arguments.of(new Day3.SchematicPart("123", 4, 3), false),
                Arguments.of(new Day3.SchematicPart("123", 4, 4), false),
                Arguments.of(new Day3.SchematicPart("123", 4, 5), false),
                Arguments.of(new Day3.SchematicPart("123", 4, 6), false),
                Arguments.of(new Day3.SchematicPart("123", 4, 7), false)
        );
    }

    @ParameterizedTest
    @MethodSource("testIsValidTestCases")
    void testIsValid(Day3.SchematicPart schematicPart, boolean expected) {
        Set<Day3.SchematicSymbol> symbolSet = new HashSet<>();
        symbolSet.add(new Day3.SchematicSymbol("*", 2, 5));

        assertEquals(expected, Day3.Schematic.isValid(symbolSet, schematicPart));
    }

    @Test
    void testGetPartSum() {
        String input = """
                467..114..
                ...*......
                ..35..633.
                ......#...
                617*......
                .....+.58.
                ..592.....
                ......755.
                ...$.*....
                .664.598..
                """;

        var schematic = new Day3().getSchematic(input);
        int validPartSum = schematic.getValidPartSum();

        assertEquals(4361, validPartSum);
    }

    @Test
    void getEngineTotalGearRatio() {
        String input = """
                467..114..
                ...*......
                ..35..633.
                ......#...
                617*......
                .....+.58.
                ..592.....
                ......755.
                ...$.*....
                .664.598..
                """;

        var schematic = new Day3().getSchematic(input);
        int engineTotalGearRatio = schematic.getEngineTotalGearRatio();

        assertEquals(467835, engineTotalGearRatio);
    }

    @Test
    void getSchematic() {
        String input = """
                467..114..
                ...*......
                ..35..633.
                ......#...
                617*......
                .....+.58.
                ..592.....
                ......755.
                ...$.*....
                .664.598..
                """;

        var schematic = new Day3().getSchematic(input);

        assertEquals(6, schematic.getSymbols().size());
        assertEquals(10, schematic.getParts().size());
    }

    @Test
    void getSchematicSingleRow() {
        String input = "467..114..";

        var schematic = new Day3().getSchematic(input);

        assertEquals(0, schematic.getSymbols().size());
        assertEquals(2, schematic.getParts().size());
    }
}