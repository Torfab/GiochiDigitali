/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hashcode2020;

import java.util.List;
import java.util.Scanner;

/**
 * @author torfab94
 */
public class Hashcode2020 {

    private String inputPath;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        ReadFromFile reader = new ReadFromFile();
        WriteToFile writer = new WriteToFile();
        MathSorcerer mathSorcerer;


        Scanner keyboard = new Scanner(System.in);
        while (true) {
            System.out.println("inserisci il nome del file di input contenuto nella cartella input della root: ");
            String filename = keyboard.nextLine();
            List<String[]> result = null;

            List<String[]> content = reader.read(filename);

            if (content != null) {
                mathSorcerer=new MathSorcerer(content);
                result = mathSorcerer.grind();
            }

            if (result != null) {
                writer.write(result, filename);
            }

        }

    }

}
