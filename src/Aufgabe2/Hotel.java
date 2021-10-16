package Aufgabe2;

public class Hotel {
    int distance;
    double rating;
    public Hotel(int distance, double rating) {
        this.distance = distance;
        this.rating = rating;
    }
    int getDistance() {
        return distance;
    }
    double getRating() {
        return rating;
    }
    public void printInfo() {
        System.out.print("distance: " + distance + ", rating: " + rating + " - ");
    }
}
