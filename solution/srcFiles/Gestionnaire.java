package srcFiles;

import com.sun.org.apache.xpath.internal.SourceTree;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.InputMismatchException;
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

    //constante taille maximale INT, nombre maximal de passager et de véhicules qu'il est possible d'avoir à la fois
    int MAXINT=2147483647;


    //C1. Ecrire une fonction ”creerLexiques()” qui permet de lire
    //les fichiers textes correspondants aux zones des v´ehicules.
    //Inspiré de : http://stackoverflow.com/a/1846349/6316091
    public void creerLexiques(String folderName) {
        Carte nouvelleCarte = new Carte();
        try(Stream<Path> paths = Files.walk(Paths.get(folderName))) {
            paths.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    System.out.println(filePath); //TODO: retirer
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
        Automate automate = new Automate();
        //TODO: lire contenu du fichier et l'entrer dans l'automate correspondant

        BufferedReader br = new BufferedReader(new FileReader(fileName.toString()));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
                if (line != null && !line.equals(""))
                    automate.ajouter(line);
            }
            String everything = sb.toString();
        } finally {
            br.close();
        }

        return automate;
    }

    //C2. Ecrire une fonction ”equilibrerFlotte()” qui permet d’´equilibrer
    //le nombre de v´ehicules pour chaque zone.
    public void equilibrerFlotte(){
        //aucun paramètre a priori

    }

    //C3. Ecrire une fonction ”lancerSimulation()” qui d´emarre la simulation  ´
    //une fois que l’utilisateur a rentr´e les donn´ees n´ecessaires.
    public void lancerSimulation() {
        //peut prendre comme paramètres les données de simulation entrées par l'utilisateurr

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
                //TODO:
                entrerClientsEtVehicules();
                break;
            case DemarrerSimulation:
                //TODO:
                break;
            case Quitter:
                System.out.println("Au revoir.");
                return false;
            default:
                System.out.println("Option invalide, veuillez réessayer. Les options valides sont (a), (b), (c) et (d).");
        }

        //option (a) doit demander de rentrer le folder où les fichiers de zone sont situés
        //option (b) permet de rentrer les infos concernant les clients et les véhicules
            //Utilisateur : point de départ, destination et numéro de groupe
            //Véhicule : occupation, zone actuelle, position actuelle
            //doit pouvoir recevoir les infos dans un fichier txt
            //Option (a) doit avoir été complétée avant
        //option (c) permet de démarrer la simulation
            //Option (b) doit avoir été complétée avant
        //Lorsque fini, afficher les 2 tableaux montrant les résultats
            //1er : Pour chaque véhicule, nb de trajets avec un occupant et nb de trajets à vide
            //2e : Nombre de voitures par zones au début et à la fin de la sim
        //option(d) permet de quitter
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
        System.out.println("Usage d'un folder temporaire pour charger les fichiers de test.");
        String temp = "C:\\Users\\Gabriel\\OneDrive\\Documents\\.Travaux d'école\\Université\\Session 3\\LOG2810\\TP2\\solution\\testFiles\\";
        creerLexiques(temp);
        return;

        /*BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Entrer le nom de folder (ex : C:\\Users\\Gabriel\\OneDrive\\Documents\\.Travaux d'école\\Université\\Session 3\\LOG2810\\TP2\\solution\\testFiles\\) : ");
        try {
            String folderName = br.readLine();
            creerLexiques(folderName);
        }
        catch(IOException ex){
            //TODO: Avertir usager
        }*/
    }

    private void entrerClientsEtVehicules() {
        int numGroupe,nombrePassagers, nombreVehicules=0;
        String mode="",depart, destination,zoneDepartVehicule="";
        Scanner reader = new Scanner(System.in);
        String regexPostalCode = "^[A-Z][0-9][A-Z] ?[0-9][A-Z][0-9]$";


        System.out.println("Sélectionnez le mode d'entrée des données, entrez ''m'' pour saisie manuelle, ou ''f'' pour saisie à l'aide d'un fichier");
        try{
            mode=reader.nextLine();
        }catch(InputMismatchException e){
            System.out.println("Sélectionnez un mode de saisie de données, ''m'' pour une saisie manuelle, ''f'' pour saisie à partir d'un fichier");
        }

        if(mode.equals("m") || mode.equals("M")){

                //On demande tout d'abord à l'utilisateur d'entrer le code postal de son point de départ
                boolean departIsValide=false;
                while(!departIsValide){
                    System.out.println("Entrez le code postal du point de départ:");
                    depart=reader.nextLine();
                   if(!depart.toUpperCase().matches(regexPostalCode)){ //on valide avec un regex que l'utilisateur entre un code postal valide constitué de chiffres et lettres en alternance
                        System.out.println("Entrez un code postal de départ valide");
                    }else{
                        departIsValide=true;
                    }
                }

                //On demande ensuite à l'utilisateur d'entrer le code postal de destination
                boolean destinationIsValide=false;
                while(!destinationIsValide){
                    System.out.println("Entrez le code postal du point de destination:");
                    destination=reader.nextLine();
                    if(!destination.toUpperCase().matches(regexPostalCode)){
                        System.out.println("Entrez un code postal de destination valide");
                    }else{
                        destinationIsValide=true;
                    }
                }


            //On demande à l'utilisateur d'entrer son numéro de groupe, on s'assure que le numéro de groupe correspond à un entier
            boolean numGroupeIsValide=false;
            while(!numGroupeIsValide){
                   try{
                       System.out.println("Entrez un numéro de groupe:");
                       numGroupe=reader.nextInt();
                      if(numGroupe>0 && numGroupe<MAXINT){
                           numGroupeIsValide=true;
                          reader.nextLine();////////////////////////////////////////
                       }else{
                           System.out.println("Entrez un numéro de groupe valide");
                       }
                   }catch(InputMismatchException e){
                       System.out.println("Entrez un numéro de groupe valide");
                       reader.nextLine();
                   }

            }

            //On demande une zonde de départ valide pour le véhicule et on s'assure qu'elle correspond à un code postal
            boolean zoneDepartIsValide=false;
            while(!zoneDepartIsValide){
                    System.out.println("Entrez la zone de départ du véhicule");
                    zoneDepartVehicule=reader.nextLine();
                if(!zoneDepartVehicule.toUpperCase().matches(regexPostalCode)){
                    System.out.println("Entrez une code postal de zone de départ du véhicule valide");
                }else{
                    zoneDepartIsValide=true;
                }

            }

            //On demande à l'utilisateur d'entrer le nombre de passagers, on s'assure qu'il correspond à un entier
            boolean nombrePassagersIsValide=false;
            while(!nombrePassagersIsValide){
                try{
                    System.out.println("Entrez le nombre de passagers:");
                    nombrePassagers=reader.nextInt();
                    if(nombrePassagers>0 && nombrePassagers<MAXINT){
                       nombrePassagersIsValide=true;
                    }else{
                        System.out.println("Entrez un nombre de passagers valide");
                    }
                }catch(InputMismatchException e){
                    System.out.println("Entrez un nombre de passagers valide");
                    reader.nextLine();
                }

            }


            //On demande à l'utilisateur d'entrer le nombre de véhicules, on s'assure qu'il correspond à un entier
            boolean nombreVehiculesIsValide=false;
            while(!nombreVehiculesIsValide){
                try{
                    System.out.println("Entrez le nombre de véchicules désiré:");
                    nombreVehicules=reader.nextInt();
                    if(nombreVehicules>0 && nombreVehicules<MAXINT){
                        nombreVehiculesIsValide=true;
                    }else{
                        System.out.println("Entrez un nombre de véhicules valide");

                    }
                }catch(InputMismatchException e){
                    System.out.println("Entrez un nombre de véhicules valide");
                    reader.nextLine();
                }

            }

        }else if(mode.equals("f")|| mode.equals("F")){


        }else{
            System.out.println("Sélectionnez un mode de saisie de données, ''m'' pour une saisie manuelle, ''f'' pour saisie à partir d'un fichier");
        }
    }


}
