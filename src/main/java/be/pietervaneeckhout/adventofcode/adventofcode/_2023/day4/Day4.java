package be.pietervaneeckhout.adventofcode.adventofcode._2023.day4;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

public class Day4 {

    public static void main(String[] args) {
        System.out.printf("pile point sum: %d%n", Day4.getPileScore(Day4Input.input));
        System.out.printf("amnount of total card: %d%n", Day4.part2(Day4Input.input));
    }

    public static int part2(String input) {
        Map<Integer, Card> originalCards = input.lines()
                .map(Day4::parseLine)
                .collect(Collectors.toMap(Card::id, card -> card));

        Queue<Card> processedCards = new ArrayDeque<>();
        Queue<Card> toProcessCards = new ArrayDeque<>(originalCards.values());
        while (!toProcessCards.isEmpty()) {
            Card card = toProcessCards.poll();
            processedCards.add(card);

            if (!card.getMatchingNumbers().isEmpty()) {
                int cardId = card.id();
                for (int i = cardId + 1; i <= cardId + card.getMatchingNumbers().size(); i++) {
                    toProcessCards.add(originalCards.get(i));
                }
            }
        }

        return processedCards.size();
    }


    public static Card parseLine(String line) {
        String[] split = line.split(":");

        String gameIdentifier = split[0].strip();
        String gameId = gameIdentifier.split(" +")[1];
        String gameNumbers = split[1].strip();
        String[] numberSets = gameNumbers.split("\\|");
        String winningNumbersString = numberSets[0].strip();
        String playNumbersString = numberSets[1].strip();

        return new Card(Integer.parseInt(gameId), numberStringToSet(winningNumbersString), numberStringToSet(playNumbersString));
    }

    private static Set<Integer> numberStringToSet(String winningNumbersString) {
        return Arrays.stream(winningNumbersString.split(" +"))
                .map(String::strip)
                .filter(s -> !s.isBlank())
                .mapToInt(Integer::parseInt)
                .boxed()
                .collect(Collectors.toSet());
    }

    public static int getPileScore(String input) {
        return input.lines()
                .map(Day4::parseLine)
                .mapToDouble(Card::getScore)
                .mapToInt(d -> (int) d)
                .sum();
    }

    public record Card(
            int id,
            Set<Integer> winningNumbers,
            Set<Integer> playNumbers
    ) {
        public Card {
            if (winningNumbers == null) {
                winningNumbers = new HashSet<>();
            }
            if (playNumbers == null) {
                playNumbers = new HashSet<>();
            }
        }

        public Set<Integer> getMatchingNumbers() {
            return playNumbers.stream()
                    .filter(winningNumbers::contains)
                    .collect(Collectors.toSet());
        }

        public double getScore() {
            return Math.max(0, Math.pow(2, getMatchingNumbers().size() - 1));
        }
    }
}
