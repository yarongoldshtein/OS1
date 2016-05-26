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
 * @author אליצור
 */
public class WriteDataBase implements Runnable {

    private int x;
    private int y;
    private int sizeOfDb;
    private int z;

    public WriteDataBase(int x, int y, int z, int sizeOfDb) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.sizeOfDb = sizeOfDb;
    }

    @Override
    public void run() {
        File dir = new File("DataBase");
        String nameOfFile;
        if (x >= 0) {
            nameOfFile = dir + "\\DataBaseNum" + (x / sizeOfDb) + ".txt";
        } else {
            nameOfFile = dir + "\\DataBaseNum" + ((x / sizeOfDb) - 1) + ".txt";
            x *= (-1);
        }
        try {
            RandomAccessFile raf = new RandomAccessFile(nameOfFile, "rw");
            raf.seek((x % sizeOfDb) * 8);
            raf.writeInt(y);
            raf.seek((x % sizeOfDb) * 8 + 4);
            raf.writeInt(z);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(WriteDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(WriteDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
