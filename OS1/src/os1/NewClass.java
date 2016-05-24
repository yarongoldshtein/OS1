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
public class NewClass {
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        File f;
        for (int i = 0; i < 1001; i++) {
            f = new File("ProbabilityFiles/"+i+".txt");
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String str = br.readLine();
            String [] splits = str.split(",");
            int t = Integer.parseInt(splits[0]);
            if(t<=20){
                System.out.println(i+"t = "+t);
            }
        }
                
    }
}
