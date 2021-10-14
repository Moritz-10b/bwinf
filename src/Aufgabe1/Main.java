package Aufgabe1;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Main {
    static final char EMPTY_SLOT = '-';
    public static List<String> input;
    public static List<Character> list;
    public static int movingCars;
    public static int parkingLots;

    public static void main(String[] args) throws IOException {
        String file = "src//Aufgabe1//parkplatz5.txt";
        input = read(file);
        parkingLots = input.remove(0).trim().split(" ")[1].toLowerCase().charAt(0) - 96;
        movingCars = Integer.parseInt(input.remove(0));
        output();
    }

    public static List<String> read(String file) throws IOException {
        return Files.readAllLines(Path.of(file));
    }
    public static void addCar(List<Character> row, char car, int slot) {
        row.set(slot, car);
        row.set(slot + 1, car);
    }
    public static List<Character> setupCars() {
        List<Character> row = new ArrayList<>();
        for (int i = 0; i < parkingLots; i++) {
            row.add(EMPTY_SLOT);
        }
        for (int i = 0; i < movingCars; i++) {
            String[] temp = input.remove(0).trim().split(" ");
            addCar(row, temp[0].toLowerCase().charAt(0), Integer.parseInt(temp[1]));
        }
        return row;
    }
    public static Map<Character, Integer> movesRequired(int dir, int slot) {
        Map<Character, Integer> moves = new HashMap<>();
        List<Character> row = new ArrayList<>(list);
        char curCar = row.get(slot);
        while (row.get(slot) != EMPTY_SLOT) {
            if(row.indexOf(curCar) == 0 && dir == 0 || row.indexOf(curCar) == parkingLots - 2 && dir == 1) {
                return null;
            }
            if (check(row, dir, curCar) == EMPTY_SLOT) {
                move(row, dir, curCar);
                updateMoves(moves, curCar);
                curCar = row.get(slot);
            } else {
                curCar = check(row, dir, curCar);
            }
        }
        return moves;
    }
    public static char check(List<Character> row, int dir, char car) {
        if (dir == 0) {
            if (row.get(row.indexOf(car)) == row.get(row.indexOf(car) - 1)) {
                return row.get(row.indexOf(car) - 2);
            } else {
                return row.get(row.indexOf(car) - 1);
            }
        } else {
            if (row.get(row.lastIndexOf(car)) == row.get(row.lastIndexOf(car) + 1)) {
                return row.get(row.lastIndexOf(car) + 2);
            } else {
                return row.get(row.lastIndexOf(car) + 1);
            }
        }
    }
    public static void move(List<Character> row, int dir, char car) {
        if (dir == 0) {
            row.set(row.lastIndexOf(car), '-');
            row.set(row.indexOf(car) - 1, car);
        } else if (dir == 1) {
            row.set(row.indexOf(car), '-');
            row.set(row.indexOf(car) + 1, car);
        }

    }
    public static void updateMoves(Map<Character, Integer> moves, char car) {
        if (moves.containsKey(car)){
            moves.replace(car, moves.get(car) + 1);
        } else {
            moves.put(car, 1);
        }
    }
    public static void printMap(Map<Character, Integer> moves, int dir) {
        String direction = "";
        if (dir == 0) {
            direction = "links";
        } else {
            direction = "rechts";
        }
        final String direc = direction;
        moves.forEach((key, value) -> System.out.print(Character.toUpperCase(key) + " " + value + " " + direc + " "));
    }
    public static void output(){
        list = setupCars();
        for (int i = 0; i < parkingLots; i++) {
            Map<Character, Integer> mapLeft = movesRequired(0, i);
            Map<Character, Integer> mapRight = movesRequired(1, i);
            System.out.print((char)(i + 65) + ": ");
            if (mapLeft != null && mapRight != null) {
                if(mapLeft.values().stream().mapToInt(Integer::intValue).sum() == 0 ||
                        mapRight.values().stream().mapToInt(Integer::intValue).sum() == 0) {
                } else if (mapLeft.values().stream().mapToInt(Integer::intValue).sum() <
                        mapRight.values().stream().mapToInt(Integer::intValue).sum()) {
                    printMap(mapLeft, 0);
                } else {
                    printMap(mapRight, 1);
                }
            } else if (mapLeft != null ) {
                printMap(mapLeft, 0);
            } else if (mapRight != null ) {
                printMap(mapRight, 1);
            } else {
                System.out.print("Kann nicht ausparken");
            }
            System.out.println("");
        }
    }
}