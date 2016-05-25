/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package os1;

import java.util.ArrayList;

/**
 *
 * @author Student
 */
public class CThread implements Runnable{

    private ArrayList<Integer> ArrayOfReq = new ArrayList<Integer>();
    private int y;
   
    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public ArrayList<Integer> getArrayOfReq() {
        return ArrayOfReq;
    }

    public int getY() {
        return y;
    }
    
    
}
