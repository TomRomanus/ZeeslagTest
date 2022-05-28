
public class Coordinate {
    private final int x;
    private final int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) return true;
        if(!(obj instanceof Coordinate)) return false;
        return ((Coordinate) obj).getX() == x && ((Coordinate) obj).getY() == y;
    }
}
