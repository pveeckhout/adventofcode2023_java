package be.pietervaneeckhout.adventofcode.adventofcode._2023.day9;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day9 {

    private static final Pattern numberPattern = Pattern.compile("(-?\\d+)");

    public static void main(String[] args) {
        long sumOfExtrapolations = parseInput(Day9Input.input).stream()
                .map(OasisPoint::extrapolateBackwardPointFromHistory)
                .reduce(Long::sum)
                .orElseThrow();

        System.out.println("Sum of extrapolations: " + sumOfExtrapolations);
    }

    public static List<OasisPoint> parseInput(String input) {
        return input.lines()
                .map(Day9::parseSingleLine)
                .toList();
    }

    public static OasisPoint parseSingleLine(String line) {
        Matcher matcher = numberPattern.matcher(line);

        OasisPoint oasisPoint = new OasisPoint();

        while (matcher.find()) {
            Long l = Long.parseLong(matcher.group());
            oasisPoint.getPointHistory().addLast(l);
        }

        return oasisPoint;
    }

    @Getter
    public static class OasisPoint {
        List<Long> pointHistory = new ArrayList<>();

        public long extrapolateForwardPointFromHistory() {
            List<List<Long>> steps = getInterpolationSteps();

            List<Long> diffs = new ArrayList<>(steps.size());
            diffs.addFirst(steps.removeLast().getLast());
            for (int i = steps.size() - 1; i > 0; i--) {
                long lastDiff = diffs.getFirst();
                long nextLast = steps.removeLast().getLast();
                long nextInterpolation = nextLast + lastDiff;
                diffs.addFirst(nextInterpolation);
            }

            return diffs.getFirst() + pointHistory.getLast();
        }

        public long extrapolateBackwardPointFromHistory() {
            List<List<Long>> steps = getInterpolationSteps();

            List<Long> diffs = new ArrayList<>(steps.size());
            diffs.addFirst(steps.removeLast().getFirst());
            for (int i = steps.size() - 1; i > 0; i--) {
                long lastDiff = diffs.getFirst();
                long nextFirst = steps.removeLast().getFirst();
                long nextInterpolation = nextFirst - lastDiff;
                diffs.addFirst(nextInterpolation);
            }

            return pointHistory.getFirst() - diffs.getFirst();
        }

        private List<List<Long>> getInterpolationSteps() {
            List<List<Long>> steps = new ArrayList<>();
            steps.add(pointHistory);
            boolean hasFinished = false;
            int stepCount = 0;
            while (!hasFinished) {
                List<Long> prevStep = steps.get(stepCount++);
                List<Long> step = new ArrayList<>();
                for (int i = 0; i < prevStep.size() - 1; i++) {
                    long diff = prevStep.get(i + 1) - prevStep.get(i);

                    step.add(i, diff);
                }

                hasFinished = step.stream().allMatch(l -> l == 0L);

                if (!hasFinished) {
                    steps.addLast(step);
                }
            }
            return steps;
        }
    }
}
