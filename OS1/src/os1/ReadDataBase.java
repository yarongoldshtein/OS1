/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os1;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author אליצור
 */
public class ReadDataBase implements Runnable {

    private int x;
    private int y;

    public ReadDataBase(int x) {
        this.x = x;
    }

    /**
     *
     * @throws Exception
     */
    @Override
    public void run() {
        int ans = 0, sizeOfDb = 1000;
        try {
            File dir = new File("DataBase");
            dir.mkdir();
            String nameOfFile = dir + "\\DataBaseNum" + (x / sizeOfDb) + ".txt";
            RandomAccessFile raf = new RandomAccessFile(nameOfFile, "r");
            raf.seek((x % sizeOfDb) * 8);
            ans = raf.readInt();
            this.y = ans;
        } catch (IOException ex) {
            Logger.getLogger(ReadDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public int getY(){
        return y;
    }

}
