package be.pietervaneeckhout.adventofcode.adventofcode._2023.day5;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day5 {

    private static final Pattern seedPattern = Pattern.compile("(?<start>\\d+) (?<range>\\d+)");
    private static final String rangePatternString = "(?<startTarget>\\d+) (?<startSource>\\d+) (?<length>\\d+)";
    private static final Pattern rangePattern = Pattern.compile(rangePatternString);
    private static final Pattern mappingTypePattern = Pattern.compile("(?<source>[a-z]+)-to-(?<target>[a-z]+) map:");

    public static void main(String[] args) {
        System.out.printf("lowest location for any seed in the almanac: %d%n", parseInput(Day5Input.input).getClosestLocation());

    }

    public static Almanac parseInput(String input) {
        Almanac almanac = new Almanac();
        TypeMapping currentTypeMapping = null;

        for (String line : input.lines().toList()) {
            if (line.startsWith("seeds:")) {
                almanac.seedRanges.addAll(getSeedsFromLine(line));
                continue;
            }
            if (line.isBlank()) {
                if (Objects.nonNull(currentTypeMapping)) {
                    almanac.typeMappings.put(currentTypeMapping.sourceType(), currentTypeMapping);
                    currentTypeMapping = new TypeMapping();
                }
                continue;
            }

            if (line.contains(" map:")) {
                currentTypeMapping = getTypeMappingFromLine(line);
                continue;
            }

            if (line.matches(rangePatternString) && Objects.nonNull(currentTypeMapping)) {
                rangeModifier rangeModifier = getRangeModifierFromLine(line);
                currentTypeMapping.getRangeModifiers().add(rangeModifier);
            }
        }

        if (Objects.nonNull(currentTypeMapping)) {
            almanac.typeMappings.put(currentTypeMapping.sourceType(), currentTypeMapping);
        }

        return almanac;
    }

    public static TypeMapping getTypeMappingFromLine(String line) {
        Matcher matcher = mappingTypePattern.matcher(line);


        if (matcher.find()) {
            TypeMapping typeMapping = new TypeMapping();
            typeMapping.setSourceType(matcher.group("source"));
            typeMapping.setTargetType(matcher.group("target"));
            return typeMapping;
        }

        throw new IllegalArgumentException("line did not contain TypeMapping init info");
    }

    public static rangeModifier getRangeModifierFromLine(String line) {
        Matcher matcher = rangePattern.matcher(line);
        if (matcher.find()) {
            return new rangeModifier(Long.parseLong(matcher.group("startSource")), Long.parseLong(matcher.group("startTarget")), Long.parseLong(matcher.group("length")));
        }

        throw new IllegalArgumentException("line did not contain range modifier info");
    }

    public static Set<SeedRange> getSeedsFromLine(String line) {
        Matcher matcher = seedPattern.matcher(line);
        Set<SeedRange> seedRanges = new HashSet<>();

        while (matcher.find()) {
            long start = Long.parseLong(matcher.group("start"));
            long range = Long.parseLong(matcher.group("range"));

            seedRanges.add(new SeedRange(start, range));
        }

        return seedRanges;
    }

    public record SeedRange(long start, long range) {

    }

    public record MappingStep(
            String type,
            long identifier
    ) implements Comparable<MappingStep> {
        @Override
        public int compareTo(MappingStep o) {
            return Long.compare(identifier, o.identifier);
        }
    }

    @Getter
    public static final class TypeMapping {
        private final List<rangeModifier> rangeModifiers = new ArrayList<>();
        private String sourceType;
        private String targetType;

        public TypeMapping() {
        }

        public void setSourceType(String sourceType) {
            this.sourceType = sourceType;
        }

        public void setTargetType(String targetType) {
            this.targetType = targetType;
        }

        public long getTarget(long source) {
            return rangeModifiers.stream()
                    .filter(rangeModifier -> rangeModifier.isSourceInRange(source))
                    .findFirst()
                    .map(rangeModifier -> source + (rangeModifier.startTarget - rangeModifier.startSource))
                    .orElse(source);
        }

        public MappingStep getNextMappingStep(long source) {
            return new MappingStep(targetType, getTarget(source));
        }

        public String sourceType() {
            return sourceType;
        }

        public String targetType() {
            return targetType;
        }

        public List<rangeModifier> rangeModifiers() {
            return rangeModifiers;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (TypeMapping) obj;
            return Objects.equals(this.sourceType, that.sourceType) &&
                    Objects.equals(this.targetType, that.targetType) &&
                    Objects.equals(this.rangeModifiers, that.rangeModifiers);
        }

        @Override
        public int hashCode() {
            return Objects.hash(sourceType, targetType, rangeModifiers);
        }

        @Override
        public String toString() {
            return "TypeMapping[" +
                    "sourceType=" + sourceType + ", " +
                    "targetType=" + targetType + ", " +
                    "rangeModifiers=" + rangeModifiers + ']';
        }

    }

    public record rangeModifier(
            long startSource,
            long startTarget,
            long length
    ) {
        boolean isSourceInRange(long source) {
            return source >= startSource && source < startSource + length;
        }
    }

    public static class Almanac {

        private final Set<SeedRange> seedRanges;
        private final Map<String, TypeMapping> typeMappings;

        public Almanac(Set<SeedRange> seedRanges, Map<String, TypeMapping> typeMappings) {
            this.seedRanges = seedRanges;
            this.typeMappings = typeMappings;
        }

        public Almanac() {
            this.seedRanges = new HashSet<>();
            this.typeMappings = new HashMap<>();
        }

        public List<MappingStep> mappingStepsStartingFromSeed(long start) {
            List<MappingStep> mappingSteps = new ArrayList<>();
            String sourceType = "seed";
            long sourceIdentifier = start;
            mappingSteps.add(new MappingStep(sourceType, sourceIdentifier));

            while (typeMappings.containsKey(sourceType)) {
                TypeMapping typeMapping = typeMappings.get(sourceType);
                MappingStep nextMappingStep = typeMapping.getNextMappingStep(sourceIdentifier);
                assert (nextMappingStep.type().equals(typeMapping.targetType()));
                sourceType = nextMappingStep.type();
                sourceIdentifier = nextMappingStep.identifier();
                mappingSteps.addLast(nextMappingStep);
            }

            return mappingSteps;
        }

        public long getClosestLocation() {
            long closestLocation = Long.MAX_VALUE;
            int j = 0;
            for (SeedRange seedRange : seedRanges) {
                System.out.println("looping over seedRange " + ++j + "/" + seedRanges.size());
                for (long i = 0; i < seedRange.range; i++) {
                    //System.out.println("looping over seed " + i + "/" + seedRange.range);
                    long locationIdentifierForSeed = this.mappingStepsStartingFromSeed(seedRange.start + i).getLast().identifier;
                    if (locationIdentifierForSeed < closestLocation) {
                        closestLocation = locationIdentifierForSeed;
                        System.out.println("temp closest location = " + closestLocation);
                    }
                }
            }

            return closestLocation;
        }
    }
}