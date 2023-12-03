package be.pietervaneeckhout.adventofcode.adventofcode._2023.day2;

import lombok.Value;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day2 {

    private static final Pattern gameIdPattern = Pattern.compile("Game (?<id>\\d+)");
    private static final Pattern pullPattern = Pattern.compile("(?<amount>\\d+) (?<color>[a-zA-Z]+)");

    public static void main(String[] args) {
        Map<String, Integer> config = Map.of(
                "red", 12,
                "green", 13,
                "blue", 14
        );

        System.out.printf("SumOfIdsForValidGames: %d%n", Day2.getSumOfIdsForValidGames(Day2Input.input, config));
        System.out.printf("getSumOfGamesPower: %d%n", Day2.getSumOfGamesPower(Day2Input.input, config));
    }

    public static int getSumOfIdsForValidGames(String input, Map<String, Integer> config) {
        return input.lines()
                .map(line -> Day2.parseGameFromLine(line, config))
                .filter(Game::isValid)
                .mapToInt(Game::getId)
                .sum();
    }

    public static int getSumOfGamesPower(String input, Map<String, Integer> config) {
        return input.lines()
                .map(line -> Day2.parseGameFromLine(line, config))
                .mapToInt(Game::getGamePower)
                .sum();
    }

    public static Game parseGameFromLine(String line, Map<String, Integer> config) {
        String[] split = line.split(":");

        String gameIdentifier = split[0].strip();
        String gamePullSets = split[1].strip();
        String[] split1 = gamePullSets.split(";");

        Matcher matcher = gameIdPattern.matcher(gameIdentifier);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Could not parse game identifier: %s".formatted(gameIdentifier));
        }

        Game game = new Game(Integer.parseInt(matcher.group("id")), config);

        Arrays.stream(split1).forEach(
                pullSetString -> {
                    String[] split2 = pullSetString.split(",");
                    Map<String, Integer> pullSet = Arrays.stream(split2).map(
                            pull -> {
                                Matcher pullMatcher = pullPattern.matcher(pull);
                                if (pullMatcher.find()) {
                                    String amount = pullMatcher.group("amount");
                                    String color = pullMatcher.group("color");

                                    return new AbstractMap.SimpleEntry<>(color, Integer.parseInt(amount));
                                } else {
                                    throw new IllegalArgumentException("Could not parse pull: %s".formatted(pull));
                                }
                            }
                    ).collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue
                    ));

                    game.addPullSet(pullSet);
                }
        );

        return game;
    }

    @Value
    public static class Game {

        int id;
        Map<String, Integer> config;
        List<Map<String, Integer>> pulls;

        public Game(int id, Map<String, Integer> config) {
            this.id = id;
            this.config = config;
            this.pulls = new ArrayList<>();
        }

        public void addPullSet(Map<String, Integer> pull) {
            pulls.add(pull);
        }

        public Map<String, Integer> getMaxAmountPerColor() {
            return pulls.stream().reduce(
                    new HashMap<>(), (finalMap, currentMap) -> {
                        currentMap.forEach((key, value) ->
                                finalMap.merge(key, value, Integer::max));
                        return finalMap;
                    });
        }

        public int getGamePower() {
            return getMaxAmountPerColor().values()
                    .stream()
                    .mapToInt(Integer::intValue)
                    .reduce(1, (a, b) -> a * b);
        }

        public boolean isValid() {
            boolean isValid = true;

            Iterator<Map.Entry<String, Integer>> iterator = getMaxAmountPerColor().entrySet().iterator();
            while (iterator.hasNext() && isValid) {
                Map.Entry<String, Integer> entry = iterator.next();
                if (entry.getValue() > config.get(entry.getKey())) {
                    isValid = false;
                }
            }

            return isValid;
        }
    }
}
