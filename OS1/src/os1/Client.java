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
import org.omg.CORBA.PRIVATE_MEMBER;

/**
 *
 * @author אליצור
 */
public class Client {

    public static void main(String[] args) {
        Socket soc;
        BufferedReader in;
        PrintWriter out;

        try {
            soc = new Socket("127.0.0.1", 5555);

            InputStreamReader sr = new InputStreamReader(soc.getInputStream());
            in = new BufferedReader(sr);
            out = new PrintWriter(soc.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
