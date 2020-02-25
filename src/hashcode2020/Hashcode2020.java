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

        LibraryProblemSolver libraryProblemSolver;
        Scanner keyboard = new Scanner(System.in);
        List<String[]> result=null;

        while (true) {

            String filename = ReadFromKeyboard(keyboard);
            List<String[]> content= readFromFile(filename);

            if (content != null) {
                libraryProblemSolver = new LibraryProblemSolver(content);
                result = libraryProblemSolver.grind();
            }

            if (result != null) {
                WriteToFile.write(result, filename);
            }
        }
    }

    private static String ReadFromKeyboard(Scanner keyboard) {
        System.out.println("inserisci il nome del file di input contenuto nella cartella input della root: ");
        String[] keyboardInput = keyboard.nextLine().split(" ");
        checkDebug(keyboardInput);
        return keyboardInput[0];
    }

    private static List<String[]> readFromFile(String filename) throws Exception {

        List<String[]> content = ReadFromFile.read(filename);
        return content;
    }

    private static void checkDebug(String[] keyboardInput) {
        if (keyboardInput.length>1) {
            if (keyboardInput[1].equalsIgnoreCase("d")) {
                Utility.setDebug(true);
                return;
            } else {
                System.out.println("Proprietà "+keyboardInput[1]+"non riconosciuta, scrivi <d> per attivare debug");
            }
        }
        Utility.setDebug(false);
    }
}
