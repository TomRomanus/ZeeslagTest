import java.util.ArrayList;

public class BotVsBotCmd {
    Bot bot1;
    Bot bot2;
    static int boardSize;

    public BotVsBotCmd(int boardSize) {
        BotVsBotCmd.boardSize = boardSize;
        bot1 = new Bot(boardSize);
        bot2 = new Bot(boardSize);
    }

    public void initialise(boolean printOutputs) {
        bot1.clearAll();
        bot2.clearAll();
        Integer[] sizes = {5, 4, 3, 3, 2};
        for(int i = 0; i < 5 ; i++) {
            bot1.addShip(sizes[i]);
            bot2.addShip(sizes[i]);
        }
        if(printOutputs) {
            System.out.println("----- Bot1's board -----");
            printBoard(bot1.getShips(), true, null, null);
            System.out.println("----- Bot2's board -----");
            printBoard(bot2.getShips(), true, null, null);
        }
    }

    public boolean game(boolean printOutputs) {
        Coordinate guess;
        int turn = 0;

        while(!bot1.lost() && !bot2.lost())
        {
            if(printOutputs) {
                System.out.println("------------ Turn " + turn++ + " ------------");
                System.out.println();
            }
            // Bot1's turn
            guess = bot1.guess();
            if(guess == null) {
                System.out.println("The bot could not generate a correct guess, exiting program...");
                System.exit(-1);
            } else {
                if(bot2.hit(guess)) bot1.addCorrectGuess(guess);
                else bot1.addBadGuess(guess);
            }
            if(printOutputs) {
                System.out.println("----- Bot1's guesses -----");
                printBoard(bot2.getShips(), true, null, bot1.getBadGuesses());
            }

            // Bot2's turn
            guess = bot2.guess();
            if(guess == null) {
                System.out.println("The bot could not generate a correct guess, exiting program...");
                System.exit(-1);
            } else {
                if(bot1.hit(guess)) bot2.addCorrectGuess(guess);
                else bot2.addBadGuess(guess);
            }
            if(printOutputs) {
                System.out.println("----- Bot2's guesses -----");
                printBoard(bot1.getShips(), true, null, bot2.getBadGuesses());
                System.out.println();
            }
        }
        //if(printOutputs) {
            if (bot1.lost()) System.out.println("Bot 2 won");
            else System.out.println("Bot 1 won");
            System.out.println();
            System.out.println("Hit/Miss ratio bot 1: "
                    + bot1.getCorrectGuesses().size() + "/"
                    + bot1.getBadGuesses().size());
            System.out.println("Hit/Miss ratio bot 2: "
                    + bot2.getCorrectGuesses().size() + "/"
                    + bot2.getBadGuesses().size());
        //}
        return bot1.lost();
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
