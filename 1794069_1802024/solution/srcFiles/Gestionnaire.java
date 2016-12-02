package srcFiles;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.Stream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by Gabriel Bourgault on 2016-11-15.
 */
public class Gestionnaire {
    private Carte carte;
    private enum OptionMenu { CreerZones, EntrerClientsEtVehicules, DemarrerSimulation, Quitter, Invalide }

    public Gestionnaire(){
        carte = new Carte();
    }

    public Carte getCarte() {
        return carte;
    }

    //C1. Ecrire une fonction ”creerLexiques()” qui permet de lire
    //les fichiers textes correspondants aux zones des véhicules.
    //Inspiré de : http://stackoverflow.com/a/1846349/6316091
    public void creerLexiques(String folderName) {
        Carte nouvelleCarte = new Carte();
        try(Stream<Path> paths = Files.walk(Paths.get(folderName))) {
            paths.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    System.out.println("Chargement du fichier : " + filePath);
                    try {
                        nouvelleCarte.ajouterZone(lireFichier(filePath));
                    }
                    catch (IOException ex){
                        System.err.println("Erreur lors de la lecture du fichier " + filePath);
                        return;
                    }
                }
            });
            carte = nouvelleCarte; //on a lu la carte avec succès
        }
        catch (IOException e){
            System.err.println("Erreur lors de la lecture des fichiers.");
            return;
        }
    }

    //Voir : http://stackoverflow.com/a/4716623/6316091
    private Automate lireFichier(Path fileName) throws IOException {
        Automate automate = new Automate(fileName.toString());
        BufferedReader br = new BufferedReader(new FileReader(fileName.toString()));
        try {
            String line = br.readLine();
            while (line != null) {
                if (!line.equals(""))
                    automate.ajouter(line);
                line = br.readLine();
            }
        } finally {
            br.close();
        }

        return automate;
    }

    //C2. Ecrire une fonction ”equilibrerFlotte()” qui permet d’équilibrer
    //le nombre de véhicules pour chaque zone.
    public void equilibrerFlotte(){
        //aucun paramètre a priori
        carte.equilibrerZones();
    }

    //C3. Ecrire une fonction ”lancerSimulation()” qui démarre la simulation  ´
    //une fois que l’utilisateur a rentré les données nécessaires.
    public void lancerSimulation() {
        //peut prendre comme paramètres les données de simulation entrées par l'utilisateur

        for (int i = 1; i <= carte.getNumeroDeGroupeMax(); i++){
            ArrayList<Utilisateur> utilisateurs = carte.getUtilisateursDuGroupe(i);
            if (utilisateurs.size() == 0)
                continue;

            System.out.println(utilisateurs.size() + " utilisateur(s) dans le groupe #" + i + ".");
            for (Utilisateur user : utilisateurs){
                try {
                    carte.deplacerUtilisateur(user);
                }
                catch (NoSuchElementException ex){
                    System.err.println(ex.getMessage());
                    System.out.println("On perd 1 usager car on n'a pas de véhicule pour lui répondre.");
                }
            }

            carte.remettreVehiculesDisponibles();
            equilibrerFlotte();
        }
    }

    private void printTableauxResultats(){
        CompteurDeDeplacementsSingleton compteurSingleton = CompteurDeDeplacementsSingleton.getInstance();
        System.out.println("Nombre de déplacements à vide : " + compteurSingleton.getNombreDeDeplacementsAVide());
        System.out.println("Nombre de déplacements avec passagers : " + compteurSingleton.getNombreDeDeplacementsAvecPassagers());
    }

    /*C4. Faire une interface qui affiche le menu suivant :
        (a) Créer les zones.
        (b) Entrer les clients et les v´ehicules.
        (c) Démarrer la simulation.
        (d) Quitter
       */
    public Boolean afficherMenu() {
        afficherOptionsDuMenu(); //afficher le menu tant que l'option (d) n'a pas été choisie
        OptionMenu optionChoisie = lireOptionChoisie(); //valider les index (a) à (d) seulement

        switch (optionChoisie){
            case CreerZones:
                creerZones();
                break;
            case EntrerClientsEtVehicules:
                if (carte.contientAutomateValide())
                    entrerClientsEtVehicules();
                else
                    System.out.println("Veuillez vous assurer d'avoir complété l'étape (a) avant d'entrer les informations clients et véhicules.");
                break;
            case DemarrerSimulation:
                if (carte.contientAutomateValide() && carte.contientClientsEtVehiculesValides()){
                    System.out.println("Début de la simulation.");
                    CompteurDeDeplacementsSingleton compteurSingleton = CompteurDeDeplacementsSingleton.getInstance();
                    lancerSimulation();
                    printTableauxResultats();
                    compteurSingleton.mettreDeplacementsAZero();
                }
                else
                    System.out.println("Veuillez vous assurer d'avoir complété les étapes (a) et (b) avant de lancer une simulation.");
                break;
            case Quitter:
                System.out.println("Au revoir.");
                return false;
            default:
                System.out.println("Option invalide, veuillez réessayer. Les options valides sont (a), (b), (c) et (d).");
        }

        return true;
    }

    private void afficherOptionsDuMenu() {
        System.out.println("\nSélectionnez une option parmi les suivantes : ");
        System.out.print("(a) Créer les zones.\n" +
                "(b) Entrer les clients et les véhicules.\n" +
                "(c) Démarrer la simulation.\n" +
                "(d) Quitter\n" +
                "Votre choix : ");
    }

    private OptionMenu lireOptionChoisie() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            String s = br.readLine();
            switch (s) {
                case "(a)":
                    return OptionMenu.CreerZones;
                case "(b)":
                    return OptionMenu.EntrerClientsEtVehicules;
                case "(c)":
                    return OptionMenu.DemarrerSimulation;
                case "(d)":
                    return OptionMenu.Quitter;
                default:
                    return OptionMenu.Invalide;
            }
        }
        catch (IOException ex) {
            //TODO: Avertir usager
        }
        return OptionMenu.Invalide;
    }

    private void creerZones() {
        //TODO: Code temporaire pour fins de tests
        /*System.out.println("Usage d'un folder temporaire pour charger les fichiers de test.");
        String temp = "C:\\Users\\Gabriel\\OneDrive\\Documents\\.Travaux d'école\\Université\\Session 3\\LOG2810\\TP2\\solution\\testFiles\\";
        String tempFred = "C:\\Users\\Frédéric\\Documents\\#École\\#Université\\Polytechnique\\#Session 04-Automne 2016\\LOG2810-Structures Discrètes\\TP\\LOG2810-TP2\\solution\\testFiles";
        creerLexiques(temp);
        return;*/

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Entrer le nom de dossier complet (ex : C:\\Users\\Gabriel\\Documents\\LOG2810\\TP2\\solution\\testFiles\\) : ");
        try {
            String folderName = br.readLine();
            creerLexiques(folderName);
        }
        catch(IOException ex){
            System.out.println("Erreur lors de la lecture. Veuillez entrer un nom de dossier valide.");
        }
    }

    private enum ModeLecture { Manuel, Fichier, Invalide }

    private void entrerClientsEtVehicules() {
        carte.resetClientsEtVehicules();
        String regexPostalCode = "^[A-Z][0-9][A-Z] ?[0-9][A-Z][0-9]$";

        ModeLecture modeDeLecture = ModeLecture.Manuel; //demanderModeDeLecture();
        if (modeDeLecture.equals(ModeLecture.Manuel)) {
            Integer nombreDeVehicules = demanderNombreDeVehicules();
            for (int i = 0; i < nombreDeVehicules; i++){
                System.out.println("Véhicule #" + (i + 1) + " / " + nombreDeVehicules + ".");
                Vehicule vehicule = demanderNouveauVehicule(regexPostalCode);
                carte.ajouterVehicule(vehicule);
            }
            System.out.println("");

            Integer nombreDUtilisateurs = demanderNombreDUtilisateurs();
            for (int i = 0; i < nombreDUtilisateurs; i++){
                System.out.println("Utilisateur #" + (i + 1) + " / " + nombreDUtilisateurs + ".");
                Utilisateur utilisateur = demanderNouvelUtilisateur(regexPostalCode);
                carte.ajouterUtilisateur(utilisateur);
            }
            System.out.println("");
        }
        else if (modeDeLecture.equals(ModeLecture.Fichier)){
            //TODO: Pas obligé selon Alexandre
        }
        else{
            System.out.println("Vous avez saisi une commande invalide, l'action n'a pu être traitée.");
        }
    }

    //Fonction inutilisée par manque de temps
    private ModeLecture demanderModeDeLecture(){
        Scanner reader = new Scanner(System.in);
        ModeLecture modeLecture = ModeLecture.Invalide;
        boolean isValide = false;
        while (!isValide){
            System.out.println("Sélectionnez le mode d'entrée des données, entrez \"m\" pour saisie manuelle, ou \"f\" pour saisie à l'aide d'un fichier");
            try{
                String mode = reader.nextLine();
                if (mode.toUpperCase().equals("M")){
                    modeLecture = ModeLecture.Manuel;
                    isValide = true;
                }
                else if (mode.toUpperCase().equals("F")){
                    modeLecture = ModeLecture.Fichier;
                    isValide = true;
                }
            }catch(InputMismatchException e){
                System.out.println("Sélectionnez le mode d'entrée des données, entrez \"m\" pour saisie manuelle, ou \"f\" pour saisie à l'aide d'un fichier");
            }
        }
        return modeLecture;
    }

    //On demande à l'utilisateur d'entrer le nombre de véhicules, on s'assure qu'il correspond à un entier
    private Integer demanderNombreDeVehicules(){
        Scanner reader = new Scanner(System.in);
        Integer nombreDeVehicules = 0;
        boolean isValide = false;
        while (!isValide){
            try{
                System.out.println("Entrez le nombre de véchicules désiré (>0 et <2147483647) : ");
                nombreDeVehicules = reader.nextInt();
                if(nombreDeVehicules > 0 && nombreDeVehicules < Integer.MAX_VALUE){
                    isValide = true;
                }else{
                    System.out.println("Entrez un nombre de véhicules valide.");
                    reader.next();
                }
            }catch(InputMismatchException e){
                System.out.println("Entrez un nombre de véhicules valide.");
                reader.next();
            }
        }
        return nombreDeVehicules;
    }

    private Vehicule demanderNouveauVehicule(String regexPostalCode){
        Scanner reader = new Scanner(System.in);
        Vehicule vehicule = new Vehicule();

        //On demande une zone de départ valide pour le véhicule et on s'assure qu'elle correspond à un code postal
        boolean zoneDepartIsValide = false;
        while(!zoneDepartIsValide){
            System.out.println("Entrez la position actuelle du véhicule (ex: H1A0A1) : ");
            String positionVehicule = reader.nextLine();
            if(!positionVehicule.toUpperCase().matches(regexPostalCode)){
                System.out.println("Entrez une code postal de quartier de départ du véhicule valide.");
            } else {
                if(carte.getNodeExistante(positionVehicule)!= null){
                    zoneDepartIsValide=true;
                    vehicule.setZoneActuelle(carte.getZoneDeNode(carte.getNodeExistante(positionVehicule)));
                    vehicule.setPositionActuelle(carte.getNodeExistante(positionVehicule));
                }else{
                    System.out.println("Entrez un code postal de de départ valide.");
                }
            }
        }

        //vehicule.setNombreDePlacesTotales(1);
        return vehicule;
    }

    private Integer demanderNombreDUtilisateurs(){
        Scanner reader = new Scanner(System.in);
        Integer nombreDUtilisateurs = 0;
        boolean isValide = false;
        while (!isValide){
            try{
                System.out.println("Entrez le nombre d'utilisateurs désiré (>0 et <2147483647) : ");
                nombreDUtilisateurs = reader.nextInt();
                if(nombreDUtilisateurs > 0 && nombreDUtilisateurs < Integer.MAX_VALUE){
                    isValide = true;
                }else{
                    System.out.println("Entrez un nombre d'utilisateurs valide.");
                    reader.next();
                }
            }catch(InputMismatchException e){
                System.out.println("Entrez un nombre de d'utilisateurs valide.");
                reader.next();
            }
        }
        return nombreDUtilisateurs;
    }

    private Utilisateur demanderNouvelUtilisateur(String regexPostalCode){
        Scanner reader = new Scanner(System.in);
        Utilisateur utilisateur = new Utilisateur();

        //On demande tout d'abord à l'utilisateur d'entrer le code postal de son point de départ
        boolean departIsValide = false;
        while(!departIsValide){
            System.out.println("Entrez le code postal du point de départ (ex : H1A0A1) : ");
            String depart = reader.nextLine();
            if(!depart.toUpperCase().matches(regexPostalCode)){ //on valide avec un regex que l'utilisateur entre un code postal valide constitué de chiffres et lettres en alternance
                System.out.println("Entrez un code postal de départ valide.");
            }
            else{
                if(carte.getNodeExistante(depart) != null){
                    departIsValide = true;
                    utilisateur.setOrigine(carte.getNodeExistante(depart));
                }
                else {
                    System.out.println("Le point de départ que vous avez entré n'est pas valide.");
                }
            }
        }

        //On demande ensuite à l'utilisateur d'entrer le code postal de destination
        boolean destinationIsValide = false;
        while(!destinationIsValide){
            System.out.println("Entrez le code postal du point de destination : ");
            String destination = reader.nextLine();
            if(!destination.toUpperCase().matches(regexPostalCode)){
                System.out.println("Entrez un code postal de destination valide.");
            }else{
                if(carte.getNodeExistante(destination) != null){
                    destinationIsValide=true;
                    utilisateur.setDestination(carte.getNodeExistante(destination));
                }
                else{
                    System.out.println("Le point de destination que vous avez entré n'est pas valide.");
                }
            }
        }

        //On demande à l'utilisateur d'entrer son numéro de groupe, on s'assure que le numéro de groupe correspond à un entier
        boolean numGroupeIsValide = false;
        while(!numGroupeIsValide){
            try{
                System.out.println("Entrez un numéro de groupe (>0 et <2147483647) : ");
                Integer numGroupe = reader.nextInt();
                if(numGroupe > 0 && numGroupe < Integer.MAX_VALUE){
                    numGroupeIsValide=true;
                    utilisateur.setNumeroDeGroupe(numGroupe);
                }
                else {
                    System.out.println("Entrez un numéro de groupe valide.");
                    reader.next();
                }
            }catch(InputMismatchException e){
                System.out.println("Entrez un numéro de groupe valide.");
                reader.next();
            }
        }
        return utilisateur;
    }
}
