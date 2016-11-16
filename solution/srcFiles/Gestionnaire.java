package srcFiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by Gabriel Bourgault on 2016-11-15.
 */
public class Gestionnaire {
    //C1. Ecrire une fonction ”creerLexiques()” qui permet de lire
    //les fichiers textes correspondants aux zones des v´ehicules.
    public void creerLexiques(String folderName){
        try(Stream<Path> paths = Files.walk(Paths.get(folderName))) {
            paths.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    //TODO: lire contenu du fichier et l'entrer dans l'automate correspondant

                    System.out.println(filePath);
                }
            });
        }
        catch (IOException e){
            System.err.println("Erreur lors de la lecture des fichiers.");
        }
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
        (a) Cr´eer les zones.
        (b) Entrer les clients et les v´ehicules.
        (c) D´emarrer la simulation.
        (d) Quitter
       */
    public Boolean afficherMenu() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //TODO: Afficher pour le vrai menu

        //afficher le menu tant que l'option (d) n'a pas été choisie
        //valider les index (a) à (d) seulement

        //option (a) doit demander de rentrer le folder où les fichiers de zone sont situés
        System.out.println("Entrer le nom de folder (ex : C:\\Users\\Gabriel\\OneDrive\\Documents\\.Travaux d'école\\Université\\Session 3\\LOG2810\\TP2\\solution) : ");
        try {
            String s = br.readLine();
            //int i = Integer.parseInt(br.readLine());
            creerLexiques(s);
        }
        catch(IOException ex){
            //TODO: Avertir usager
        }

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
        return false;
    }
}
