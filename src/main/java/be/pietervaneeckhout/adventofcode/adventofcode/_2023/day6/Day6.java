package be.pietervaneeckhout.adventofcode.adventofcode._2023.day6;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Day6 {

    // legend:
    //      * Hold time -> h
    //      * Distance traveled -> d
    //      * actual travel time = t
    //      * Max race time -> T
    // formula -> d = t * h
    // formula -> T = t + h
    // formula -> (T - h) h  > d
    // formula -> h > 0
    // formula -> T = 7
    // formula -> d > 9

    public static void main(String[] args) {
        Integer raceSolutionsAmountMultiplied = parseRacesFromInput(Day6Input.input)
                .stream()
                .map(race -> Day6.solveInequality(race.distance(), race.time(), 1))
                .map(List::size)
                .reduce(1, (a, b) -> a * b);

        System.out.printf("raceSolutionsAmountMultiplied: %d%n", raceSolutionsAmountMultiplied);
    }

    public static List<Race> parseRacesFromInput(String input) {
        Pattern pattern = Pattern.compile("(\\d+)");

        List<Double> times = new ArrayList<>();
        List<Double> distances = new ArrayList<>();
        List<Race> races = new ArrayList<>();

        input.lines().forEach(
                line -> {
                    if (line.startsWith("Time:")) {
                        String s = line.replaceAll("\\D", "");
                        times.addLast(Double.parseDouble(s));
                    }
                    if (line.startsWith("Distance:")) {
                        String s = line.replaceAll("\\D", "");

                        distances.addLast(Double.parseDouble(s));
                    }
                }
        );

        if (times.size() != distances.size()) {
            throw new RuntimeException("parsing error, skewed input for races");
        }

        for (int i = 0; i < times.size(); i++) {
            races.add(new Race(times.get(i), distances.get(i)));
        }

        return races;
    }

    public static List<Double> solveInequality(double targetDistance, double raceTime, double precision) {
        final double minHoldTime = 0;                   // h cannot be negative or zero
        final List<Double> solutions = new ArrayList<>();

        for (double holdTime = minHoldTime; holdTime <= raceTime; holdTime += precision) {
            double startSpeed = holdTime;
            double distance = (raceTime - holdTime) * startSpeed;
            if (targetDistance < distance) {
                solutions.add(holdTime);
            }
        }

        return solutions;
    }

    public record Race(
            double time,
            double distance
    ) {
    }

    public record InequalitySolution(
            double startRange,
            double endRange,
            double precision
    ) {
    }
}
