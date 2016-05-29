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
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author אליצור
 */
public class WriteDataBase implements Runnable {

    private cacheNode cn;
    private String nameOfFile;
    private int x;
    private boolean found;
    private RandomAccessFile raf;

    public WriteDataBase(int x, cacheNode cn, boolean found) {

        this.cn = cn;
        this.x = x;
        this.found = found;

    }

    @Override
    public void run() {
        try {
            File dir = new File("DataBase");
            if (x >= 0) {
                nameOfFile = dir + "\\DataBaseNum" + (x / Server.sizeOfDb) + ".txt";
            } else {
                nameOfFile = dir + "\\DataBaseNum" + ((x / Server.sizeOfDb) - 1) + ".txt";
                x *= (-1);
            }
            raf = new RandomAccessFile(nameOfFile, "rw");

            if (!found) {
                Server.waitersToWriteInDb.put(x, cn);
                if (Server.waitersToWriteInDb.size() >= 3) {
                    Iterator<Map.Entry<Integer, cacheNode>> it = Server.waitersToWriteInDb.entrySet().iterator();
                    while (it.hasNext()) {
                        cacheNode enter = new cacheNode(it.next().getValue());
                        try {
                            write(enter);
                            if (enter.getZ() >= Server.cache.getM()) {///////////////////////////<=  הוספתי את התנאי הזה
                                Server.cache.insert(enter);
                            }
                        } catch (IOException ex) {
                            Logger.getLogger(WriteDataBase.class
                                    .getName()).log(Level.SEVERE, null, ex);
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
                    if (TThread.wasInTheDb.size() == 50) {
                        for (int i = 0; i < TThread.wasInTheDb.size(); i++) {
                            update(TThread.wasInTheDb.get(i));
                        }
                        TThread.wasInTheDb.clear();
                    }
                } else if (Server.waitersToWriteInDb.containsKey(x)) {
                    Server.waitersToWriteInDb.get(x).setZ(Server.waitersToWriteInDb.get(x).getZ() + 1);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(WriteDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void write(cacheNode other) throws IOException {

        raf.seek((other.getX() % Server.sizeOfDb) * 8);
        raf.writeInt(other.getY());
        raf.seek((other.getX() % Server.sizeOfDb) * 8);
        raf.seek((other.getX() % Server.sizeOfDb) * 8 + 4);
        raf.writeInt(other.getZ());
    }

    public void update(int x) throws IOException {
        int z;
        raf.seek((x % Server.sizeOfDb) * 8 + 4);
        z = raf.readInt();
        z++;
        raf.seek((x % Server.sizeOfDb) * 8 + 4);
        raf.writeInt(z);
    }
}
