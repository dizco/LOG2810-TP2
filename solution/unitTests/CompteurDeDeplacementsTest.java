package unitTests;

import srcFiles.CompteurDeDeplacementsSingleton;

import static org.junit.Assert.*;

/**
 * Created by Gabriel on 2016-11-28.
 */
public class CompteurDeDeplacementsTest {
    @org.junit.Test
    public void testGetInstance() throws Exception {
        CompteurDeDeplacementsSingleton compteur1 = CompteurDeDeplacementsSingleton.getInstance();
        CompteurDeDeplacementsSingleton compteur2 = CompteurDeDeplacementsSingleton.getInstance();
        assertEquals(compteur1, compteur2);
    }

    @org.junit.Test
    public void testAugmenterDeplacements() throws Exception {
        CompteurDeDeplacementsSingleton compteur = CompteurDeDeplacementsSingleton.getInstance();
        assertEquals((Integer)0, compteur.getNombreDeDeplacementsAVide());
        assertEquals((Integer)0, compteur.getNombreDeDeplacementsAvecPassagers());
        compteur.augmenterNombreDeDeplacementsAVide();
        assertEquals((Integer)1, compteur.getNombreDeDeplacementsAVide());
        compteur.augmenterNombreDeDeplacementsAvecPassagers();
        assertEquals((Integer)1, compteur.getNombreDeDeplacementsAvecPassagers());
    }
}
