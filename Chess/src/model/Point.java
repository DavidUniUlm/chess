package model;

public class Point {

    /**
     * Cache the hash code to make computing hashes faster.
     */
    private int hash = 0;
    private int x;
    private int y;


    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }


    /**
     * Returns a hash code value for the point.
     * @return a hash code value for the point.
     */
    @Override public int hashCode() {
        if (hash == 0) {
            long bits = 7L;
            bits = 31L * bits + Double.doubleToLongBits(getX());
            bits = 31L * bits + Double.doubleToLongBits(getY());
            hash =  (int)(bits ^ (bits >> 32));
        }
        return hash;
    }

    @Override public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj instanceof Point) {
            Point other = (Point) obj;
            return getX() == other.getX() && getY() == other.getY();
        } else return false;
    }

    @Override public String toString() {
        return "Point2D [x = " + getX() + ", y = " + getY() + "]";
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
