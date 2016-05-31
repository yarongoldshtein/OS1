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
import java.util.concurrent.locks.ReentrantLock;

/**
 * Client sends queries to the server
 * @author אליצור
 */
public class Client implements Runnable {

    private final ReentrantLock lock = new ReentrantLock(true);
    private final int id;
    private int y;
    private final int R1, R2;
    private String file;
    static int ClientNo = 0;

    /**
     * Client constractor
     *
     * @param R1 Beginning of term questions
     * @param R2 End of term questions
     * @param fileName
     */
    public Client(int R1, int R2, String fileName) {
        this.R1 = R1;
        this.R2 = R2;
        file = fileName;
        id = (int) (Math.abs(R1 * R2 * Math.random() * 1000) % 10000);
    }

    /**
     * set ArrayOfPreformance Takes from a range of probabilities and by
     * probabilities and help fill an array size of 1000
     *
     * @param str line from a range of probabilities
     * @return Array of preformance
     */
    public static int[] ArrayOfPre(String str) {
        Thread.currentThread().setName("Client" + (ClientNo++));

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

    /**
     * The client opens a socket server and sending it a random query from the
     * Array of preformance , the client waits for a response and prints it and
     * back again ( not open again Socket )
     */
    @Override
    public void run() {
        Socket soc;
        BufferedReader in;
        PrintWriter out;
        String str;

        int[] ArrayOfPreformance = new int[1000];
        FileReader fr;
        try {
            fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            str = br.readLine();
            ArrayOfPreformance = ArrayOfPre(str);
            soc = new Socket("127.0.0.1", 4500);
            in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
            out = new PrintWriter(soc.getOutputStream());
            while (true) {
                int x = getX(ArrayOfPreformance);
                System.out.println("Client<" + id + ">:sending " + x);
                out.println(x);
                out.flush();
                y = Integer.parseInt(in.readLine());
                System.out.println("Client<" + id + ">:got reply " + y + " for query " + x + "\n");
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     *
     * @param ArrayOfPreformance
     * @return Query randomly from the Array
     */
    public static int getX(int[] ArrayOfPreformance) {
        int x = (int) (Math.random() * 1000);
        return ArrayOfPreformance[x];
    }

}
