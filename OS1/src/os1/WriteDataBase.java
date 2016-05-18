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
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author אליצור
 */
public class WriteDataBase implements Runnable {

    private int x;
    private int L;
    private int y;
    private final static int random = (int)(Math.random()*10000);

    public WriteDataBase(int x, int L) {
        this.x = x;
        this.L = L;   
    }

    @Override
    public void run() {
        int sizeOfDb = L;
        File dir = new File("DataBase");
        dir.mkdir();
        String nameOfFile = dir + "\\DataBaseNum" + (x / sizeOfDb) + ".txt";
        RandomAccessFile raf;
        try {
            raf = new RandomAccessFile(nameOfFile, "rw");
            raf.seek((x % sizeOfDb) * 8);
            y = raf.readInt();
            if (y == 0) {
                raf.seek((x % sizeOfDb) * 8);
                raf.writeInt(((random+x)%L)+1);
                raf.seek((x % sizeOfDb) * 8 + 4);
                raf.writeInt(1);
                y = ((random+x)%L)+1;
            } else {
                raf.seek((x % sizeOfDb) * 8 + 4);
                int z = raf.readInt();
                z++;
                raf.seek((x % sizeOfDb) * 8 + 4);
                raf.writeInt(z);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(WriteDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(WriteDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public int getY(){
        return y;
    }
}
