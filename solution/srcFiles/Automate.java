package srcFiles;

/**
 * Created by Gabriel Bourgault on 2016-11-15.
 */
public class Automate {
    private Node root;

    public Automate(){
        //root = new Node();
    }

    public void ajouter(String nom){
        //TODO: Inserer un noeud dans l'automate
        String cleanNom = nom.replaceAll("\\s","");
        if (root == null)
            root = new Node(cleanNom.substring(0, 1));
        root.ajouterEnfant(cleanNom);
    }
}
