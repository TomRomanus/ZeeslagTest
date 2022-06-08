import java.util.*;

public class Bot {
    private final ArrayList<Ship> ships;
    private final ArrayList<Coordinate> allCoordinates;
    private final ArrayList<Coordinate> correctGuesses;
    private final ArrayList<Coordinate> badGuesses;

    private final Random random;

    private final int board_size;
    private final int MAX_TRIES = 100;

    public Bot(int board_size) {
        this.board_size = board_size;
        ships = new ArrayList<>();
        allCoordinates = new ArrayList<>();
        correctGuesses = new ArrayList<>();
        badGuesses = new ArrayList<>();
        random = new Random();
    }

    public boolean lost() {
        for(Ship ship : ships) if(ship.isAlive()) return false;
        return true;
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }

    public void clearAll() {
        ships.clear();
        allCoordinates.clear();
        correctGuesses.clear();
        badGuesses.clear();
    }

    public ArrayList<Coordinate> getCorrectGuesses() {
        return correctGuesses;
    }

    public ArrayList<Coordinate> getBadGuesses() {
        return badGuesses;
    }

    public Coordinate guess() {
        int x, y, dX, dY;
        ArrayList<Coordinate> allGuesses = new ArrayList<>();
        allGuesses.addAll(correctGuesses);
        allGuesses.addAll(badGuesses);

        if(correctGuesses.size() >= 2) {
            dX = correctGuesses.get(correctGuesses.size() - 1).getX() - correctGuesses.get(correctGuesses.size() - 2).getX();
            dY = correctGuesses.get(correctGuesses.size() - 1).getY() - correctGuesses.get(correctGuesses.size() - 2).getY();
            if(dX >= -1 && dX <= 1 && dY >= -1 && dY <= 1) {
                x = correctGuesses.get(correctGuesses.size()-1).getX() + dX;
                y = correctGuesses.get(correctGuesses.size()-1).getY() + dY;
                if(isValidCoordinate(x, y, allGuesses)) return new Coordinate(x, y);

                x = correctGuesses.get(correctGuesses.size() - 2).getX() - dX;
                y = correctGuesses.get(correctGuesses.size() - 2).getY() - dY;
                while(!isValidCoordinate(x, y, correctGuesses)) {
                    x -= dX;
                    y -= dY;
                }
                if(isValidCoordinate(x, y, allGuesses)) return new Coordinate(x, y);
            }
            else {
                x = correctGuesses.get(correctGuesses.size() - 1).getX();
                y = correctGuesses.get(correctGuesses.size() - 1).getY();
                if(Math.abs(dX) > 1 && dY == 0 && Math.abs(dX) + 1 < correctGuesses.size())
                    dX = x - correctGuesses.get(correctGuesses.size() - 1 - Math.abs(dX)).getX();
                if(Math.abs(dY) > 1 && dX == 0 && Math.abs(dY) + 1 < correctGuesses.size())
                    dY = y - correctGuesses.get(correctGuesses.size() - 1 - Math.abs(dY)).getY();
                if(isValidCoordinate(x + dX, y + dY, allGuesses)) return new Coordinate(x + dX, y + dY);
                else{
                    if (isValidCoordinate(x + 1, y, allGuesses)) return new Coordinate(x + 1, y);
                    if (isValidCoordinate(x - 1, y, allGuesses)) return new Coordinate(x - 1, y);
                    if (isValidCoordinate(x, y + 1, allGuesses)) return new Coordinate(x, y + 1);
                    if (isValidCoordinate(x, y - 1, allGuesses)) return new Coordinate(x, y - 1);
                }
            }
        }

        if(correctGuesses.size() == 1) {
            x = correctGuesses.get(0).getX();
            y = correctGuesses.get(0).getY();
            if(isValidCoordinate(x+1, y, allGuesses)) return new Coordinate(x+1, y);
            if(isValidCoordinate(x-1, y, allGuesses)) return new Coordinate(x-1, y);
            if(isValidCoordinate(x, y+1, allGuesses)) return new Coordinate(x, y+1);
            if(isValidCoordinate(x, y-1, allGuesses)) return new Coordinate(x, y-1);
        }

        int tries = 0;
        do {
            x = random.nextInt(board_size);
            y = random.nextInt(board_size);
            tries++;
        } while(!isValidCoordinate(x, y, allGuesses) && tries <= MAX_TRIES);
        if(isValidCoordinate(x, y, allGuesses)) return new Coordinate(x, y);

        for(x = 0; x < board_size; x++) {
            for(y = 0; y < board_size; y++)
                if(isValidCoordinate(x, y, allGuesses)) return new Coordinate(x, y);
        }
        System.out.println("Could not make a correct guess, exiting program...");
        System.exit(-1);
        return null;
    }

    public void addCorrectGuess(Coordinate coordinate) {
        correctGuesses.add(coordinate);
    }

    public void addBadGuess(Coordinate coordinate) {
        badGuesses.add(coordinate);
    }

    private boolean isValidCoordinate(int x, int y, List<Coordinate> invalidCoordinates) {
        if(x < 0 || y < 0 || x >= board_size || y >= board_size) return false;
        for(Coordinate coordinate : invalidCoordinates) {
            if (coordinate.getX() == x && coordinate.getY() == y) {
                return false;
            }
        }
        return true;
    }

    public void addShip(int size) {
        Coordinate[] shipCoordinates = new Coordinate[size];
        int x = -1;
        int y = -1;
        int tries = 0;
        boolean failed = true;
        do {
            for(int i = 0; i<= MAX_TRIES; i++) {
                x = random.nextInt(board_size);
                y = random.nextInt(board_size);
                if(isValidCoordinate(x, y, allCoordinates)) {
                    failed = false;
                    break;
                }
            }
            if(failed) {
                System.out.println("Could not add a new ship, exiting program...");
                System.exit(-1);
            }
            shipCoordinates[0] = new Coordinate(x, y);
            if(random.nextBoolean()) { // x or y axis
                if(random.nextBoolean()) { // up or down
                    for(int i = 1; i < size; i++) {
                        x++;
                        if(isValidCoordinate(x, y, allCoordinates)) {
                            shipCoordinates[i] = new Coordinate(x, y);
                        } else {
                            failed = true;
                            break;
                        }
                    }
                } else {
                    for(int i = 1; i < size; i++) {
                        x--;
                        if(isValidCoordinate(x, y, allCoordinates)) {
                            shipCoordinates[i] = new Coordinate(x, y);
                        } else {
                            failed = true;
                            break;
                        }
                    }
                }
            } else {
                if(random.nextBoolean()){ // up or down
                    for(int i = 1; i < size; i++) {
                        y++;
                        if(isValidCoordinate(x, y, allCoordinates)) {
                            shipCoordinates[i] = new Coordinate(x, y);
                        } else {
                            failed = true;
                            break;
                        }
                    }
                } else {
                    for(int i = 1; i < size; i++) {
                        y--;
                        if(isValidCoordinate(x, y, allCoordinates)) {
                            shipCoordinates[i] = new Coordinate(x, y);
                        } else {
                            failed = true;
                            break;
                        }
                    }
                }
            }
            tries ++;
        } while(failed && tries <= MAX_TRIES);
        if(failed) {
            System.out.println("Could not add a new ship, exiting program...");
            System.exit(-1);
        }
        ArrayList<Coordinate> coordinates = new ArrayList<>(Arrays.asList(shipCoordinates));
        allCoordinates.addAll(coordinates);
        Ship ship = new Ship(coordinates);
        ships.add(ship);
    }

    public boolean hit(Coordinate guess) {
        return ships.stream().anyMatch(s -> s.hit(guess));
    }
}
