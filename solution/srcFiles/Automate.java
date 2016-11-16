package srcFiles;

import java.util.ArrayList;

/**
 * Created by Gabriel Bourgault on 2016-11-15.
 */
public class Automate {
    private Node root;

    public Automate(){
        root = new Node();
    }

    public static class Node {
        private String nom;
        private ArrayList<Node> enfants;
        private Node parent;
    }
}
