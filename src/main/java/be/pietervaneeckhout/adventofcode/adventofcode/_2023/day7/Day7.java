package be.pietervaneeckhout.adventofcode.adventofcode._2023.day7;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Getter;

public class Day7 {

    public static void main(String[] args) {
        System.out.printf("total winnings: %s%n", getTotalWinnings(Day7Input.input));
    }

    public static BigInteger getTotalWinnings(String input) {
        BigInteger totalWinnings = BigInteger.ZERO;
        List<CamelHand> hands = parseInput(input);

        for (int i = 0; i < hands.size(); i++) {
            CamelHand camelHand = hands.get(i);
            BigInteger bet = camelHand.getBet();
            BigInteger rank = new BigInteger(i + 1 + "");
            BigInteger thisWinning = bet.multiply(rank);
            totalWinnings = totalWinnings.add(thisWinning);
        }
        return totalWinnings;
    }

    public static List<CamelHand> parseInput(String input) {
        return input.lines()
                .map(line -> {
                    Matcher matcher = Pattern.compile("(?<hand>[2-9TJQKA]{5}) (?<bet>\\d+)").matcher(line);
                    if (matcher.find()) {
                        char[] hand = matcher.group("hand").toCharArray();
                        String bet = matcher.group("bet");

                        return new CamelHand(hand, bet);
                    }
                    throw new RuntimeException("no valid camel hand config");
                }).sorted().toList();
    }

    @Getter
    public static class CamelHand implements Comparable<CamelHand> {

        private final BigInteger bet;
        private final char[] hand;
        public final BigInteger rank;

        public CamelHand(char[] hand, String bet) {
            this.hand = hand;
            this.bet = new BigInteger(bet);
            this.rank = calculateTypeRank(calculateCharacterCounts(hand)).multiply(calculatedSequenceRank(hand));
        }

        public static BigInteger calculateTypeRank(Map<Character, Integer> characterCounts) {
            int amountOfJokers = characterCounts.getOrDefault('J', 0);

            var sortedNonJokerAmounts = characterCounts.entrySet().stream()
                    .filter(entry -> !entry.getKey().equals('J'))
                    .map(Map.Entry::getValue)
                    .sorted(Comparator.reverseOrder()) // invert the order
                    .toList();

            int maxNonJoker = 0;
            if (!sortedNonJokerAmounts.isEmpty()) {
                maxNonJoker = sortedNonJokerAmounts.getFirst();
            }
            int secondMaxNonJoker = 0;
            if (sortedNonJokerAmounts.size() > 1) {
                secondMaxNonJoker = sortedNonJokerAmounts.get(1);
            }

            int maxSame = amountOfJokers + maxNonJoker;
            return switch (maxSame) {
                case 5 -> new BigInteger("1000000");
                case 4 -> new BigInteger("100000");
                case 3 -> {
                    if (secondMaxNonJoker == 2) {
                        yield new BigInteger("10000");
                    } else {
                        yield new BigInteger("1000");
                    }
                }
                case 2 -> {
                    if (secondMaxNonJoker == 2) {
                        yield new BigInteger("100");
                    } else {
                        yield BigInteger.TEN;
                    }
                }
                default -> BigInteger.ONE;
            };
        }

        public static BigInteger calculatedSequenceRank(char[] hand) {
            StringBuilder rank = new StringBuilder("1"); // start with on to prevent disappearing leading zeros
            for (char c : hand) {
                rank.append(switch (c) {
                    case 'T' -> "10";
                    case 'J' -> "01";
                    case 'Q' -> "12";
                    case 'K' -> "13";
                    case 'A' -> "14";
                    default -> "0" + c;
                });
            }

            return new BigInteger(rank.toString());
        }

        public static Map<Character, Integer> calculateCharacterCounts(char[] characters) {
            Map<Character, Integer> characterCounts = new HashMap<>();
            for (Character character : characters) {
                characterCounts.put(character, characterCounts.getOrDefault(character, 0) + 1);
            }

            return characterCounts;
        }

        @Override
        public int compareTo(CamelHand o) {
            // compare based on rank, descending
            return this.rank.compareTo(o.rank);
        }
    }
}
