import java.util.Random;

public class Main {
    private static Ship[] shipsPC;
    private static Ship[] shipsPlayer;
    private static byte nrOfShips = 5;

    public static void main(String[] args) {
        init();
    }

    static void init() {
        shipsPC = new Ship[nrOfShips];
        Coordinate[] invalidCoordinates = new Coordinate[18];


        shipsPlayer = new Ship[nrOfShips];
    }

    static boolean isValidCoordinate(byte x, byte y, Coordinate[] invalidCoordinates) {
        for(Coordinate coordinate : invalidCoordinates) {
            if (coordinate.getX() == x && coordinate.getY() == y) {
                return false;
            }
        }
        return true;
    }

    static Ship addShip(byte size, Coordinate[] invalidCoordinates) {
        Random rand = new Random();
        Coordinate[] coordinates = new Coordinate[size];
        byte x;
        byte y;
        boolean failed = false;
        do {
            do {
                x = (byte) rand.nextInt(10);
                y = (byte) rand.nextInt(10);
            } while(!isValidCoordinate(x, y, invalidCoordinates));
            Coordinate coordinate = new Coordinate(x, y);
            coordinates[0] = coordinate;
            if(rand.nextBoolean()) { // x or y axis
                if(rand.nextBoolean()){ // up or down
                    for(byte i = 1; i < size; i++) {
                        x++;
                        if(isValidCoordinate(x, y, invalidCoordinates)) {
                            coordinate = new Coordinate(x, y);
                            coordinates[i] = coordinate;
                        } else {
                            failed = true;
                            break;
                        }
                    }
                } else {
                    for(byte i = 1; i < size; i++) {
                        x--;
                        if(isValidCoordinate(x, y, invalidCoordinates)) {
                            coordinate = new Coordinate(x, y);
                            coordinates[i] = coordinate;
                        } else {
                            failed = true;
                            break;
                        }
                    }
                }
            } else {
                if(rand.nextBoolean()){ // up or down
                    for(byte i = 1; i < size; i++) {
                        y++;
                        if(isValidCoordinate(x, y, invalidCoordinates)) {
                            coordinate = new Coordinate(x, y);
                            coordinates[i] = coordinate;
                        } else {
                            failed = true;
                            break;
                        }
                    }
                } else {
                    for(byte i = 1; i < size; i++) {
                        y--;
                        if(isValidCoordinate(x, y, invalidCoordinates)) {
                            coordinate = new Coordinate(x, y);
                            coordinates[i] = coordinate;
                        } else {
                            failed = true;
                            break;
                        }
                    }
                }
            }
        } while(failed);
        return new Ship(coordinates);
    }
}
