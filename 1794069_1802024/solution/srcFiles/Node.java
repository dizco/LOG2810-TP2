package srcFiles;

import java.util.ArrayList;
import java.util.Random;

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

    public Node getEnfantRandom(){
        if (enfants.size() == 0)
            return this; //on a affaire avec un noeud final

        Random generator = new Random(System.nanoTime());
        return enfants.get(generator.nextInt(enfants.size())).getEnfantRandom();
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

    public Node getNodeExistante(String nom){
        if (this.nom.equals(nom))
            return this;
        for (int i = 0; i < this.nom.length(); i++){
            if (this.nom.charAt(i) != nom.charAt(i))
                return null;
        }

        for (Node enfant : enfants){
            Node resultat = enfant.getNodeExistante(nom);
            if (resultat != null)
                return resultat;
        }
        return null;
    }

}
