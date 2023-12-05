package be.pietervaneeckhout.adventofcode.adventofcode._2023.day5;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Day5Test {

    @Test
    void getSeedsFromLine() {
        String line = "seeds: 79 14 55 13";

        Set<Day5.SeedRange> result = Day5.getSeedsFromLine(line);

        assertEquals(27, result.size());
        assertTrue(result.containsAll(List.of(new Day5.SeedRange(79, 14), new Day5.SeedRange(55, 13))));
    }

    @Test
    void getTypeMappingFromLine() {
        String line = "seed-to-soil map:";

        Day5.TypeMapping result = Day5.getTypeMappingFromLine(line);

        assertEquals("seed", result.getSourceType());
        assertEquals("soil", result.getTargetType());
        assertEquals(0, result.getRangeModifiers().size());
    }

    @Test
    void getRangeModifierFromLine() {
        String line = "50 98 2";

        Day5.rangeModifier result = Day5.getRangeModifierFromLine(line);

        assertEquals(98L, result.startSource());
        assertEquals(50L, result.startTarget());
        assertEquals(2L, result.length());
    }

    @Test
    void TypeMapping_getTarget() {
        Day5.TypeMapping typeMapping = new Day5.TypeMapping();
        typeMapping.getRangeModifiers().add(new Day5.rangeModifier(50, 98, 2));
        typeMapping.getRangeModifiers().add(new Day5.rangeModifier(52, 50, 48));

        assertEquals(1, typeMapping.getTarget(1));
        assertEquals(49, typeMapping.getTarget(49));
        assertEquals(98, typeMapping.getTarget(50));
        assertEquals(99, typeMapping.getTarget(51));
        assertEquals(50, typeMapping.getTarget(52));
        assertEquals(51, typeMapping.getTarget(53));
        assertEquals(74, typeMapping.getTarget(76));
        assertEquals(100, typeMapping.getTarget(100));
    }

    @Test
    void parseInput() {
        String input = """
                seeds: 79 14 55 13
                                
                seed-to-soil map:
                50 98 2
                52 50 48
                                
                soil-to-fertilizer map:
                0 15 37
                37 52 2
                39 0 15
                                
                fertilizer-to-water map:
                49 53 8
                0 11 42
                42 0 7
                57 7 4
                                
                water-to-light map:
                88 18 7
                18 25 70
                                
                light-to-temperature map:
                45 77 23
                81 45 19
                68 64 13
                                
                temperature-to-humidity map:
                0 69 1
                1 0 69
                                
                humidity-to-location map:
                60 56 37
                56 93 4
                """;

        Day5.Almanac almanac = Day5.parseInput(input);

        List<Day5.MappingStep> expected = almanac.mappingStepsStartingFromSeed(79);
        assertEquals(8, expected.size());
        assertTrue(expected.containsAll(
                List.of(
                        new Day5.MappingStep("seed", 79),
                        new Day5.MappingStep("soil", 81),
                        new Day5.MappingStep("fertilizer", 81),
                        new Day5.MappingStep("water", 81),
                        new Day5.MappingStep("light", 74),
                        new Day5.MappingStep("temperature", 78),
                        new Day5.MappingStep("humidity", 78),
                        new Day5.MappingStep("location", 82)
                )
        ));

        expected = almanac.mappingStepsStartingFromSeed(14);
        assertEquals(8, expected.size());
        assertTrue(expected.containsAll(
                List.of(
                        new Day5.MappingStep("seed", 14),
                        new Day5.MappingStep("soil", 14),
                        new Day5.MappingStep("fertilizer", 53),
                        new Day5.MappingStep("water", 49),
                        new Day5.MappingStep("light", 42),
                        new Day5.MappingStep("temperature", 42),
                        new Day5.MappingStep("humidity", 43),
                        new Day5.MappingStep("location", 43)
                )
        ));

        expected = almanac.mappingStepsStartingFromSeed(55);
        assertEquals(8, expected.size());
        assertTrue(expected.containsAll(
                List.of(
                        new Day5.MappingStep("seed", 55),
                        new Day5.MappingStep("soil", 57),
                        new Day5.MappingStep("fertilizer", 57),
                        new Day5.MappingStep("water", 53),
                        new Day5.MappingStep("light", 46),
                        new Day5.MappingStep("temperature", 82),
                        new Day5.MappingStep("humidity", 82),
                        new Day5.MappingStep("location", 86)
                )
        ));

        expected = almanac.mappingStepsStartingFromSeed(13);
        assertEquals(8, expected.size());
        assertTrue(expected.containsAll(
                List.of(
                        new Day5.MappingStep("seed", 13),
                        new Day5.MappingStep("soil", 13),
                        new Day5.MappingStep("fertilizer", 52),
                        new Day5.MappingStep("water", 41),
                        new Day5.MappingStep("light", 34),
                        new Day5.MappingStep("temperature", 34),
                        new Day5.MappingStep("humidity", 35),
                        new Day5.MappingStep("location", 35)
                )
        ));

        assertEquals(35L, almanac.getClosestLocation());
    }
}