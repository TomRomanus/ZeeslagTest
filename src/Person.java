import java.util.ArrayList;

public class Person {
    private final ArrayList<Ship> ships;
    private final ArrayList<Coordinate> correctGuesses;
    private final ArrayList<Coordinate> badGuesses;
    private final int board_size;

    public Person(int board_size) {
        this.board_size = board_size;
        correctGuesses = new ArrayList<>();
        badGuesses = new ArrayList<>();
        ships = new ArrayList<>();
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }

    public boolean addShip(Ship ship) {
        if(ship.getAllCoordinates().stream().anyMatch(c -> c.getX()>board_size || c.getY()>board_size)) return false;
        if(ships.stream()
                .flatMap(ship1 -> ship1.getAllCoordinates().stream())
                .anyMatch(c -> ship.getAllCoordinates().contains(c)))
            return false;
        ships.add(ship);
        return true;
    }

    public void addCoorectGuess(int x, int y) {
        correctGuesses.add(new Coordinate(x, y));
    }

    public ArrayList<Coordinate> getCorrectGuesses() {
        return correctGuesses;
    }

    public void addBadGuess(int x, int y) {
        badGuesses.add(new Coordinate(x, y));
    }

    public ArrayList<Coordinate> getBadGuesses() {
        return badGuesses;
    }

    public boolean isValidGuess(int x, int y) {
        if(x> board_size || y > board_size) return false;
        Coordinate coordinate = new Coordinate(x, y);
        if(correctGuesses.contains(coordinate) || badGuesses.contains(coordinate)) return false;
        return true;
    }

    public boolean hit(int x, int y) {
        return ships.stream().anyMatch(s -> s.hit(x, y));
    }
}
