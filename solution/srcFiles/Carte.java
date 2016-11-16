package srcFiles;

import java.util.ArrayList;

/**
 * Created by Gabriel Bourgault on 2016-11-15.
 */
public class Carte {
    private ArrayList<Automate> zones;

    public Carte(){
        zones = new ArrayList<>();
    }

    public void ajouterZone(Automate automate){
        zones.add(automate);
    }
}
