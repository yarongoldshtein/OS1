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
 *
 * @author אליצור
 */
public class Client implements Runnable {

    private static int idNum = 0;
    private final ReentrantLock lock = new ReentrantLock(true);
    private final int id;
    private int y;
    private final int r1, r2;
    private String file;

    public Client(int r1, int r2, String fileName) {
        id = getID();
        this.r1 = r1;
        this.r2 = r2;
        file = fileName;
    }

    public static int[] ArrayOfPre(String str) {
        Thread.currentThread().setName("Client");

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

    private int getID() {
        lock.lock();
        try {
            return idNum++;
        } finally {
            lock.unlock();
        }
    }

    public static int getX(int[] ArrayOfPreformance) {
        int x = (int) (Math.random() * 1000);
        return ArrayOfPreformance[x];
    }

}
