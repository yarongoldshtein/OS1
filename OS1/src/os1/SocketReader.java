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

    String str;
    private PrintWriter out;
    private BufferedReader in;
    SocketController SocCon;
    int y;

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
                // threadPoolOfReaders.execute(rdb);
                rdb.run();
                y = rdb.getY();
                if (y >= 0) {
                    UpdateDataBase up = new UpdateDataBase(x);
                    up.run();

                } else {
                    WriteDataBase wdb = new WriteDataBase(x);
                    wdb.run();
                    y = wdb.getY();

                }
                out.println("" + y);
                out.flush();
            }
        } catch (IOException ex) {
            Logger.getLogger(SocketReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}


