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

/**
 *
 * @author yaron
 */
public class Server implements Runnable {

    private ServerSocket ServSoc;
    static int id = 0;
    int y;

    @Override
    public void run() {
        String str;
        try {
            ServSoc = new ServerSocket(4500);
            while (true) {
                Socket clientSoc = ServSoc.accept();
                PrintWriter out = new PrintWriter(clientSoc.getOutputStream());
                InputStreamReader sr = new InputStreamReader(clientSoc.getInputStream());
                BufferedReader in = new BufferedReader(sr);
                while ((str = in.readLine()) != null) {
                    if (str.equals("id")) {
                        out.println("" + id++);
                        out.flush();
                    } else {
                        y = Integer.parseInt(str) + 1;
                        out.println("" + y);
                        out.flush();
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
