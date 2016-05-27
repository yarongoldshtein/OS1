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

    private int y;
    

 

    @Override
    public void run() {
        try {
        int x = SocketReader.x;
            File dir = new File("DataBase");
            String nameOfFile;
            if (x >= 0) {
                nameOfFile = dir + "\\DataBaseNum" + (x / Server.sizeOfDb) + ".txt";
            } else {
                nameOfFile = dir + "\\DataBaseNum" + ((x / Server.sizeOfDb) -1) + ".txt";
                x *= (-1);
            }
            RandomAccessFile raf = new RandomAccessFile(nameOfFile, "rw");
            raf.seek((x % Server.sizeOfDb) * 8 + 4);
            int z = raf.readInt();
            z++;
            raf.seek((x % Server.sizeOfDb) * 8 + 4);
            raf.writeInt(z);
            

        } catch (FileNotFoundException ex) {
            Logger.getLogger(UpdateDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UpdateDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
