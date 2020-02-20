/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hashcode2020;

import java.io.*;
import java.util.List;

/**
 * @author torfab94
 */
public class WriteToFile {

    public void write(List<String[]> content, String path) throws IOException, FileNotFoundException {


        File f = new File("output/" + path);
        if (!f.exists()) {
            f.getParentFile().mkdirs();
            f.createNewFile();
        }
        BufferedWriter realWriter = new BufferedWriter(new FileWriter("output/" + path));
        for (String[] element : content) {
            for (int j = 0; j < element.length; j++) {
                realWriter.write(element[j]);
                if (j < element.length - 1) {
                    realWriter.write(" ");
                }
            }
            realWriter.write("\n");
        }

        realWriter.close();
        return;
    }

}
