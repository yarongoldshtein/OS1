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
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author אליצור
 */
public class OS1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Thread(new Server(5,10)).start();
        new Thread(new Client()).start();
        new Thread(new Client()).start();
//        File dir = new File("DataBase");
//        dir.mkdir();
//        for (int i = 0; i < 5; i++) {
//            String nameOfFile = dir + "\\testNum" + i + ".txt";
//            try {
//                RandomAccessFile raf = new RandomAccessFile(nameOfFile, "rw");
//                raf.seek(i * 4);
//                raf.writeInt(i+2550000);
//                raf.seek(i * 4 + 4);
//                raf.writeInt(i+2560000);
//                raf.seek(i*4);
//                System.out.println("" + raf.readInt() + " , "+raf.readInt());
//            } catch (FileNotFoundException ex) {
//                Logger.getLogger(OS1.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (IOException ex) {
//                Logger.getLogger(OS1.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
    }
}
    
