/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os1;

import com.sun.prism.impl.BufferUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author אליצור
 */
public class Client {

    public static int getX(int [] ArrayOfPreformance){
        int x = (int)(Math.random()*1000);
        return ArrayOfPreformance[x];
    }
    
    public static int[] ArrayOfPre(String str){
        int [] ans = new int [1000];
        double probability;
        String[] splits = str.split(",");
        int start = 0;
        int r = Integer.parseInt(splits[0]);
        for(int i = 2 ;i < splits.length;i++ ){
            probability = (int)(Double.parseDouble(splits[i]) * 1000)+ start;
            for(int k = start ; k < probability; k++){
                ans[k] = r;
            }
            start = (int)probability;
            r++;
        }
        return ans;
   }
    
    public static void main(String[] args) {
        Socket soc;
        BufferedReader in;
        PrintWriter out;
        int r1 = Integer.parseInt(args[0]);
        int r2 = Integer.parseInt(args[1]);
        String file = args[2];
        int x = getX(ArrayOfPre(null));
        
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
