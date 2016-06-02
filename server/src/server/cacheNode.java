/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 * Object save x, y, z
 * @author yaron
 */
public class cacheNode implements Comparable<Object> {

    private int x;
    private int y;
    private int z;

    /**
     * defult constractor
     */
    public cacheNode() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    /**
     * constractor that get data
     * @param x
     * @param y
     * @param z 
     */
    public cacheNode(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * copy constractor ( deep copy)
     * @param other 
     */
    public cacheNode(cacheNode other) {
        if (other != null) {
            this.x = other.x;
            this.y = other.y;
            this.z = other.z;
        }else{
            this.x = 0;
            this.y = -1;
            this.z = -1;
        }
    }

    /**
     * 
     * @return this.x
     */
    public int getX() {
        return x;
    }

    /**
     * 
     * @return this.y
     */
    public int getY() {
        return y;
    }

    /**
     * 
     * @return this.z
     */
    public int getZ() {
        return z;
    }

    /**
     * compare 2 cacheNode per their z
     *
     * @param other
     * @return 1 if this.z > other.z ; 0 this.z = other.z ; -1 this.z < other.z
     *
     */
    @Override
    public int compareTo(Object t) {
        cacheNode other = (cacheNode) t;
        int ans = 0;
        if (this.z > other.getZ()) {
            ans = 1;
        }
        if (this.z == other.getZ()) {
            ans = 0;
        }
        if (this.z < other.getZ()) {
            ans = -1;
        }
        return ans;
    }

    @Override
    public String toString() {
        return "{" + "x=" + x + ", y=" + y + ", z=" + z + "}";
    }

    /**
     * set this.x
     * @param x new x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * set this.y
     * @param y new y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * set this.z
     * @param z new z
     */
    public void setZ(int z) {
        this.z = z;
    }
}
