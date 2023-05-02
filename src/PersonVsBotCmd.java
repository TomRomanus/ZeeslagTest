import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class PersonVsBotCmd {
    Bot bot;
    Person person;
    static int boardSize;

    public PersonVsBotCmd(int boardSize) {
        PersonVsBotCmd.boardSize = boardSize;
        bot = new Bot(boardSize);
        person = new Person(boardSize);
        initialise(person, bot);
        game(bot, person);
    }

    static void initialise(Person person, Bot bot) {
        Scanner sc = new Scanner(System.in);
        int x, y, size;
        Integer[] sizes = {5, 4, 3, 3, 2};
        String dir;
        ArrayList<Coordinate> coordinates;
        ArrayList<Coordinate> invalidCoordinates = new ArrayList<>();
        printBoard(person.getShips(), true, null, null);

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
            printBoard(person.getShips(), true, null, null);
            bot.addShip(size);
        }
    }

    static ArrayList<Coordinate> processInitialInput(int x, int y, String direction, int size, ArrayList<Coordinate> invalidCoordinates) {
        ArrayList<Coordinate> coordinates = new ArrayList<>();
        int dx = 0, dy = 0;
        String dir = direction.toLowerCase();
        if((dir.equals("u") || dir.equals("d") || dir.equals("l") || dir.equals("r"))
                && x < boardSize && y < boardSize && x >= 0 && y >= 0)
        {
            if(dir.equals("u")) dy = 1;
            else if(dir.equals("d")) dy = -1;
            else if(dir.equals("r")) dx = 1;
            else dx = -1;

            if((x + (size-1) * dx) >= boardSize || (x + (size-1) * dx) < 0 || (y + (size-1) * dy) >= boardSize || (y + (size-1) * dy) < 0) {
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

    static Coordinate processGuessInput(int x, int y, Person person) {
        Coordinate guess = null;
        if(person.isValidGuess(x, y)) guess = new Coordinate(x, y);
        else System.out.println("Invalid input, please try again");
        return guess;
    }

    static void game(Bot bot, Person person) {
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);
        int x, y;
        Coordinate guess;
        int turn = 0;

        if(random.nextBoolean()) // let the player start or let the bot start
        {
            guess = bot.guess();
            if(guess == null) {
                System.out.println("The bot could not generate a correct guess, exiting program...");
                System.exit(-1);
            }
            else {
                if(person.hit(guess)) bot.addCorrectGuess(guess);
                else bot.addBadGuess(guess);
            }
            System.out.println("----- own board -----");
            printBoard(person.getShips(), true, null, bot.getBadGuesses());
        }
        while(!bot.lost() && !person.lost())
        {
            System.out.println("Turn: " + turn++);
            // Players turn
            System.out.println("----- opponents board -----");
            printBoard(null, false, person.getCorrectGuesses(), person.getBadGuesses());

            do {
                guess = null;
                System.out.print("Please enter x coordinate of guess: ");
                try {
                    x = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input, please try again");
                    scanner.next();
                    continue;
                }
                System.out.print("Please enter y coordinate of guess: ");
                try {
                    y = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input, please try again");
                    scanner.next();
                    continue;
                }
                guess = processGuessInput(x, y, person);
            } while (guess == null);
            if(bot.hit(guess)) person.addCorrectGuess(guess);
            else person.addBadGuess(guess);

            // Bot's turn
            guess = bot.guess();
            if(guess == null) {
                System.out.println("The bot could not generate a correct guess, exiting program...");
                System.exit(-1);
            } else {
                if(person.hit(guess)) bot.addCorrectGuess(guess);
                else bot.addBadGuess(guess);
            }
            System.out.println("----- own board -----");
            printBoard(person.getShips(), true, null, bot.getBadGuesses());
        }
        if(person.lost()){
            System.out.println("You lost, better luck next time!\n");
            System.out.println("Other players board:");
            printBoard(bot.getShips(), true, null, person.getBadGuesses());
        }
        else System.out.println("Good job! You have won!!!");
    }

    static void printBoard(ArrayList<Ship> ships, boolean showNotHit, ArrayList<Coordinate> correctGuesses, ArrayList<Coordinate> badGuesses)
    {
        for(int y = boardSize-1; y >= 0; y--) {
            System.out.print(y + "| ");
            for(int x = 0; x < boardSize; x++) {
                System.out.print(getPrint(x, y,ships, showNotHit, correctGuesses, badGuesses) + " ");
            }
            System.out.println();
        }
        System.out.println(" ---------------------");
        System.out.println("   0 1 2 3 4 5 6 7 8 9");
    }

    static char getPrint(int x, int y, ArrayList<Ship> ships, boolean showNotHit, ArrayList<Coordinate> correctGuesses, ArrayList<Coordinate> badGuesses)
    {
        if(ships != null) {
            for (Ship ship : ships) {
                if (ship.isHit(x, y)) return 'X';
                if (showNotHit && ship.isNotHit(x, y)) return '#';
            }
        }
        if(correctGuesses != null && correctGuesses.contains(new Coordinate(x, y))) return 'X';
        if(badGuesses != null && badGuesses.contains(new Coordinate(x, y))) return 'o';
        return ' ';
    }
}
