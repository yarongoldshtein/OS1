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
import java.io.RandomAccessFile;
import java.util.Arrays;
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
    public static void main(String[] args) throws FileNotFoundException, IOException {
        new Thread(new Server(5, 1000)).start();
        File f;
        for (int i = 1; i < 1001; i++) {
            String nameOfFile ="ProbabilityFiles/" + i + ".txt";
            f = new File(nameOfFile);
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String str = br.readLine();
            String[] splits = str.split(",");
            int r1 = Integer.parseInt(splits[0]);
            int r2 = Integer.parseInt(splits[1]);
            new Thread(new Client(r1, r2,nameOfFile)).start();
            System.err.println(i);
        }
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
//        cacheNode[] arr = new cacheNode[5];
//        for (int i = 0; i < 5; i++) {
//            arr[i] = new cacheNode(i, i + 1,10-i);
//        }
//        System.out.println(Arrays.toString(arr));
//         MinHeap mh = new MinHeap(arr);
//         mh.buildMinHeap();
//         mh.print();
    }
}
