import static game_assets.GameConstants.*;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;


interface GameActions {
    void basicAttack();  // The standard attack animation
    void playHand(String hand);  // Method for playing rock/paper/scissors
    void celebrate();  // Victory celebration
}

class Game { // Used hierarchical inheritance
    private int consecutiveWins;
    private boolean randomCards;

    Player p = new Player(); // For overriden method calling
    Enemy e = new Enemy(); // For overriden method calling

    static {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    // Start of the Game
    public void initializeGame() {
        consecutiveWins = 0;
        randomCards = false;

        // Title and background music
        WavPlayer.lobbyMusic();
        System.out.println(mainmenu());

    }

    // Selects the Character
    public String selectCharacter(Scanner scanner, String type, String[] options) {
        WavPlayer.playClick(); // Play click sound when entering character selection
        System.out.println("\n=====================================");
        System.out.println("-----  " + type + "  -----");

        // Display Options
        for (int i = 0; i < options.length; i++) {

            if (type.equals("PLAYER")) {
                System.out.println((i + 1) + ": " + options[i]);
            }

            if (type.equals("ENEMY")) {
                System.out.print((i + 1) + ": " + options[i]);
                switch (i) {
                    case 0:
                        System.out.println(" | Easy");
                        break;
                    case 1:
                        System.out.println(" | Normal");
                        break;
                    case 2:
                        System.out.println(" | Hard");
                        break;
                    case 3:
                        System.out.println(" | Hell");
                        break;
                }
            }
        }
        System.out.println("====================================="); // Goes to the displayOptions to print Options

        // Player Input for Character
        System.out.print("Choose your " + type.toLowerCase() + ": ");

        int choice = getValidInput(scanner, options.length);

        if (type.equals("ENEMY")) {
            switch (choice) {
                case 1:
                    e.enemyLives -= 7;
                    break;
                case 2:
                    e.enemyLives -= 5;
                    break;
                case 3:
                    e.enemyLives -= 3;
                    break;
            }
        }
        WavPlayer.playCharacterSelect(); // Play character select sound when character is chosen
        return options[choice - 1];
    }

    // Prints the versus screen.
    public void displayVersusScreen(String player, String enemy) {
        WavPlayer.playVersus(); // Play versus sound when showing versus screen
        System.out.println("\n=====================================");
        System.out.println(player + "   V.S.   " + enemy);
        System.out.println("=====================================\n");
        displayLives(player, enemy);
    }

    // Play game method.
    public void playGame(Scanner scanner, String chosenPlayer, String chosenEnemy) {
        WavPlayer.stopMusic(); // stops background music
        /*
         * WavPlayer.<name of song>(); to play a song again
         * make sure it is in the game_assets folder and is called on wavplayer.java
         */
        while (p.playerLives > 0 && e.enemyLives > 0) {
            displayCardOptions();
            String playerHand = getPlayerHand(scanner);
            String enemyHand = hand[random.nextInt(hand.length)];
            displayRoundResult(chosenPlayer, playerHand, chosenEnemy, enemyHand);
            updateGameState(determineWinner(playerHand, enemyHand), chosenPlayer, playerHand, chosenEnemy, enemyHand);
            displayLives(chosenPlayer, chosenEnemy);
        }
    }

    //
    public void displayCardOptions() {
        if (randomCards) {
            System.out.println("Cards are now random! Pick wisely.");
            System.out.println("[ 1 ]  [ 2 ]  [ 3 ]");
        } else {
            System.out.println("[ 1 ]" + ROCK + "  [ 2 ]" + PAPER + "  [ 3 ]" + SCISSORS);
        }
    }

    public String getPlayerHand(Scanner scanner) {
        System.out.print("Choose your card: ");
        int choice = getValidInput(scanner, 3);

        if (randomCards) {
            randomCards = false;
            return hand[random.nextInt(hand.length)];
        }
        // Play appropriate sound based on choice
        switch (hand[choice - 1]) {
            case ROCK:
                WavPlayer.playRock();
                break;
            case PAPER:
                WavPlayer.playPaper();
                break;
            case SCISSORS:
                WavPlayer.playScissors();
                break;
        }
        return hand[choice - 1];
    }

    public void displayRoundResult(String player, String playerHand, String enemy, String enemyHand) {
        System.out.println("\n=====================================");
        System.out.println(player + "  played: " + playerHand + "   |   " + enemy + "  played: " + enemyHand);
        System.out.println("─────────────────────────────────────");
    }

    public String determineWinner(String playerHand, String enemyHand) {
        if (playerHand.equals(enemyHand))
            return "tie";
        if ((playerHand.equals(SCISSORS) && enemyHand.equals(PAPER)) ||
                (playerHand.equals(ROCK) && enemyHand.equals(SCISSORS)) ||
                (playerHand.equals(PAPER) && enemyHand.equals(ROCK))) {
            return "win";
        }
        return "lose";
    }

    public void updateGameState(String result, String player, String playerHand, String enemy,
            String enemyHand) {
        switch (result) {
            case "win":
                clearScreen();
                animation("player", player, playerHand, enemy, enemyHand);
                p.celebrate();
                System.out.println(PARTY + "  You WON this round!");
                e.drainlife();
                consecutiveWins++;
                if (consecutiveWins >= 3) {
                    randomCards = true;
                    consecutiveWins = 0;
                }
                break;
            case "lose":
                clearScreen();
                WavPlayer.playLoseRound(); // Play lose round sound
                animation("enemy", player, playerHand, enemy, enemyHand);
                System.out.println(SAD + "  You LOST this round!");
                p.drainlife();
                consecutiveWins = 0;
                break;
            default:
                clearScreen();
                WavPlayer.playTie(); // Play tie sound
                animation("tie", player, playerHand, enemy, enemyHand);
                System.out.println(HANDSHAKE + "  It's a TIE!");
        }
        System.out.println("=====================================\n");
    }

    public void displayLives(String player, String enemy) {
        System.out.println("-------------------------------------");
        System.out.println(
                player + "  lives: " + (p.playerLives > 0 ? HEART + " " + p.playerLives : BROKEN_HEART + " 0"));
        System.out.println(enemy + "  lives: " + (e.enemyLives > 0 ? HEART + " " + e.enemyLives : BROKEN_HEART + " 0"));
        System.out.println("-------------------------------------");
    }

    public void displayGameResult(String player, String enemy) {
        System.out.println("\n=====================================");
        if (p.playerLives == 0) {
            WavPlayer.playLoseGame(); // Play lose game sound
            System.out.println(player + "  has been defeated by  " + enemy);
            System.out.println(" __            _    _        _  _  ");
            System.out.println("/__  /\\  |\\/| |_   / \\ \\  / |_ |_| ");
            System.out.println("\\_| /--\\ |  | |_   \\_/  \\/  |_ | \\ ");
            System.out.println("                                    ");
            System.out.println(BROKEN_HEART + " Don't worry, you'll rise again! " + BROKEN_HEART);
        } else {
            WavPlayer.playWinGame(); // Play win game sound
            System.out.println(player + "  defeated  " + enemy);
            System.out.println(" _  _        __  _       ___  __ ");
            System.out.println("/  / \\ |\\ | /__ |_)  /\\   |  (_  ");
            System.out.println("\\_ \\_/ | \\| \\_| | \\ /--\\  |  __) ");
            System.out.println("                                    ");
            System.out.println(FIRE + "  Crowd goes wild! " + FIRE);
        }
        System.out.println("=====================================\n");
    }

    public boolean askPlayAgain(Scanner scanner) {
        System.out.println("-------------------------------------");
        System.out.print("Do you want to play again? (yes/no): ");
        boolean playAgain = scanner.next().equalsIgnoreCase("yes");

        if (playAgain) {
            e.enemyLives = 10;
            p.playerLives = 10;
        }

        scanner.nextLine(); // Clear buffer
        clearScreen(); // Clear before starting new game
        return playAgain;
    }

    public int getValidInput(Scanner scanner, int maxOption) {
        while (true) {
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println(WARNING + "  Invalid input: You cannot leave this blank. Please try again.");
                System.out.print("Choose again: ");
                continue;
            }

            try {
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= maxOption) {
                    return choice;
                }
                System.out
                        .println(WARNING + "  Invalid choice! Please enter a number between 1 and " + maxOption + ".");
            } catch (NumberFormatException e) {
                System.out.println(WARNING + "  Invalid input: Please enter a valid number."); // Exception Handling
            }
            System.out.print("Choose again: ");
        }
    }

    public void clearScreen() {
        try {
            String os = System.getProperty("os.name").toLowerCase();

            if (os.contains("win")) {
                // For Windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // For Unix/Linux/MacOS
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            // Fallback: print several newlines
            System.out.println("\n".repeat(50));
        }
    }

    public void delay(int ms) { // milliseconds
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) { // exception handling
            e.printStackTrace();
        }
    }

    public void animation(String winner, String player, String playerHand, String enemy, String enemyHand) {
        if (winner.equals("player")) {
            // show guess first
            System.out.println(player + playerHand + "     " + enemyHand + enemy);
            WavPlayer.playPlayerAttack(); // Play player attack sound
            p.basicAttack(); // interface
            p.attack();
            WavPlayer.playEnemyDamaged(); // Play enemy damaged sound
            e.damaged();
            delay(1000);
            clearScreen();
            // then attack animation
            System.out.println(player + playerHand + "      " + enemy);
            delay(50);
            clearScreen();
            System.out.println(player + " " + playerHand + "     " + enemy);
            delay(50);
            clearScreen();
            System.out.println(player + "  " + playerHand + "    " + enemy);
            delay(50);
            clearScreen();
            System.out.println(player + "   " + playerHand + "   " + enemy);
            delay(50);
            clearScreen();
            System.out.println(player + "    " + playerHand + "  " + enemy);
            delay(50);
            clearScreen();
            System.out.println(player + "     " + playerHand + " " + enemy);
            delay(50);
            clearScreen();
            System.out.println(player + "      " + playerHand + enemy);
            delay(50);
            clearScreen();
            // take damage (as if nadula)
            System.out.println(player + "      " + playerHand);
            delay(50);
            clearScreen();
            // go back
            System.out.println(player + "      " + playerHand + enemy);
            delay(50);
            clearScreen();
        } else if (winner.equals("enemy")) {
            // show guess first
            System.out.println(player + playerHand + "     " + enemyHand + enemy);
            WavPlayer.playPlayerDamaged(); // Play player damaged sound
            e.basicAttack(); // interface
            p.damaged();
            WavPlayer.playEnemyAttack(); // Play enemy attack sound
            e.attack();
            delay(1000);
            clearScreen();
            // then attack animation
            System.out.println(player + "       " + enemyHand + enemy);
            delay(50);
            clearScreen();
            System.out.println(player + "      " + enemyHand + " " + enemy);
            delay(50);
            clearScreen();
            System.out.println(player + "     " + enemyHand + "  " + enemy);
            delay(50);
            clearScreen();
            System.out.println(player + "    " + enemyHand + "   " + enemy);
            delay(50);
            clearScreen();
            System.out.println(player + "   " + enemyHand + "    " + enemy);
            delay(50);
            clearScreen();
            System.out.println(player + "  " + enemyHand + "     " + enemy);
            delay(50);
            clearScreen();
            System.out.println(player + " " + enemyHand + "      " + enemy);
            delay(50);
            clearScreen();
            System.out.println(player + enemyHand + "       " + enemy);
            delay(50);
            clearScreen();
            // take damage (as if nadula)
            System.out.println(" " + enemyHand + "        " + enemy);
            delay(50);
            clearScreen();
            // go back
            System.out.println(player + enemyHand + "       " + enemy);
            delay(50);
            clearScreen();

        } else if (winner.equals("tie")) {
            System.out.println(player + playerHand + "     " + enemyHand + enemy);
            delay(1000);
            clearScreen();
        }
    }
}

// For ABSTRACTION and INHERITANCE
abstract class Character {
    private int lives = 10;

    // For Encapsulation
    public int getLives() {
        return this.lives;
    }

    // For abstract methods
    abstract void attack();
    abstract void damaged();
    abstract void drainlife();
}

class Player extends Character implements GameActions {
    int playerLives = getLives();
    private String lastPlayedHand;

    // Constructor
    public Player() {
        super();
        this.lastPlayedHand = "";
    }

    // Constructor overloading
    public Player(int startingLives) {
        super();
        this.playerLives = startingLives;
        this.lastPlayedHand = "";
    }

    @Override
    public void attack() {
        System.out.println("\nPlayer: Heh.");
    }

    @Override
    public void damaged() {
        WavPlayer.playLifeLost();
        System.out.print("\nPlayer: Oof!");
    }

    @Override
    public void drainlife() {
        playerLives--;
        if (playerLives <= 2) {
            WavPlayer.playHeartbeat();
        }
    }

    @Override
    public void basicAttack() {
        System.out.println("\nPlayer did a basic attack!");
    }

    @Override
    public void playHand(String hand) {
        this.lastPlayedHand = hand;
        System.out.println("\nPlayer plays " + hand + "!");
    }

    @Override
    public void celebrate() {
        System.out.println("\nPlayer: Victory!");
        WavPlayer.playWinRound();
    }
}

class Enemy extends Character implements GameActions {
    int enemyLives = getLives();
    private String lastPlayedHand;

    // Constructor
    public Enemy() {
        super();
        this.lastPlayedHand = "";
    }

    // Constructor overloading
    public Enemy(int startingLives) {
        super();
        this.enemyLives = startingLives;
        this.lastPlayedHand = "";
    }

    @Override
    public void attack() {
        System.out.println("\nEnemy: Rah!");
    }

    @Override
    public void damaged() {
        WavPlayer.playLifeLost();
        System.out.println("Enemy: Argh.");
    }

    @Override
    public void drainlife() {
        enemyLives--;
        if (enemyLives <= 2) {
            WavPlayer.playHeartbeat();
        }
    }

    @Override
    public void basicAttack() {
        System.out.println("\nEnemy did a basic attack!");
    }

    @Override
    public void playHand(String hand) {
        this.lastPlayedHand = hand;
        System.out.println("\nEnemy plays " + hand + "!");
    }

    @Override
    public void celebrate() {
        System.out.println("\nEnemy: Muhahaha!");
        WavPlayer.playWinRound();
    }
}

class Bye { // constructor
    public Bye() {
        System.out.println("Thanks for playing! Goodbye!");
    }
}

public class behu {
    public static void main(String[] args) {
        Game g = new Game(); // Initializes an object "g" from the class Game which extends to the class
                             // Enemy

        try {
            // Force console to use UTF-8
            new ProcessBuilder("cmd", "/c", "chcp", "65001").inheritIO().start().waitFor();
        } catch (Exception e) {
            System.err.println("Warning: Console encoding could not be set to UTF-8");
        }

        Scanner scanner = new Scanner(System.in, "UTF-8");
        boolean playAgain;

        do {
            g.clearScreen(); // Clear at start of new game
            g.initializeGame(); // Initializes character health and enemy health + Title Sysout
            String chosenPlayer = g.selectCharacter(scanner, "PLAYER", PLAYER); // Select Player Character
            String chosenEnemy = g.selectCharacter(scanner, "ENEMY", ENEMY); // Select Enemy Character
            g.clearScreen();

            g.displayVersusScreen(chosenPlayer, chosenEnemy); // Print versus screen
            g.playGame(scanner, chosenPlayer, chosenEnemy);
            g.displayGameResult(chosenPlayer, chosenEnemy);

            playAgain = g.askPlayAgain(scanner);
        } while (playAgain);

        Bye b = new Bye(); // used this to replace the goodbye message to have a constructor requirement
        // System.out.println("Thanks for playing! Goodbye!");
        scanner.close();
    }

}