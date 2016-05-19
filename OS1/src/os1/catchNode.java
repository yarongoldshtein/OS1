/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os1;

/**
 *
 * @author אליצור
 */
public class catchNode {
    
    private int x;
    private int y;
    private int z;
    private catchNode father;

    public catchNode(int x, int y, int z, catchNode father) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.father = father;
    }

    public void setFather(catchNode father) {
        this.father = father;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public catchNode getFather() {
        return father;
    }

    /**
     * compare 2 catchNode per their Y
     * @param other
     * @return   1 if | this.y > other.y ; 0 this.y = other.y ; -1 this.y <  other.y

     */
    public int comparePerY(catchNode other){
        int ans = 0;
        if(this.y  > other.y){
            ans = 1;
        }
        if(this.y == other.y){
            ans = 0;
        }
        if(this.y < other.y){
            ans = -1;
        }
        return ans;
    }
    
    /**
     *  compare 2 catchNode per their z
     * @param other
     * @return  1 if | this.z > other.z ; 0 this.z = other.z ; -1 this.z <  other.z

     */
    public int comparePerZ(catchNode other){
        int ans = 0;
        if(this.z  > other.z){
            ans = 1;
        }
        if(this.z == other.z){
            ans = 0;
        }
        if(this.z < other.z){
            ans = -1;
        }
        return ans;
    }
}
