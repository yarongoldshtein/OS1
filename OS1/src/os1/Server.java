/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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

    private ServerSocket ServSoc;
    static int L;
    ArrayList<SocketController> socArr = new ArrayList<>();
    static Cache cache;
    static CThread ct;
    static final int sizeOfDb = 1000;
    static final int random = (int) (Math.random() * 10000);
    static  int s;

    public Server(int s, int L, int M, int C) {
        this.L = L;
        cache = new Cache(M, C);
        ct = new CThread();
        this.s = s;
    }

    @Override
    public void run() {
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
            ex.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
