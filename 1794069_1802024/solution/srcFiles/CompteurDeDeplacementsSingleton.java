package srcFiles;

/**
 * Created by Gabriel Bourgaulton 2016-11-18.
 */
public class CompteurDeDeplacementsSingleton {
    //Inspiré de : http://thecodersbreakfast.net/index.php?post/2008/02/25/26-de-la-bonne-implementation-du-singleton-en-java

    /** Constructeur privé */
    private CompteurDeDeplacementsSingleton()
    {}

    /** Instance unique non préinitialisée */
    private static CompteurDeDeplacementsSingleton instance = null;

    /** Point d'accès pour l'instance unique du singleton */
    public static CompteurDeDeplacementsSingleton getInstance()
    {
        if (instance == null)
            instance = new CompteurDeDeplacementsSingleton();
        return instance;
    }

    private Integer deplacementsAVide = 0;
    private Integer deplacementsAvecPassagers = 0;

    public Integer getNombreDeDeplacementsAVide(){
        return deplacementsAVide;
    }
    public Integer getNombreDeDeplacementsAvecPassagers(){
        return deplacementsAvecPassagers;
    }

    public void augmenterNombreDeDeplacementsAVide(){
        deplacementsAVide++;
    }
    public void augmenterNombreDeDeplacementsAvecPassagers(){
        deplacementsAvecPassagers++;
    }

    public void mettreDeplacementsAZero(){
        deplacementsAVide = 0;
        deplacementsAvecPassagers = 0;
    }

}
