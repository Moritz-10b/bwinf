package Aufgabe2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
/*
in der ersten Zeile die Anzahl der Hotels n,
  * in der zweiten Zeile Gesamtfahrzeit t in Minuten
  * in den weiteren n Zeilen jeweils für jedes Hotel:
    * die Entfernung t_i des Hotels vom Start, sowie
    * die Bewertung b_i des Hotels.
 */
public class Main {
    public static List<Node> path;
    public static  List<List<Node>> allPaths;
    public static void main(String[] args) throws IOException {
        String file = "src//Aufgabe2//hotels2.txt";
        List<String> input = read(file);
        int countHotels = Integer.parseInt(input.remove(0));
        int drivingTime = Integer.parseInt(input.remove(0));
        Hotel[] hotels = new Hotel[countHotels];
        for (int i = 0; i < countHotels; i++) {
            hotels[i] = new Hotel(Integer.parseInt(input.get(0).trim().split(" ")[0]), Double.parseDouble(input.remove(0).trim().split(" ")[1]));
        }
        Node root = implementTree(new Node(new Hotel(0, 5.0)), hotels, drivingTime);
        path = new LinkedList<>();
        allPaths = new ArrayList<>();
        fillAllPaths(root, drivingTime);
        findBestPath();
    }

    public static void fillAllPaths(Node root, int drivingTime) {
            path.add(root);
            for(Node child : root.children) {
                fillAllPaths(child, drivingTime);
            }
            if(path.get(path.size() - 1).data.getDistance() + 360 > drivingTime && path.size() <= 5) {
                List<Node> add = new LinkedList<>(path);
                add.remove(0);
                allPaths.add(add);
            }
            path.remove(root);
    }
    public static void findBestPath() {
        List<Double> minPathRatings = new ArrayList<>();
        double minHotelRating;
        double maxPathRating;
        for (List<Node> path : allPaths) {
            minHotelRating = path.get(0).data.getRating();
            for (Node hotel : path) {
                if(minHotelRating > hotel.data.getRating()) {
                    minHotelRating = hotel.data.getRating();
                }
            }
            minPathRatings.add(minHotelRating);
        }
        maxPathRating = minPathRatings.get(0);
        int pos = 0;
        for (double value : minPathRatings) {
            pos++;
            if(maxPathRating < value) {
                maxPathRating = value;
            }
        }
        printPath(pos, maxPathRating);
    }
    public static void printPath(int pos, double maxPathRating) {
        for (int i = 0; i < allPaths.get(pos -1).size(); i++) {
            allPaths.get(pos-1).get(i).data.printInfo();
        }
        System.out.print("minimale Bewertung: " + maxPathRating);
    }
    public static void Test() {
        System.out.println(allPaths.size());
        for (List<Node> path : allPaths) {
            for (Node node : path) {
                node.data.printInfo();
            }
            System.out.println();
        }
    }


    public static Node implementTree(Node root, Hotel[] hotels, int drivingTime) {
        for (Hotel hotel : hotels) {
            if (hotel.getDistance() - root.data.getDistance() <= 360 && hotel.getDistance() - root.data.getDistance() > 0 && root.data.getDistance() < drivingTime) {
                root.insert(hotel);
                implementTree(root.children.get(root.children.size() - 1), hotels, drivingTime);
            }
        }
        return root;
    }

    public static List<String> read(String file) throws IOException {
        return Files.readAllLines(Path.of(file));
    }
}
