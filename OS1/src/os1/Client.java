/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author אליצור
 */
public class Client implements Runnable {

    public static int getX(int[] ArrayOfPreformance) {
        int x = (int) (Math.random() * 1000);
        return ArrayOfPreformance[x];
    }

    public static int[] ArrayOfPre(String str) {
        int[] ans = new int[1000];
        double probability;
        String[] splits = str.split(",");
        int start = 0;
        int r = Integer.parseInt(splits[0]);
        for (int i = 2; i < splits.length; i++) {
            probability = (int) (Double.parseDouble(splits[i]) * 1000) + start;
            for (int k = start; k < probability; k++) {
                ans[k] = r;
            }
            start = (int) probability;
            r++;
        }
        return ans;
    }

    @Override
    public void run() {
        Socket soc;
        BufferedReader in;
        PrintWriter out;
        int r1 = Integer.parseInt("1");
        int r2 = Integer.parseInt("4");
        String file = "ProbabilityFiles\\0.txt";
        int[] ArrayOfPreformance = new int[1000];
        FileReader fr;
        try {
            fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String str;
            str = br.readLine();
            ArrayOfPreformance = ArrayOfPre(str);
            soc = new Socket("127.0.0.1", 4500);
            InputStreamReader sr = new InputStreamReader(soc.getInputStream());
            in = new BufferedReader(sr);
            out = new PrintWriter(soc.getOutputStream());
            int id, y;
            out.println("id");
            out.flush();
            id = Integer.parseInt(in.readLine());
            int i = 0;
            while (i<10) {
                int x = getX(ArrayOfPreformance);
                System.out.println("Client<" + id + ">:sending " + x);
                out.println(x);
                out.flush();
                y = Integer.parseInt(in.readLine());
                System.out.println("Client<" + id + ">:got reply " + y + " for query " + x+"\n");
                i++;
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
