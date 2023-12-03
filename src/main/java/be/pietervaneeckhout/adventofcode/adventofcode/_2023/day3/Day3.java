package be.pietervaneeckhout.adventofcode.adventofcode._2023.day3;

import lombok.Value;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day3 {

    private static final Pattern SYMBOL_PATTERN = Pattern.compile("([^0-9.])");
    private static final Pattern PARTNUMBER_PATTERN = Pattern.compile("(\\d+)");

    public static void main(String[] args) {
        System.out.printf("schematic part sum: %d%n", new Day3().getSchematic(Day3Input.input).getValidPartSum());
        System.out.printf("EngineTotalGearRatio: %d%n", new Day3().getSchematic(Day3Input.input).getEngineTotalGearRatio());
    }

    public Schematic getSchematic(String input) {
        Schematic schematic = new Schematic();

        List<String> lineList = input.lines().toList();
        for (int i = 0; i < lineList.size(); i++) {
            String line = lineList.get(i);
            Matcher SymbolMatcher = SYMBOL_PATTERN.matcher(line);
            Matcher PartNumberMatcher = PARTNUMBER_PATTERN.matcher(line);

            while (SymbolMatcher.find()) {
                schematic.addSymbol(new SchematicSymbol(SymbolMatcher.group(), i, SymbolMatcher.start()));
            }

            while (PartNumberMatcher.find()) {
                schematic.addPart(new SchematicPart(PartNumberMatcher.group(), i, PartNumberMatcher.start()));
            }
        }

        return schematic;
    }

    @Value
    public static class Schematic {
        Set<SchematicPart> parts = new HashSet<>();
        Set<SchematicSymbol> symbols = new HashSet<>();

        public static boolean isValid(Set<SchematicSymbol> schematicSymbols, SchematicPart schematicPart) {
            Set<Integer> columns = HashSet.newHashSet(schematicPart.partNumber().length());
            Set<Integer> rows = HashSet.newHashSet(3);

            for (int i = -1; i <= schematicPart.partNumber().length(); i++) {
                columns.add(schematicPart.startColumn() + i);
            }

            for (int i = -1; i <= 1; i++) {
                rows.add(schematicPart.row() + i);
            }

            return schematicSymbols.stream()
                    .anyMatch(schematicSymbol -> rows.contains(schematicSymbol.row()) && columns.contains(schematicSymbol.column()));
        }

        public Set<SchematicPart> partsSurrounding(SchematicSymbol schematicSymbol) {
            return parts.stream()
                    .filter(part -> Schematic.isValid(Set.of(schematicSymbol), part))
                    .collect(Collectors.toSet());
        }

        public int getEngineTotalGearRatio() {
            return symbols.stream()
                    .filter(symbol -> symbol.symbol().equals("*"))
                    .map(this::partsSurrounding)
                    .mapToInt(parts -> {
                        if (parts.size() != 2) {
                            return 0;
                        } else {
                            return parts.stream()
                                    .map(SchematicPart::partNumber)
                                    .map(Integer::parseInt)
                                    .reduce(1, (a, b) -> a * b);
                        }
                    })
                    .sum();
        }

        public Set<SchematicPart> getValidParts() {
            return parts.stream()
                    .filter(part -> isValid(symbols, part))
                    .collect(Collectors.toSet());
        }

        public void addPart(SchematicPart part) {
            parts.add(part);
        }

        public void addSymbol(SchematicSymbol symbol) {
            symbols.add(symbol);
        }

        public int getValidPartSum() {
            return getValidParts().stream()
                    .mapToInt(part -> Integer.parseInt(part.partNumber()))
                    .sum();
        }
    }


    public record SchematicSymbol(
            String symbol,
            int row,
            int column) {
    }


    public record SchematicPart(
            String partNumber,
            int row,
            int startColumn) {
    }
}
