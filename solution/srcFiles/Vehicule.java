package srcFiles;

import com.sun.javaws.exceptions.InvalidArgumentException;

/**
 * Created by Gabriel Bourgault on 2016-11-15.
 */
public class Vehicule {
    private boolean occupation; //s'il y a quelqu'un dedans
    private Automate zoneActuelle;
    private Node positionActuelle;

    private Integer nombreDePassagers;
    private Integer nombreDePlacesDisponibles;

    public Vehicule(){};

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

    public Integer getNombreDePassagers() {
        return nombreDePassagers;
    }

    public Integer getNombreDePlacesDisponibles() {
        return nombreDePlacesDisponibles;
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
        if (nombreDePassagers > nombreDePlacesDisponibles)
            throw new IllegalArgumentException("Le nombre de passagers ne peut être supérieur au nombre de places disponibles.");
        this.nombreDePassagers = nombreDePassagers;
    }

    public void setNombreDePlacesDisponibles(Integer nombreDePlacesDisponibles) {
        this.nombreDePlacesDisponibles = nombreDePlacesDisponibles;
    }
}
