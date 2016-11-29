package unitTests;

import srcFiles.*;

import static org.junit.Assert.*;

/**
 * Created by Gabriel Bourgault on 2016-11-28.
 */
public class GestionnaireTest {

    /* Zone 1 : H1A0A1, H1A0A2
       Zone 2 : H1A5B5, H1A5B6
       Zone 3 : H1C1A9, H1C1B1
       Zone 4 : H1C1J6, H1C1J7 */

    private String[] utilisateur1 = { "H1A0A1", "H1A0A2", "1" }; //1 to 1
    private String[] utilisateur2 = { "H1A0A2", "H1A0A1", "1" }; //1 to 1
    private String[] utilisateur3 = { "H1A5B5", "H1A5B6", "1" }; //2 to 2
    private String[] utilisateur4 = { "H1A0A1", "H1A5B5", "2" }; //1 to 2

    private String[][] uGroupe1 = { utilisateur1, utilisateur2 };
    private String[][] uGroupe2 = { utilisateur1, utilisateur3, utilisateur4 };
    private String[][] uGroupe3 = { utilisateur1, utilisateur2, utilisateur2, utilisateur4 };

    private String[] vehicules1 = { "H1A0A1", "H1A5B5", "H1C1A9", "H1C1J6" }; //1-1-1-1
    private String[] vehicules2 = { "H1A0A1", "H1A0A1" }; //2-0-0-0
    private String[] vehicules3 = { "H1A0A1", "H1A0A2" }; //2-0-0-0
    private String[] vehicules4 = { "H1A5B5", "H1A5B5", "H1C1J6" }; //0-2-0-1
    private String[] vehicules5 = { "H1C1J6", "H1C1J6", "H1C1J7", "H1C1J7", "H1C1J6" }; //0-0-0-5
    private String[] vehicules6 = { "H1A0A1", "H1A0A1", "H1A5B5", "H1A5B5",
            "H1C1A9", "H1C1A9", "H1C1A9", "H1C1A9", "H1C1A9", "H1C1J6", "H1C1J6" }; //2-2-5-2
    private String[] vehicules7 = { "H1C1J6" }; //0-0-0-1
    private String[] vehicules8 = { "H1A0A1", "H1A0A1", "H1A0A1", "H1A0A1", "H1A0A1", "H1A0A2", "H1A0A2", "H1A0A2", "H1A0A2", "H1A0A2",
            "H1A5B5", "H1A5B6", "H1C1J6" }; //10-2-0-1
    private String[] vehicules9 = { "H1A0A1", "H1A0A2", "H1A5B5", "H1A5B5", "H1A5B5", "H1A5B5", "H1A5B6", "H1A5B6", "H1A5B6",
            "H1C1A9", "H1C1A9", "H1C1A9", "H1C1A9", "H1C1B1", "H1C1J6" }; //2-7-5-1
    private String[] vehicules10 = { "H1A0A1", "H1A0A1", "H1A0A2",
            "H1A5B5", "H1A5B5", "H1A5B5", "H1A5B5", "H1A5B6",
            "H1C1A9", "H1C1A9", "H1C1A9", "H1C1A9", "H1C1B1", "H1C1B1", "H1C1B1", "H1C1B1",
            "H1C1J6", "H1C1J6", "H1C1J6", "H1C1J7", "H1C1J7" }; //3-5-8-5

    private Gestionnaire gestionnaire = new Gestionnaire();

    private void setup() {
        String folderNameGab = "C:\\Users\\Gabriel\\OneDrive\\Documents\\.Travaux d'école\\Université\\Session 3\\LOG2810\\TP2\\solution\\testFiles\\";
        gestionnaire.creerLexiques(folderNameGab);
    }

    @org.junit.Test
    public void lectureFolder() throws Exception {
        setup();
        assertNotEquals(null, gestionnaire.getCarte());
        assertNotEquals(0, gestionnaire.getCarte().getZones().size());
    }

    @org.junit.Test
    public void entrerDesUtilisateursParGroupes() throws Exception {
        lectureFolder();

        //Zone1 to Zone1
        Utilisateur user1 = new Utilisateur(gestionnaire.getCarte().getNodeExistante("H1A0A2"), gestionnaire.getCarte().getNodeExistante("H1A0A5"), 1);
        //Zone3 to Zone3
        Utilisateur user2 = new Utilisateur(gestionnaire.getCarte().getNodeExistante("H1B5P2"), gestionnaire.getCarte().getNodeExistante("H1B5M8"), 1);

        //Zone1 to Zone3
        Utilisateur user3 = new Utilisateur(gestionnaire.getCarte().getNodeExistante("H1A0A9"), gestionnaire.getCarte().getNodeExistante("H1B5M8"), 2);
        //Zone2 to Zone3
        Utilisateur user4 = new Utilisateur(gestionnaire.getCarte().getNodeExistante("H1A 5B5"), gestionnaire.getCarte().getNodeExistante("H1B5M8"), 2);

        //Zone3 to Zone1
        Utilisateur user5 = new Utilisateur(gestionnaire.getCarte().getNodeExistante("H1B 5M8"), gestionnaire.getCarte().getNodeExistante("H1A0A1"), 3);

        gestionnaire.getCarte().ajouterUtilisateur(user1);
        gestionnaire.getCarte().ajouterUtilisateur(user2);
        gestionnaire.getCarte().ajouterUtilisateur(user3);
        gestionnaire.getCarte().ajouterUtilisateur(user4);
        gestionnaire.getCarte().ajouterUtilisateur(user5);

        assertEquals(2, gestionnaire.getCarte().getUtilisateursDuGroupe(1).size());
        assertEquals(2, gestionnaire.getCarte().getUtilisateursDuGroupe(2).size());
        assertEquals(1, gestionnaire.getCarte().getUtilisateursDuGroupe(3).size());
    }

    @org.junit.Test
    public void testerEquilibrage() throws Exception {
        setup();

        String[][] groupesVehicules = { vehicules1, vehicules2, vehicules3, vehicules4, vehicules5, vehicules6,
                vehicules7, vehicules8, vehicules9, vehicules10 };

        for (int i = 0; i < groupesVehicules.length; i++){
            entrerVehiculesDeTest(groupesVehicules[i]);
            equilibrerFlotteEtVerifier();
        }
    }

    private void entrerVehiculesDeTest(String[] vehicules){
        gestionnaire.getCarte().resetVehicules();
        for (String vehicule : vehicules) {
            Vehicule vehiculeAjoute = new Vehicule(false,
                    gestionnaire.getCarte().getZoneDeNode(gestionnaire.getCarte().getNodeExistante(vehicule)),
                    gestionnaire.getCarte().getNodeExistante(vehicule));
            gestionnaire.getCarte().ajouterVehicule(vehiculeAjoute);
        }
    }

    private void entrerUtilisateursDeTest(String[][] utilisateurs){
        gestionnaire.getCarte().resetClients();
        for (String[] utilisateur : utilisateurs){
            Utilisateur utilisateurAjoute = new Utilisateur(
                    gestionnaire.getCarte().getNodeExistante(utilisateur[0]),
                    gestionnaire.getCarte().getNodeExistante(utilisateur[1]),
                    Integer.parseInt(utilisateur[2]));
            gestionnaire.getCarte().ajouterUtilisateur(utilisateurAjoute);
        }
    }

    private void equilibrerFlotteEtVerifier() {
        gestionnaire.equilibrerFlotte();
        Carte carte = gestionnaire.getCarte();

        Integer max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
        for (int i = 0; i < carte.getZones().size(); i++){
            Integer nbVehicules = carte.nombreDeVehiculesDansZone(carte.getZones().get(i));
            if (nbVehicules >= 0 && nbVehicules < min)
                min = nbVehicules;
            if (nbVehicules >= 0 && nbVehicules > max)
                max = nbVehicules;
        }

        assertTrue(max - min <= 1);
    }

    @org.junit.Test
    public void testerSimulation() throws Exception {
        setup();
        String[][][] utilisateurs = { uGroupe1, uGroupe2, uGroupe3 };
        String[] vehicules = vehicules1;
        CompteurDeDeplacementsSingleton compteurSingleton = CompteurDeDeplacementsSingleton.getInstance();
        compteurSingleton.mettreDeplacementsAZero();

        for (int i = 0; i < utilisateurs.length; i++){
            System.out.println("\n----- TEST #" + (i + 1) + " ----- : ");
            entrerVehiculesDeTest(vehicules);
            entrerUtilisateursDeTest(utilisateurs[i]);

            assertEquals((Integer)0, compteurSingleton.getNombreDeDeplacementsAVide());
            assertEquals((Integer)0, compteurSingleton.getNombreDeDeplacementsAvecPassagers());

            gestionnaire.lancerSimulation();
            //gestionnaire.printTableauxResultats();

            //assertEquals((Integer)2, compteurSingleton.getNombreDeDeplacementsAVide());
            //Integer deplacementsAvecPassagers = (utilisateurs[i].length > vehicules.length) ? vehicules.length : utilisateurs[i].length;
            assertEquals((Integer)utilisateurs[i].length, compteurSingleton.getNombreDeDeplacementsAvecPassagers());
            compteurSingleton.mettreDeplacementsAZero();
        }

    }
}
