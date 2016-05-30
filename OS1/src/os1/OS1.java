/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author אליצור
 */
public class OS1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        File dir = new File("DataBase");
        final File[] dbs = dir.listFiles();
        if (dir.exists()) {
            for (File file : dbs) {
                file.delete();
            }
        }

        new Thread(new Server(100, 1, 30, 5, 5)).start();
        File f;
        for (int i = 1; i <= 1000; i++) {
            String nameOfFile = "ProbabilityFiles/" + i + ".txt";
            f = new File(nameOfFile);
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String str = br.readLine();
            String[] splits = str.split(",");
            int r1 = Integer.parseInt(splits[0]);
            int r2 = Integer.parseInt(splits[1]);
            new Thread(new Client(r1, r2, nameOfFile)).start();
        }

    }
}
