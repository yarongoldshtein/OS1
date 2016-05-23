/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os1;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yaron
 */
public class SocketManager implements Runnable {

    private ArrayList<SocketController> socArr;
    private SocketController SocCon;

    public SocketManager(ArrayList<SocketController> socArr) {
        this.socArr = socArr;
    }

    @Override
    public void run() {
        while (true) {
            while (socArr.isEmpty()) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    Logger.getLogger(SocketManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            for (int i = 0; i < socArr.size(); i++) {
                SocCon = socArr.get(i);
                Thread SocRead = new Thread(new SocketReader(SocCon));
                SocRead.start();
                try {
                    SocRead.join(333);
                    if (SocRead.isAlive()) {
                        SocRead.interrupt();
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(SocketManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
