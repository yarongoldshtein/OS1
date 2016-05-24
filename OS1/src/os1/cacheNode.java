/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os1;

/**
 *
 * @author yaron
 */
public class cacheNode implements Comparable<Object>{

    private int x;
    private int y;
    private int z;

    public cacheNode() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public cacheNode(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public cacheNode(cacheNode other) {
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
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


    /**
     * compare 2 cacheNode per their z
     *
     * @param other
     * @return 1 if this.z > other.z ; 0 this.z = other.z ; -1 this.z < other.z
     *
     */
    @Override
      public int compareTo(Object t) {
          cacheNode other = (cacheNode)t;
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

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }
}
