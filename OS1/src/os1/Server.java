/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yaron
 */
public class Server implements Runnable {

    static LockedHashMap<Integer, cacheNode> waitersToWriteInDb = new LockedHashMap<>();
    private ServerSocket ServSoc;
    private ArrayList<SocketController> socArr = new ArrayList<>();
    static Cache cache;
    static CThread ct;
    static final int sizeOfDb = 1000;
    static final int random = (int) (Math.random() * 10000);
    static int L;
    static ThreadPool SearchThreadPool;
    static ThreadPool ReadersThreadPool;

    /**
     *
     * @param L - the range of answers
     * @param M - the minimum size to enter the cache
     * @param C - the size of cache
     * @param S - the number of search Threads
     * @param Y - the number of readers Threads
     */
    public Server(int L, int M, int C, int S, int Y) {
        this.L = L;
        cache = new Cache(M, C);
        ct = new CThread();
        SearchThreadPool = new ThreadPool(S);
        ReadersThreadPool = new ThreadPool(Y);
    }

    @Override
    public void run() {
        Thread.currentThread().setName("Server");

        try {
            ServSoc = new ServerSocket(4500);
            new Thread(new SocketManager(socArr, L)).start();
            new Thread(Server.ct).start();

            while (true) {
                Socket clientSoc = ServSoc.accept();
                SocketController SocCon = new SocketController(clientSoc);
                socArr.add(SocCon);
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
