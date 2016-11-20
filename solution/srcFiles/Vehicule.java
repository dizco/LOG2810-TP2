package srcFiles;

/**
 * Created by Gabriel Bourgault on 2016-11-15.
 */
public class Vehicule {
    private boolean occupation; //s'il y a quelqu'un dedans
    private Automate zoneActuelle;
    private Node positionActuelle;

    private Integer nombreDePassagers;
    private Integer nombreDePlacesTotales;

    public Vehicule(){
        nombreDePassagers = 0;
        nombreDePlacesTotales = 0;
    };

    public Vehicule(boolean occupation, Automate zoneActuelle, Node positionActuelle, Integer nombreDePassagers, Integer nombreDePlacesTotales){
        this.occupation = occupation;
        this.zoneActuelle = zoneActuelle;
        this.positionActuelle = positionActuelle;
        this.nombreDePassagers = nombreDePassagers;
        this.nombreDePlacesTotales = nombreDePlacesTotales;
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

    public Integer getNombreDePassagers() {
        return nombreDePassagers;
    }

    public Integer getNombreDePlacesTotales() {
        return nombreDePlacesTotales;
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

    public void setNombreDePassagers(Integer nombreDePassagers) {
        if (nombreDePassagers > nombreDePlacesTotales)
            throw new IllegalArgumentException("Le nombre de passagers ne peut être supérieur au nombre de places totales.");
        this.nombreDePassagers = nombreDePassagers;
    }

    public void setNombreDePlacesTotales(Integer nombreDePlacesTotales) {
        if (nombreDePassagers > nombreDePlacesTotales)
            throw new IllegalArgumentException("Le nombre de passagers ne peut être supérieur au nombre de places totales.");
        this.nombreDePlacesTotales = nombreDePlacesTotales;
    }

    public boolean peutAccueillirPassagers(Integer nombreDePassagers){
        return !estOccupe() && (this.nombreDePlacesTotales >= nombreDePassagers);
    }
}
