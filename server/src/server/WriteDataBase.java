package server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author אליצור
 */
public class WriteDataBase implements Runnable {

    private int x;
    private cacheNode cn;
    private boolean found;
    private String nameOfFile;
    private RandomAccessFile raf;
    static int WriteDataBaseNo = 0;
    private ReentrantLock lock = new ReentrantLock(true);

    /**
     * WruteDataBase constractor
     * @param x the query
     * @param cn the y & z of this query 
     * @param found 
     */
    public WriteDataBase(int x, cacheNode cn, boolean found) {
        this.cn = cn;
        this.x = x;
        this.found = found;

    }

    @Override
    public void run() {
        Thread.currentThread().setName("WriteDataBaseNo" + (WriteDataBaseNo++));
        try {
            File dir = new File("C:\\Users\\yaron\\Documents\\NetBeansProjects\\OS1\\OS1\\DataBase");
            if (x >= 0) {
                nameOfFile = dir + "\\DataBaseNum" + (x / Server.sizeOfDb) + ".txt";
            } else {
                nameOfFile = dir + "\\DataBaseNum" + ((x / Server.sizeOfDb) - 1) + ".txt";
                x *= (-1);
            }
            raf = new RandomAccessFile(nameOfFile, "rw");
            if (!found) {
                Server.waitersToWriteInDb.put(x, cn);
                if (Server.waitersToWriteInDb.size() >= 50) {
                    Iterator<Map.Entry<Integer, cacheNode>> it = Server.waitersToWriteInDb.entrySet().iterator();
                    while (it.hasNext()) {
                        try {
                            cacheNode enter = new cacheNode(it.next().getValue());
                            write(enter);
                            if (enter.getZ() >= Server.cache.getM()) {
                                Server.cache.insert(enter);
                            }
                        } catch (Exception ex) {
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException ex1) {
                            }
                        }
                    }
                    Server.waitersToWriteInDb.clear();

                }
            } else {
                int temp;
                raf.seek((x % Server.sizeOfDb) * 8);
                temp = raf.read();
                if (temp >= 0) {
                    TThread.wasInTheDb.add(x);
                    if (TThread.wasInTheDb.size() == 30) {
                        lock.lock();
                        for (int i = 0; i < TThread.wasInTheDb.size(); i++) {
                            update(TThread.wasInTheDb.get(i));
                        }
                        TThread.wasInTheDb.clear();
                        lock.unlock();
                    }
                } else if (Server.waitersToWriteInDb.containsKey(x)) {
                    Server.waitersToWriteInDb.get(x).setZ(Server.waitersToWriteInDb.get(x).getZ() + 1);
                    if (Server.waitersToWriteInDb.get(x).getZ() >= Server.cache.getM()) {
                        Server.cache.insert(Server.waitersToWriteInDb.get(x));
                    }

                }
            }
        } catch (IOException ex) {
            Logger.getLogger(WriteDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                Server.cache.upDateCache();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(WriteDataBase.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(WriteDataBase.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(WriteDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     * change the data in the RanddomAcsessFile acording the Other data
     * @param other
     * @throws IOException 
     */
    public void write(cacheNode other) throws IOException {

        other.setX(Math.abs(other.getX()));
        raf.seek((other.getX() % Server.sizeOfDb) * 8);
        raf.writeInt(other.getY());
        raf.seek((other.getX() % Server.sizeOfDb) * 8);
        raf.seek((other.getX() % Server.sizeOfDb) * 8 + 4);
        raf.writeInt(other.getZ());
    }

    /**
     * update the z for this query
     * @param x query
     */
    public void update(int x) {
        try {
            int z, y;
            raf.seek((x % Server.sizeOfDb) * 8);
            y = raf.readInt();
            raf.seek((x % Server.sizeOfDb) * 8 + 4);
            z = raf.readInt();
            z++;
            raf.seek((x % Server.sizeOfDb) * 8 + 4);
            raf.writeInt(z);
            if (z >= Server.cache.getM()) {
                Server.cache.insert(new cacheNode(x, y, z));
            }
        } catch (IOException ex) {
        }
    }
}
