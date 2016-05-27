/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os1;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author אליצור
 */
public class ReadDataBase implements Runnable {

    private int y;
    private boolean runFlag = true;
    private boolean getYFlag = false;
    private final ReentrantLock lock = new ReentrantLock(true);

    @Override
    public void run() {
        int ans;
        int x = SocketReader.x;
        lock.lock();
        try {
            try {
                while (!runFlag) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(CThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                runFlag = false;
            } finally {
                lock.unlock();
            }

            File dir = new File("DataBase");
            dir.mkdir();
            String nameOfFile;
            if (x >= 0) {
                nameOfFile = dir + "\\DataBaseNum" + (x / Server.sizeOfDb) + ".txt";
            } else {
                nameOfFile = dir + "\\DataBaseNum" + ((x / Server.sizeOfDb) - 1) + ".txt";
                x *= (-1);
            }
            lock.lock();
            RandomAccessFile raf = new RandomAccessFile(nameOfFile, "rw");
            raf.seek((x % Server.sizeOfDb) * 8);
            ans = raf.read();
            if (ans >= 0) {
                raf.seek((x % Server.sizeOfDb) * 8);
                ans = raf.readInt();
            } else if (ans == 0) {
                ans = -1;
            }
            this.y = ans;

            getYFlag = true;
        } catch (IOException ex) {
            Logger.getLogger(ReadDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getY() throws InterruptedException {
        while (!getYFlag) {
            Thread.sleep(50);
        }
        getYFlag = false;
        try {
            return y;
        } finally {
            runFlag = true;
        }
    }

}
