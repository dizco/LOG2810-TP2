package srcFiles;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * Created by Gabriel Bourgault on 2016-11-15.
 */
public class Carte {
    private ArrayList<Automate> zones;
    private ArrayList<Vehicule> vehicules;
    private ArrayList<Utilisateur> utilisateurs;

    public Carte(){
        zones = new ArrayList<>();
        vehicules = new ArrayList<>();
        utilisateurs = new ArrayList<>();
    }

    public ArrayList<Automate> getZones(){
        return zones;
    }

    public void ajouterZone(Automate automate){
        zones.add(automate);
    }

    public void ajouterVehicule(Vehicule vehicule){
        vehicules.add(vehicule);
    }

    public void ajouterUtilisateur(Utilisateur utilisateur){
        utilisateurs.add(utilisateur);
    }

    public Node getNodeExistante(String nom){
        for (Automate zone : zones){
            Node resultat = zone.getNodeExistante(nom);
            if (resultat != null)
                return resultat;
        }
        return null;
    }

    public Automate getZoneDeNode(Node node){
        if (node != null){
            Node temp = node;
            while (temp.getParent() != null)
                temp = temp.getParent();

            for (Automate zone : zones){
                if (zone.getRoot().equals(temp))
                    return zone;
            }
        }
        return null;
    }

    public void equilibrerZones(){
        ArrayList<Integer> nombreDeVehicules = new ArrayList<>();
        int premierNombre = 0;
        boolean inegal = false;
        System.out.println("Zone -> Nombre de véhicules avant équilibrage -> Nombre de véhicules après répartition.");
        for (int i = 0; i < zones.size(); i++){
            int nombre = nombreDeVehiculesDansZone(zones.get(i));
            nombreDeVehicules.add(nombre);

            if (i == 0)
                premierNombre = nombre;
            if (!inegal && premierNombre != nombre)
                inegal = true;
        }

        if (inegal){
            repartirLesVehiculesEntreLesZones(nombreDeVehicules);
        }

        for (int i = 0; i < zones.size(); i++){
            System.out.println(i + " -> " + nombreDeVehicules.get(i) + " -> " + nombreDeVehiculesDansZone(zones.get(i)) + ".");
        }

    }

    private void repartirLesVehiculesEntreLesZones(ArrayList<Integer> nombreDeVehicules){
        ArrayList<Integer> differentiel = calculerDifferentiel(nombreDeVehicules);
        CompteurDeDeplacementsSingleton compteurSingleton = CompteurDeDeplacementsSingleton.getInstance();

        ArrayList<Vehicule> vehiculesExcedentaires = new ArrayList<>();
        for (int i = 0; i < zones.size(); i++){
            while (differentiel.get(i) > 0){
                differentiel.set(i, differentiel.get(i) - 1);
                vehiculesExcedentaires.add(getVehiculeRandomDansZone(zones.get(i)));
            }
        }

        while (Math.abs(calculerSomme(differentiel)) > vehiculesExcedentaires.size()) {
            //on a besoin de plus de véhicules que ceux que l'on a
            boolean vehiculeTrouve = false;
            for (int i = 0; i < zones.size() && !vehiculeTrouve; i++){
                if (nombreDeVehiculesDansZone(zones.get(i)) == (int)Math.ceil(calculateAverage(nombreDeVehicules))){
                    vehiculesExcedentaires.add(getVehiculeRandomDansZone(zones.get(i)));
                    vehiculeTrouve = true;
                }
            }
        }

        for (int i = 0; i < zones.size(); i++){
            while (differentiel.get(i) < 0){
                if (vehiculesExcedentaires.size() == 0){
                    throw new NoSuchElementException();
                }

                differentiel.set(i, differentiel.get(i) + 1);
                Vehicule v = vehiculesExcedentaires.remove(0);
                v.setZoneActuelle(zones.get(i));
                v.setPositionActuelle(zones.get(i).getDestinationRandom());
                compteurSingleton.augmenterNombreDeDeplacementsAVide();
            }
        }

    }

    private ArrayList<Integer> calculerDifferentiel(ArrayList<Integer> nombreDeVehicules) {
        ArrayList<Integer> differentiel = new ArrayList<>();
        double moyenne = calculateAverage(nombreDeVehicules);
        int up = (int)Math.ceil(moyenne);
        int down = (int)Math.floor(moyenne);

        for (int i = 0; i < zones.size(); i++){
            //int temp = Math.min(Math.abs(nombreDeVehicules.get(i)-up), Math.abs(nombreDeVehicules.get(i)-down));
            if (down > nombreDeVehicules.get(i)) //il n'y a pas assez de véhicules
                differentiel.add(i, -1 * (down - nombreDeVehicules.get(i)));
            else if (up < nombreDeVehicules.get(i)) //il y a trop de véhicules
                differentiel.add(i, nombreDeVehicules.get(i) - up);
            else //il y a un nombre acceptable de véhicules
                differentiel.add(i, 0);
        }
        return differentiel;
    }

    //Inspiré de : http://stackoverflow.com/a/10791597/6316091
    private double calculateAverage(ArrayList <Integer> liste) {
        Integer sum = 0;
        if(!liste.isEmpty()) {
            sum = calculerSomme(liste);
            return sum.doubleValue() / liste.size();
        }
        return sum;
    }

    private int calculerSomme(ArrayList<Integer> liste) {
        Integer somme = 0;
        if(!liste.isEmpty()) {
            for (Integer mark : liste) {
                somme += mark;
            }
        }
        return somme;
    }

    private int nombreDeVehiculesDansZone(Automate zone){
        int nombre = 0;
        for (Vehicule vehicule : vehicules)
            if (vehicule.getZoneActuelle().equals(zone))
                nombre++;
        return nombre;
    }

    private Vehicule getVehiculeRandomDansZone(Automate zone){
        Random generator = new Random(System.nanoTime());
        ArrayList<Vehicule> vehiculesDansZone = new ArrayList<>();
        for (Vehicule vehicule : vehicules){
            if (vehicule.getZoneActuelle().equals(zone))
                vehiculesDansZone.add(vehicule);
        }

        if (vehiculesDansZone.size() == 0)
            throw new NoSuchElementException();

        return vehiculesDansZone.get(generator.nextInt(vehiculesDansZone.size()));
    }

    private Vehicule getVehiculeLibreDansNode(Node node){
        for (Vehicule vehicule : vehicules){
            if (vehicule.getPositionActuelle().equals(node) && vehicule.peutAccueillirPassagers(1))
                return vehicule;
        }
        return null;
    }

    public ArrayList<Utilisateur> getUtilisateursDuGroupe(Integer numeroDeGroupe){
        ArrayList<Utilisateur> utilisateurs = new ArrayList<>();
        for (Utilisateur utilisateur : this.utilisateurs){
            if (utilisateur.getNumeroDeGroupe().equals(numeroDeGroupe))
                utilisateurs.add(utilisateur);
        }
        return utilisateurs;
    }

    public void deplacerUtilisateur(Utilisateur utilisateur) throws NoSuchElementException {
        Vehicule taxi = getVehiculeLePlusProche(utilisateur.getOrigine());
        if (taxi != null){
            System.out.println("On prend le véhicule positionné à " + taxi.getPositionActuelle().getNom()
                    + " pour déplacer un utilisateur vers " + utilisateur.getDestination().getNom() + ".");
            taxi.setOccupation(true);
            taxi.setPositionActuelle(utilisateur.getDestination());
            taxi.setZoneActuelle(getZoneDeNode(utilisateur.getDestination()));
            taxi.setNombreDePassagers(taxi.getNombreDePassagers() + 1);
            CompteurDeDeplacementsSingleton.getInstance().augmenterNombreDeDeplacementsAvecPassagers();
        }
        else {
            throw new NoSuchElementException("Il n'y a aucun véhicule de disponible pour effectuer le déplacement de "
                    + utilisateur.getOrigine().getNom() + " vers " + utilisateur.getDestination().getNom() + ".");
        }
    }

    private Vehicule getVehiculeLePlusProche(Node node){
        Vehicule vehicule = getVehiculeLibreDansNode(node); //on commence par chercher dans son quartier
        if (vehicule == null){
            //on cherche dans la zone
            vehicule = getVehiculeLibreDansLaZone(getZoneDeNode(node));
            if (vehicule != null){
                CompteurDeDeplacementsSingleton.getInstance().augmenterNombreDeDeplacementsAVide();
            }
        }

        if (vehicule == null) {
            //on cherche dans toutes les zones
            for (Automate zone : zones){
                if (zone.equals(getZoneDeNode(node)))
                    continue;
                vehicule = getVehiculeLibreDansLaZone(zone);
                if (vehicule != null){
                    CompteurDeDeplacementsSingleton.getInstance().augmenterNombreDeDeplacementsAVide();
                    break;
                }
            }
        }

        return vehicule;
    }

    private Vehicule getVehiculeLibreDansLaZone(Automate zone){
        for (Vehicule vehicule : vehicules){
            if (vehicule.getZoneActuelle().equals(zone) && vehicule.peutAccueillirPassagers(1))
                return vehicule;
        }
        return null;
    }

    public void remettreVehiculesDisponibles(){
        for (Vehicule vehicule : vehicules){
            vehicule.setNombreDePassagers(0);
            vehicule.setOccupation(false);
        }
    }

    public void resetClientsEtVehicules() {
        vehicules.clear();
        utilisateurs.clear();
    }

    public boolean contientAutomateValide(){
        return zones.size() > 0;
    }
    public boolean contientClientsEtVehiculesValides(){
        return utilisateurs.size() > 0 && vehicules.size() > 0;
    }
}
