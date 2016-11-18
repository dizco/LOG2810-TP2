package srcFiles;

/**
 * Created by Gabriel Bourgault on 2016-11-15.
 */
public class Automate {
    private Node root;

    public Automate(){}

    public void ajouter(String nom){
        String cleanNom = nom.replaceAll("\\s","");
        if (root == null)
            root = new Node(cleanNom.substring(0, 1));
        root.ajouterEnfant(cleanNom);
    }

    public Node getNodeExistante(String nom) {
        String cleanNom = nom.replaceAll("\\s","");
        return root.getNodeExistante(nom);
    }

    public Node getDestinationRandom(){
        return root.getEnfantRandom();
    }

    public Node getRoot(){
        return root;
    }
}
