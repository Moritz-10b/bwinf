package Aufgabe2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
/*
in der ersten Zeile die Anzahl der Hotels n,
  * in der zweiten Zeile Gesamtfahrzeit t in Minuten
  * in den weiteren n Zeilen jeweils für jedes Hotel:
    * die Entfernung t_i des Hotels vom Start, sowie
    * die Bewertung b_i des Hotels.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        String file = "src//Aufgabe2//hotels1.txt";
        List<String> input = read(file);
        int countHotels = Integer.parseInt(input.remove(0));
        int drivingTime = Integer.parseInt(input.remove(0));
        Hotel[] hotels = new Hotel[countHotels];

        for (int i = 0; i < countHotels; i++) {
            hotels[i] = new Hotel(Integer.parseInt(input.get(0).trim().split(" ")[0]), Double.parseDouble(input.remove(0).trim().split(" ")[1]));
        }
        Node xd = implementTree(new Node(new Hotel(0, 5.0)), hotels);
        xd.printSubtree();
    }

    public static Node implementTree(Node root, Hotel[] hotels) {
        for (Hotel hotel : hotels) {
            if (hotel.getDistance() - root.data.getDistance() <= 360 && hotel.getDistance() - root.data.getDistance() > 0) {
                root.insert(hotel);
                implementTree(root.children.get(root.children.size() - 1), hotels);
            }
        }
        return root;
    }

    public static List<String> read(String file) throws IOException {
        return Files.readAllLines(Path.of(file));
    }
}
