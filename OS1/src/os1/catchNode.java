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

    public catchNode() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public catchNode(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public catchNode(catchNode other) {
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
     * compare 2 catchNode per their x
     *
     * @param other
     * @return 1 if this.x > other.x ; 0 this.x = other.x ; -1 this.x < other.x
     *
     */
    public int comparePerX(catchNode other) {
        int ans = 0;
        if (this.x > other.getX()) {
            ans = 1;
        }
        if (this.x == other.getX()) {
            ans = 0;
        }
        if (this.x < other.getX()) {
            ans = -1;
        }
        return ans;
    }

    /**
     * compare 2 catchNode per their z
     *
     * @param other
     * @return 1 if this.z > other.z ; 0 this.z = other.z ; -1 this.z < other.z
     *
     */
    public int comparePerZ(catchNode other) {
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
}
