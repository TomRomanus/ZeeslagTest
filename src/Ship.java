import java.util.ArrayList;

public class Ship {
    private final ArrayList<Coordinate> notHitCoordinates;
    private final ArrayList<Coordinate> hitCoordinates;
    private final int size;

    public Ship(ArrayList<Coordinate> coordinates) {
        this.notHitCoordinates = coordinates;
        hitCoordinates = new ArrayList<>();
        size = coordinates.size();
    }

    public ArrayList<Coordinate> getAllCoordinates() {
        ArrayList<Coordinate> allCoordinates = new ArrayList<>();
        allCoordinates.addAll(notHitCoordinates);
        allCoordinates.addAll(hitCoordinates);
        return allCoordinates;
    }

    public ArrayList<Coordinate> getNotHitCoordinates() {
        return notHitCoordinates;
    }

    public boolean isAlive() {
        return !notHitCoordinates.isEmpty();
    }

    public boolean hit(Coordinate guess) {
        for (Coordinate coordinate : notHitCoordinates) {
            if (coordinate.equals(guess)) {
                //System.out.println("Hit at (" + coordinate.getX() + ", " + coordinate.getY() + ")");
                hitCoordinates.add(coordinate);
                notHitCoordinates.remove(coordinate);
                return true;
            }
        }
        return false;
    }

    public boolean isHit(int x, int y) {
        for(Coordinate coordinate : hitCoordinates) {
            if(coordinate.getX() == x && coordinate.getY() == y) return true;
        }
        return false;
    }

    public boolean isNotHit(int x, int y) {
        for(Coordinate coordinate : notHitCoordinates) {
            if(coordinate.getX() == x && coordinate.getY() == y) return true;
        }
        return false;
    }
}
