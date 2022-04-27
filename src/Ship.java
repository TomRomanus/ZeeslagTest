
public class Ship {
    private final Coordinate[] coordinates;
    private boolean alive;

    public Ship(Coordinate[] coordinates) {
        this.coordinates = coordinates;
        alive = true;
    }

    public Coordinate[] getCoordinates() {
        return coordinates;
    }

    public boolean isAlive() {
        return alive;
    }

    private void checkIsAlive() {
        for (Coordinate coordinate : coordinates) {
            if (!coordinate.isHit()) return;
        }
        alive = false;
    }

    public boolean Hit(byte x, byte y) {
        for (Coordinate coordinate : coordinates) {
            if (coordinate.getX() == x && coordinate.getY() == y && coordinate.Hit()) {
                    checkIsAlive();
                    return true;
            }
        }
        return false;
    }
}
