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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yaron
 */
public class Server implements Runnable {

    private ServerSocket ServSoc;
    static int id = 0;
    private int y;
    ThreadPool threadPoolOfReaders;
    private int L;

    public Server(int namOfThreads, int L) {
        threadPoolOfReaders = new ThreadPool(namOfThreads);
        this.L = L;
    }

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
                        int x = Integer.parseInt(str);
                        ReadDataBase rdb = new ReadDataBase(x, L);
                        // threadPoolOfReaders.execute(rdb);
                        rdb.run();
                        y = rdb.getY();
                        if (y >= 0) {
                            UpdateDataBase up = new UpdateDataBase(x, L);
                            up.run();

                        } else {
                            WriteDataBase wdb = new WriteDataBase(x, L);
                            wdb.run();
                            y = wdb.getY();

                        }
                        out.println("" + y);
                        out.flush();
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
