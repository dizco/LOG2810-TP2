package srcFiles;

/**
 * Created by Gabriel Bourgault on 2016-11-15.
 */
public class Vehicule {
    private boolean occupation; //s'il y a quelqu'un dedans
    private Automate zoneActuelle;
    private Node positionActuelle;

    public Vehicule(){}

    public Vehicule(boolean occupation, Automate zoneActuelle, Node positionActuelle){
        this.occupation = occupation;
        this.zoneActuelle = zoneActuelle;
        this.positionActuelle = positionActuelle;
    }

    public boolean estOccupe() {
        return occupation;
    }

    public Automate getZoneActuelle() {
        return zoneActuelle;
    }

    public Node getPositionActuelle() {
        return positionActuelle;
    }

    public void setOccupation(boolean occupation) {
        this.occupation = occupation;
    }

    public void setZoneActuelle(Automate zoneActuelle) {
        this.zoneActuelle = zoneActuelle;
    }

    public void setPositionActuelle(Node positionActuelle) {
        this.positionActuelle = positionActuelle;
    }

    public boolean peutAccueillirPassagers(){
        return !estOccupe();
    }
}
