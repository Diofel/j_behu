import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.Scanner;

public class behu {
    // Unicode constants for game symbols
    private static final String ROCK = "\u270A"; // ✊
    private static final String PAPER = "\u270B"; // ✋
    private static final String SCISSORS = "\u270C"; // ✌
    private static final String STAR = "\u2B50"; // ⭐
    private static final String HEART = "\u2764"; // ❤
    private static final String BROKEN_HEART = "\uD83D\uDC94"; // 💔
    private static final String PARTY = "\uD83C\uDF89"; // 🎉
    private static final String HANDSHAKE = "\uD83E\uDD1D"; // 🤝
    private static final String SAD = "\uD83D\uDE14"; // 😔
    private static final String FIRE = "\uD83D\uDD25"; // 🔥
    private static final String WARNING = "\u26A0"; // ⚠

    private static final String[] hand = { ROCK, PAPER, SCISSORS };
    private static final String[] player = {
            "\uD83D\uDC68", // 👨
            "\uD83D\uDC69", // 👩
            "\uD83D\uDC74", // 👴
            "\uD83D\uDC75" // 👵
    };
    private static final String[] enemy = {
            "\uD83D\uDC79", // 👹
            "\uD83D\uDC7B", // 👻
            "\uD83D\uDC0D", // 🐍
            "\uD83D\uDC7E" // 👾
    };

    private static final Random random = new Random();
    private static int playerLives;
    private static int enemyLives;
    private static int consecutiveWins;
    private static boolean randomCards;

    static {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            // Force console to use UTF-8
            new ProcessBuilder("cmd", "/c", "chcp", "65001").inheritIO().start().waitFor();
        } catch (Exception e) {
            System.err.println("Warning: Console encoding could not be set to UTF-8");
        }

        Scanner scanner = new Scanner(System.in, "UTF-8");
        boolean playAgain;

        do {
            clearScreen(); // Clear at start of new game
            initializeGame();
            String chosenPlayer = selectCharacter(scanner, "PLAYER", player);
            String chosenEnemy = selectCharacter(scanner, "ENEMY", enemy);

            displayVersusScreen(chosenPlayer, chosenEnemy);
            playGame(scanner, chosenPlayer, chosenEnemy);
            displayGameResult(chosenPlayer, chosenEnemy);

            playAgain = askPlayAgain(scanner);
        } while (playAgain);

        System.out.println("Thanks for playing! Goodbye!");
        scanner.close();
    }

    private static void initializeGame() {
        playerLives = 10;
        enemyLives = 10;
        consecutiveWins = 0;
        randomCards = false;

        System.out.println("╔════════════════════════════════════╗");
        System.out.println("       " + STAR + "  HANDS OF FATE  " + STAR);
        System.out.println("╚════════════════════════════════════╝\n");
    }

    private static String selectCharacter(Scanner scanner, String type, String[] options) {
        System.out.println("=====================================");
        System.out.println("-----  " + type + "  -----");
        displayOptions(options);

        System.out.print("Choose your " + type.toLowerCase() + ": ");
        int choice = getValidInput(scanner, options.length);
        return options[choice - 1];
    }

    private static void displayOptions(String[] options) {
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ": " + options[i]);
        }
        System.out.println("=====================================");
    }

    private static void displayVersusScreen(String player, String enemy) {
        System.out.println("\n=====================================");
        System.out.println(player + "   V.S.   " + enemy);
        System.out.println("=====================================\n");
        displayLives(player, enemy);
    }

    private static void playGame(Scanner scanner, String chosenPlayer, String chosenEnemy) {
        while (playerLives > 0 && enemyLives > 0) {
            displayCardOptions();
            String playerHand = getPlayerHand(scanner);
            String enemyHand = hand[random.nextInt(hand.length)];

            displayRoundResult(chosenPlayer, playerHand, chosenEnemy, enemyHand);
            updateGameState(determineWinner(playerHand, enemyHand), chosenPlayer, playerHand, chosenEnemy, enemyHand);
            displayLives(chosenPlayer, chosenEnemy);
        }
    }

    private static void displayCardOptions() {
        if (randomCards) {
            System.out.println("Cards are now random! Pick wisely.");
            System.out.println("[ 1 ]  [ 2 ]  [ 3 ]");
        } else {
            System.out.println("[ 1 ]" + ROCK + "  [ 2 ]" + PAPER + "  [ 3 ]" + SCISSORS);
        }
    }

    private static String getPlayerHand(Scanner scanner) {
        System.out.print("Choose your card: ");
        int choice = getValidInput(scanner, 3);

        if (randomCards) {
            randomCards = false;
            return hand[random.nextInt(hand.length)];
        }
        return hand[choice - 1];
    }

    private static void displayRoundResult(String player, String playerHand, String enemy, String enemyHand) {
        System.out.println("\n=====================================");
        System.out.println(player + "  played: " + playerHand + "   |   " + enemy + "  played: " + enemyHand);
        System.out.println("─────────────────────────────────────");
    }

    private static String determineWinner(String playerHand, String enemyHand) {
        if (playerHand.equals(enemyHand))
            return "tie";
        if ((playerHand.equals(SCISSORS) && enemyHand.equals(PAPER)) ||
                (playerHand.equals(ROCK) && enemyHand.equals(SCISSORS)) ||
                (playerHand.equals(PAPER) && enemyHand.equals(ROCK))) {
            return "win";
        }
        return "lose";
    }

    private static void updateGameState(String result, String player, String playerHand, String enemy,
            String enemyHand) {
        switch (result) {
            case "win":
                clearScreen();
                animation("player", player, playerHand, enemy, enemyHand);
                System.out.println(PARTY + "  You WON this round!");
                enemyLives--;
                consecutiveWins++;
                if (consecutiveWins >= 3) {
                    randomCards = true;
                    consecutiveWins = 0;
                }
                break;
            case "lose":
                clearScreen();
                animation("enemy", player, playerHand, enemy, enemyHand);
                System.out.println(SAD + "  You LOST this round!");
                playerLives--;
                consecutiveWins = 0;
                break;
            default:
                clearScreen();
                animation("tie", player, playerHand, enemy, enemyHand);
                System.out.println(HANDSHAKE + "  It's a TIE!");
        }
        System.out.println("=====================================\n");
    }

    private static void displayLives(String player, String enemy) {
        System.out.println("-------------------------------------");
        System.out.println(player + "  lives: " + (playerLives > 0 ? HEART + " " + playerLives : BROKEN_HEART + " 0"));
        System.out.println(enemy + "  lives: " + (enemyLives > 0 ? HEART + " " + enemyLives : BROKEN_HEART + " 0"));
        System.out.println("-------------------------------------");
    }

    private static void displayGameResult(String player, String enemy) {
        System.out.println("\n=====================================");
        if (playerLives == 0) {
            System.out.println(player + "  has been defeated by  " + enemy + ".");
            System.out.println("-----  GAME OVER  -----");
            System.out.println(BROKEN_HEART + " Don't worry, you'll rise again! " + BROKEN_HEART);
        } else {
            System.out.println(player + "  defeated  " + enemy + ".");
            System.out.println("-----  CONGRATULATIONS  -----");
            System.out.println(FIRE + "  Crowd goes wild! " + FIRE);
        }
        System.out.println("=====================================\n");
    }

    private static boolean askPlayAgain(Scanner scanner) {
        System.out.println("-------------------------------------");
        System.out.print("Do you want to play again? (yes/no): ");
        boolean playAgain = scanner.next().equalsIgnoreCase("yes");
        scanner.nextLine(); // Clear buffer
        clearScreen(); // Clear before starting new game
        return playAgain;
    }

    private static int getValidInput(Scanner scanner, int maxOption) {
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
                System.out.println(WARNING + "  Invalid input: Please enter a valid number.");
            }
            System.out.print("Choose again: ");
        }
    }

    private static void clearScreen() {
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

    private static void delay(int ms) { // milliseconds
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void animation(String winner, String player, String playerHand, String enemy, String enemyHand) {
        if (winner.equals("player")) {
            // show guess first
            System.out.println(player + playerHand + "     " + enemyHand + enemy);
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
            delay(1000);
            clearScreen();
            // then attack animation
            System.out.println(player + "      " + enemyHand + enemy);
            delay(50);
            clearScreen();
            System.out.println(player + "     " + enemyHand + " " + enemy);
            delay(50);
            clearScreen();
            System.out.println(player + "    " + enemyHand + "  " + enemy);
            delay(50);
            clearScreen();
            System.out.println(player + "   " + enemyHand + "   " + enemy);
            delay(50);
            clearScreen();
            System.out.println(player + "  " + enemyHand + "    " + enemy);
            delay(50);
            clearScreen();
            System.out.println(player + " " + enemyHand + "    " + enemy);
            delay(50);
            clearScreen();
            System.out.println(player + enemyHand + "     " + enemy);
            delay(50);
            clearScreen();
            // take damage (as if nadula)
            System.out.println(enemyHand + "      " + enemy);
            delay(50);
            clearScreen();
            // go back
            System.out.println(player + enemyHand + "      " + enemy);
            delay(50);
            clearScreen();

        } else if (winner.equals("tie")) {
            System.out.println(player + playerHand + "     " + enemyHand + enemy);
            delay(1000);
            clearScreen();
        }
    }
}