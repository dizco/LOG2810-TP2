package srcFiles;

import java.util.ArrayList;

/**
 * Created by Gabriel Bourgault on 2016-11-16.
 */
public class Node {
    private String nom;
    private ArrayList<Node> enfants;
    private Node parent;

    public Node(String nom){
        this.nom = nom;
        enfants = new ArrayList<>();
    }

    public String getNom(){
        return nom;
    }

    public ArrayList<Node> getEnfants(){
        return enfants;
    }

    //Fait une recherche selon le nom de noeud
    private Node getEnfant(String nom){
        for (Node enfant : enfants){
            if (enfant.getNom().equals(nom))
                return enfant;
        }
        return null;
    }

    public Node getParent(){
        return parent;
    }

    public void setNom(String nom){
        this.nom = nom;
    }

    public void setParent(Node parent){
        this.parent = parent;
    }

    public void ajouterEnfant(String nom){
        if (nom.length() > this.nom.length()){
            String nomExtrait = nom.substring(0, this.nom.length() + 1);

            Node enfantCorrespondant = getEnfant(nomExtrait);
            if (enfantCorrespondant == null){
                //l'enfant n'existe pas, on le crée
                Node enfant = new Node(nomExtrait);
                ajouterEnfant(enfant);
                enfant.ajouterEnfant(nom);
            }
            else {
                enfantCorrespondant.ajouterEnfant(nom);
            }

        }
    }

    public void ajouterEnfant(Node node) {
        node.setParent(this);
        enfants.add(node);
    }

}
