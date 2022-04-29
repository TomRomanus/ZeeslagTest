import java.util.ArrayList;
import java.util.Random;

public class Main {
    private static Ship[] shipsPC;
    private static Ship[] shipsPlayer;

    static final byte NR_OF_SHIPS = 5;
    static final byte BOARD_SIZE = 10;
    static final byte MAX_TRIES = 100;

    public static void main(String[] args) {
        init();
        printBoard();
    }

    static void init() {
        shipsPC = new Ship[NR_OF_SHIPS];
        ArrayList<Coordinate> invalidCoordinates = new ArrayList<>();
        shipsPC[0] = addShip((byte) 5, invalidCoordinates);
        for(int i=0; i<5; i++) invalidCoordinates.add(shipsPC[0].getCoordinates()[i]);
        shipsPC[1] = addShip((byte) 4, invalidCoordinates);
        for(int i=0; i<4; i++) invalidCoordinates.add(shipsPC[1].getCoordinates()[i]);
        shipsPC[2] = addShip((byte) 3, invalidCoordinates);
        for(int i=0; i<3; i++) invalidCoordinates.add(shipsPC[2].getCoordinates()[i]);
        shipsPC[3] = addShip((byte) 3, invalidCoordinates);
        for(int i=0; i<3; i++) invalidCoordinates.add(shipsPC[3].getCoordinates()[i]);
        shipsPC[4] = addShip((byte) 2, invalidCoordinates);
        for(int i=0; i<2; i++) invalidCoordinates.add(shipsPC[4].getCoordinates()[i]);
        invalidCoordinates.forEach(c -> System.out.println("(" + c.getX() + ", " + c.getY() + ")"));
        //shipsPlayer = new Ship[NR_OF_SHIPS];
    }

    static void printBoard() {
        for(byte y = BOARD_SIZE-1; y >= 0; y--) {
            System.out.print(y + "| ");
            for(byte x = 0; x < BOARD_SIZE; x++) {
                System.out.print(getPrint(x, y) + " ");
            }
            System.out.println();
        }
        System.out.println(" ---------------------");
        System.out.println("   0 1 2 3 4 5 6 7 8 9");
    }

    static char getPrint(byte x, byte y) {
        for(Ship ship : shipsPC) {
            for(Coordinate coordinate : ship.getCoordinates()) {
                if(coordinate.getX() == x && coordinate.getY() == y) {
                    if(coordinate.isHit()) return 'X';
                    else return 'x';
                }
            }
        }
        return ' ';
    }

    static boolean isValidCoordinate(byte x, byte y, ArrayList<Coordinate> invalidCoordinates) {
        if(x < 0 || y < 0 || x >= BOARD_SIZE || y >= BOARD_SIZE) return false;
        for(Coordinate coordinate : invalidCoordinates) {
            if (coordinate.getX() == x && coordinate.getY() == y) {
                return false;
            }
        }
        return true;
    }

    static Ship addShip(byte size, ArrayList<Coordinate> invalidCoordinates) {
        Random rand = new Random();
        Coordinate[] coordinates = new Coordinate[size];
        byte x = -1;
        byte y = -1;
        byte tries = 0;
        boolean failed = true;
        do {
            for(int i = 0; i<= MAX_TRIES; i++) {
                x = (byte) rand.nextInt(BOARD_SIZE);
                y = (byte) rand.nextInt(BOARD_SIZE);
                if(isValidCoordinate(x, y, invalidCoordinates)) {
                    failed = false;
                    break;
                }
            }
            if(failed) System.exit(0);
            Coordinate coordinate = new Coordinate(x, y);
            coordinates[0] = coordinate;
            if(rand.nextBoolean()) { // x or y axis
                if(rand.nextBoolean()) { // up or down
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
            tries ++;
        } while(failed && tries <= MAX_TRIES);
        System.out.println("tries: " + tries);
        return new Ship(coordinates);
    }
}
