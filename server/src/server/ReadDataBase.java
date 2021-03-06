/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

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

    private cacheNode cn = new cacheNode();
    private int x;
    private boolean runFlag = true;
    private boolean getYFlag = false;
    private final ReentrantLock lock = new ReentrantLock(true);
    static int ReadDataBaseNo = 0;

    /**
     * ReadDataBase constractor
     * @param x 
     */
    public ReadDataBase(int x) {
        this.x = x;
        cn.setX(x);
    }

    /**
     * search the query in the Database
     */
    @Override
    public void run() {
        Thread.currentThread().setName("ReadDataBaseNo"+(ReadDataBaseNo++));
        int ans;
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

            File dir = new File("C:\\Users\\yaron\\Documents\\NetBeansProjects\\OS1\\OS1\\DataBase");
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
                raf.seek((x % Server.sizeOfDb) * 8 + 4);
                cn.setZ(raf.readInt());
            }

            if (ans == 0) {
                ans = -1;
            }
            this.cn.setY(ans);
            if (cn.getY() == -1) {
                cacheNode tempNode = new cacheNode(Server.waitersToWriteInDb.get(x));
                if (tempNode.getZ() != -1) {
                    this.cn.setY(tempNode.getY());
                    this.cn.setZ(tempNode.getZ());
                }
            }

            getYFlag = true;
        } catch (IOException ex) {
            Logger.getLogger(ReadDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 
     * @return node with the answer
     * @throws InterruptedException 
     */
    public cacheNode getNode() throws InterruptedException {
        while (!getYFlag) {
            Thread.sleep(50);
        }
        getYFlag = false;
        try {
            return cn;
        } finally {
            runFlag = true;
        }
    }

}
