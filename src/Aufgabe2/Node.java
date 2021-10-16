package Aufgabe2;

import java.util.ArrayList;
import java.util.List;

public class Node {
    Hotel data;
    List<Node> children;
    public Node(Hotel data) {
        this.data = data;
        this.children = new ArrayList<>();
    }
    public void insert(Hotel hotel) {
        this.children.add(new Node(hotel));
    }

    public void getSubtree() {

    }
    }

