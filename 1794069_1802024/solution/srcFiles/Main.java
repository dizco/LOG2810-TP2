package srcFiles;

public class Main
{
    public static void main(String[] args)
    {
        Gestionnaire gestionnaire = new Gestionnaire();
        while(gestionnaire.afficherMenu()){} //affiche le menu tant que l'utilisateur ne sélectionne pas "(d)"
    }
}
