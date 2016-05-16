/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os1;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author אליצור
 */
public class Client {

    public static void main (String [] args){
    Socket soc;
    BufferedReader in;
    PrintWriter out;
        try{
           soc = new Socket("127.0.0.1", 5555);
           
       }catch(Exception e){
           
       }
               
    }
    
}
