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
       Cache c = new Cache(0, 5);
        for (int i = 0; i < 10; i++) {
            cacheNode cn = new cacheNode(i,i,i);
            c.insert(cn);
        }
//          c.upDateCache();
    }
}