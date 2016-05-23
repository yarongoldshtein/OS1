/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yaron
 */
public class randomacsses {

    public static void main(String[] args) {
//        try {
//           RandomAccessFile raf = new RandomAccessFile("test.txt", "rw");
//           raf.seek(0);
//           // raf.writeInt(101);
//            for (int i = 0; i < 15; i+=4) {
//                raf.seek(i);
//                raf.writeInt(256);
//            }
//            raf.seek(0);
//            int x = raf.read();
//            System.out.println("" + x);
//            for (int i = 0; i < 120; i+=4) {
//                raf.seek(i);
//                x = raf.read();
//                System.out.println(i+"::" + x);
//            }
//
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(randomacsses.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(randomacsses.class.getName()).log(Level.SEVERE, null, ex);
//        }

        new Thread(new ReadDataBase(5)).start();
        new Thread(new WriteDataBase(5,20,1000)).start();
        new Thread(new UpdateDataBase(5,1000)).start();
    }
}
