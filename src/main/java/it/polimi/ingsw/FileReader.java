package it.polimi.ingsw;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReader {
    private File file;

    public FileReader(File file){
        this.file=file;
    }

    public void parsamiIlFileCats() throws FileNotFoundException {
        file = new File("C:\\Users\\Rei\\IdeaProjects\\ing-sw-2020-Barjami-Bianchera-Borrelli\\File.txt");
        Scanner sc = new Scanner(file);
        String tutto = sc.next();
        System.out.println(tutto);
    }
}