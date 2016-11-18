package srcFiles;

/**
 * Created by Gabriel Bourgault on 2016-11-15.
 */
public class Utilisateur {
    private Node origine;
    private Node destination;
    private Integer numeroDeGroupe;

    public Node getOrigine(){
        return origine;
    }

    public Node getDestination() {
        return destination;
    }

    public Integer getNumeroDeGroupe() {
        return numeroDeGroupe;
    }

    public void setOrigine(Node origine) {
        this.origine = origine;
    }

    public void setDestination(Node destination) {
        this.destination = destination;
    }

    public void setNumeroDeGroupe(Integer numeroDeGroupe) {
        this.numeroDeGroupe = numeroDeGroupe;
    }
}
