//TODO: de bot zoekt altijd rond zijn laatste hit, zelfs na een volledige boot te hebben doen zinken
//TODO: de bot verbeteren door enkel te raden uit een lijst van mogelijkheden

public class Main {
    static final int BOARD_SIZE = 10;

    public static void main(String[] args) {
        new PersonVsBotCmd(BOARD_SIZE);
        //botVsBot();
    }

    static void botVsBot() {
        BotVsBotCmd game = new BotVsBotCmd(BOARD_SIZE);
        int wins = 0;
        long startTime = System.currentTimeMillis();
        for(int i = 0; i < 10; i++) {
            System.out.println("Game: " + i);
            game.initialise(false);
            if(game.game(false)) wins++;
            System.out.flush();
        }
        System.out.println("Wins: " + wins);
        System.out.println("Duration:" + (System.currentTimeMillis() - startTime) + "ms");
    }
}
