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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * control the Sockets
 * @author yaron
 */
public class SocketController {

    private PrintWriter out;
    private BufferedReader in;

    /**
     * SocketController constractor
     * @param soc 
     */
    public SocketController(Socket soc) {
        try {
            out = new PrintWriter(soc.getOutputStream());
            in = new BufferedReader(new InputStreamReader(soc.getInputStream()));

        } catch (IOException ex) {
            Logger.getLogger(SocketController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 
     * @return out from this socket
     */
    public PrintWriter getOut() {
        return out;
    }

    /**
     * set this Socket out
     * @param out 
     */
    public void setOut(PrintWriter out) {
        this.out = out;
    }

    /**
     * 
     * @return in from this Socket
     */
    public BufferedReader getIn() {
        return in;
    }

    /**
     * set this socket in
     * @param in 
     */
    public void setIn(BufferedReader in) {
        this.in = in;
    }

}
