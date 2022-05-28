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

    private boolean isAlive() {
        if(notHitCoordinates.size() == 0) {
            System.out.println("Ship of size: " + size + "has sunk");
            return false;
        } else return true;
    }

    public boolean hit(int x, int y) {
        for (Coordinate coordinate : notHitCoordinates) {
            if (coordinate.getX() == x && coordinate.getY() == y) {
                hitCoordinates.add(coordinate);
                notHitCoordinates.remove(coordinate);
                isAlive();
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
