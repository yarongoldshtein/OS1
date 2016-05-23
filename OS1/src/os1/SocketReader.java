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
import java.net.Socket;
import java.util.ArrayList;
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
    private final static int random = (int) (Math.random() * 10000);
    private final int sizeOfDb = 1000;

    public SocketReader(SocketController SocCon) {
        this.SocCon = SocCon;
    }

    @Override
    public void run() {
        try {
            in = SocCon.getIn();
            out = SocCon.getOut();
            while ((str = in.readLine()) != null) {
                int x = Integer.parseInt(str);
                ReadDataBase rdb = new ReadDataBase(x);
                rdb.run();
                y = rdb.getY();
                if (y > 0) {
                    out.println("" + y);
                    UpdateDataBase up = new UpdateDataBase(x,sizeOfDb);
                    up.run();

                } else {
                    y = ((random + x) % sizeOfDb) + 1;
                    out.println("" + y);
                    WriteDataBase wdb = new WriteDataBase(x,y,sizeOfDb);
                    wdb.run();
                }

                out.flush();
            }
        } catch (IOException ex) {
            Logger.getLogger(SocketReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
