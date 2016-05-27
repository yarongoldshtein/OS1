/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yaron
 */
public class SocketReader implements Runnable {

    private String str;
    private PrintWriter out;
    private BufferedReader in;
    private SocketController SocCon;
    private int y;
    static int x;
    private final int L;
    private ThreadPool SThread = new ThreadPool(Server.s);
        private final ReentrantLock lock = new ReentrantLock(true);


    public SocketReader(SocketController SocCon, int l) {
        this.SocCon = SocCon;
        L = l;
    }

    @Override
    public void run() {
        try {
            in = SocCon.getIn();
            out = SocCon.getOut();
            while ((str = in.readLine()) != null) {
                lock.lock();
                x = Integer.parseInt(str);
                OS1.numO++;
                TThread T = new TThread(x);
                new Thread(T).start();
                y = T.getY();
                out.println("" + y);
                out.flush();
                System.err.println("numO = "+OS1.numO);
                lock.unlock();
            }
        } catch (IOException ex) {
            Logger.getLogger(SocketReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
