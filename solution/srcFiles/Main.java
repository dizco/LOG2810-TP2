package srcFiles;

import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Main
{
    public static void main(String[] args)
    {
        Gestionnaire gestionnaire = new Gestionnaire();
        while(gestionnaire.afficherMenu()){}
    }
}
