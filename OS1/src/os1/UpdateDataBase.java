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
public class UpdateDataBase implements Runnable {

    private int x;
    private int L;
    private int y;
    private final static int random = (int) (Math.random() * 10000);

    public UpdateDataBase(int x, int L) {
        this.x = x;
        this.L = L;
    }

    @Override
    public void run() {
        try {
            int sizeOfDb = L;
            File dir = new File("DataBase");
            String nameOfFile = dir + "\\DataBaseNum" + (x / sizeOfDb) + ".txt";
            RandomAccessFile raf = new RandomAccessFile(nameOfFile, "rw");
            raf.seek((x % sizeOfDb) * 8 + 4);
            int z = raf.readInt();
            z++;
            raf.seek((x % sizeOfDb) * 8 + 4);
            raf.writeInt(z);
            System.out.println("z = "+z);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(UpdateDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UpdateDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
