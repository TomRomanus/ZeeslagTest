public class Coordinate {
    private final byte x;
    private final byte y;
    private boolean hit;

    public Coordinate(byte x, byte y) {
        this.x = x;
        this.y = y;
        hit = false;
    }

    public boolean Hit() {
        if(!hit) {
            hit = true;
            return true;
        } else return false;
    }

    public boolean isHit() {
        return hit;
    }

    public byte getX() {
        return x;
    }

    public byte getY() {
        return y;
    }
}
