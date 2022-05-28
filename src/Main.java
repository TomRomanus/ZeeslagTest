import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static final int BOARD_SIZE = 10;

    public static void main(String[] args) {
        Bot bot = new Bot(BOARD_SIZE);
        Person person = new Person(BOARD_SIZE);
        initialise(person, bot);
        game();
    }

    static void initialise(Person person, Bot bot) {
        Scanner sc = new Scanner(System.in);
        int x, y, size;
        Integer[] sizes = {5, 4, 3, 3, 2};
        String dir;
        ArrayList<Coordinate> coordinates;
        ArrayList<Coordinate> invalidCoordinates = new ArrayList<>();
        printBoard(person.getShips(), true);

        for(int i = 0; i < 5 ; i++) {
            size = sizes[i];
            do {
                coordinates = null;
                System.out.print("Please enter x coordinate of ship with size "+ size +": ");
                try {
                    x = sc.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input, please try again");
                    sc.next();
                    continue;
                }
                System.out.print("Please enter y coordinate of ship with size "+ size +": ");
                try {
                    y = sc.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input, please try again");
                    sc.next();
                    continue;
                }
                System.out.print("Enter U to go up, D to go down, L to go left, and R to go right: ");
                dir = sc.next();
                coordinates = processInitialInput(x, y, dir, size, invalidCoordinates);
            } while (coordinates == null);
            person.addShip(new Ship(coordinates));
            invalidCoordinates.addAll(coordinates);
            printBoard(person.getShips(), true);
            bot.addShip(size);
        }
        printBoard(bot.getShips(), true);
    }

    static ArrayList<Coordinate> processInitialInput(int x, int y, String direction, int size, ArrayList<Coordinate> invalidCoordinates) {
        ArrayList<Coordinate> coordinates = new ArrayList<>();
        int dx = 0, dy = 0;
        String dir = direction.toLowerCase();
        if((dir.equals("u") || dir.equals("d") || dir.equals("l") || dir.equals("r"))
                && x < BOARD_SIZE && y < BOARD_SIZE && x >= 0 && y >= 0)
        {
            if(dir.equals("u")) dy = 1;
            else if(dir.equals("d")) dy = -1;
            else if(dir.equals("r")) dx = 1;
            else dx = -1;

            if((x + size * dx) >= BOARD_SIZE || (x + size * dx) < 0 || (y + size * dy) >= BOARD_SIZE || (y + size * dy) < 0) {
                System.out.println("Invalid input, please try again");
                return null;
            }

            Coordinate coordinate;
            for(int i = 0; i < size; i++) {
                coordinate = new Coordinate(x + i * dx, y + i * dy);
                if(invalidCoordinates.contains(coordinate)) {
                    System.out.println("Invalid input, please try again");
                    return null;
                }
                else coordinates.add(coordinate);
            }
        }
        else {
            System.out.println("Invalid input, please try again");
            return null;
        }
        return coordinates;
    }

    static void game() {
        //TODO: Create the game logic
    }

    static void printBoard(ArrayList<Ship> ships, boolean showNotHit) {
        for(int y = BOARD_SIZE-1; y >= 0; y--) {
            System.out.print(y + "| ");
            for(int x = 0; x < BOARD_SIZE; x++) {
                System.out.print(getPrint(x, y,ships, showNotHit) + " ");
            }
            System.out.println();
        }
        System.out.println(" ---------------------");
        System.out.println("   0 1 2 3 4 5 6 7 8 9");
    }

    static char getPrint(int x, int y, ArrayList<Ship> ships, boolean showNotHit) {
        for(Ship ship : ships) {
            if(ship.isHit(x, y)) return 'X';
            if(showNotHit && ship.isNotHit(x, y)) return 'x';
        }
        return ' ';
    }
}
