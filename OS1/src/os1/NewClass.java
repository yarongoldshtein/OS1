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
    
    public static void main(String[] args)  {
       CThread ct = new CThread();
       ct.getArrayOfReq().add(7);
        System.out.println(ct.getArrayOfReq().toString());
    }
}
