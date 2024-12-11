import java.util.Random;
import java.util.Scanner;

public class behu {
    private static final String[] hand = { "✊", "🖐", "✌" };
    private static final String[] player = { "👨", "👩", "👴", "👵" };
    private static final String[] enemy = { "👹", "👻", "🐍️", "🦍" };

    private static final Random random = new Random();
    private static int playerL = 10;
    private static int enemyL = 10;
    private static int c_wins = 0;
    private static boolean randomC = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean again;

        do {
            // Resetting game values at the start of a new game
            playerL = 10;
            enemyL = 10;
            c_wins = 0;
            randomC = false;

            System.out.println("╔════════════════════════════════════╗");
            System.out.println("       🌟  HANDS OF FATE  🌟");
            System.out.println("╚════════════════════════════════════╝");
            System.out.println();
            System.out.println("=====================================");
            System.out.println("-----  PLAYER  -----");
            options(player);

            System.out.print("Choose your player: ");
            int playerChoice = valid_input(scanner, player.length) - 1;
            String chosenPlayer = player[playerChoice];

            System.out.println();
            System.out.println("=====================================");
            System.out.println("-----  ENEMY  -----");
            options(enemy);

            System.out.print("Choose your enemy: ");
            int enemyChoice = valid_input(scanner, enemy.length) - 1;
            String chosenEnemy = enemy[enemyChoice];

            System.out.println();
            System.out.println("=====================================");
            System.out.println(chosenPlayer + "   V.S.   " + chosenEnemy);
            System.out.println("=====================================");
            System.out.println();
            lives_display(chosenPlayer, chosenEnemy);

            // game loop
            while (playerL > 0 && enemyL > 0) {
                if (randomC) {
                    System.out.println("Cards are now random! Pick wisely.");
                    option_r();
                } else {
                    option_n();
                }

                System.out.print("Choose your card: ");
                int player_card = valid_input(scanner, 3) - 1;

                String userHand;
                if (randomC) {
                    userHand = hand[random.nextInt(3)];
                    randomC = false;
                } else {
                    userHand = hand[player_card];
                }

                String enemyHand = hand[random.nextInt(3)];
                System.out.println();
                System.out.println("=====================================");
                System.out.println(
                        chosenPlayer + "  played: " + userHand + "   |   " + chosenEnemy + "  played: " + enemyHand);
                String result = g_result(userHand, enemyHand);
                System.out.println("─────────────────────────────────────");

                if (result.equals("win")) {
                    System.out.println("🎉  You WON this round!");
                    enemyL--;
                    c_wins++;

                    if (c_wins >= 3) {
                        randomC = true;
                        c_wins = 0;
                    }
                } else if (result.equals("lose")) {
                    System.out.println("😞  You LOST this round!");
                    playerL--;
                    c_wins = 0;
                } else {
                    System.out.println("🤝  It's a TIE!");
                }

                System.out.println("=====================================");
                System.out.println();
                lives_display(chosenPlayer, chosenEnemy);
            }

            if (playerL == 0) {
                System.out.println();
                System.out.println("=====================================");
                System.out.println(chosenPlayer + "  has been defeated by  " + chosenEnemy + ".");
                System.out.println("-----  GAME OVER  -----");
                System.out.println("💔 Don’t worry, you’ll rise again! 💔");
                System.out.println("=====================================");
                System.out.println();
            } else {
                System.out.println();
                System.out.println("=====================================");
                System.out.println(chosenPlayer + "  defeated  " + chosenEnemy + ".");
                System.out.println("-----  CONGRATULATIONS  -----");
                System.out.println("🔥  Crowd goes wild! 🔥");
                System.out.println("=====================================");
                System.out.println();
            }

            System.out.println("-------------------------------------");
            System.out.print("Do you want to play again? (yes/no): ");
            again = scanner.next().equalsIgnoreCase("yes");
            scanner.nextLine(); // Clear the buffer
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
        } while (again);

        System.out.println("Thanks for playing! Goodbye!");
        scanner.close();
    }

    private static void options(String[] options) {
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ": " + options[i]);
        }
        System.out.println("=====================================");
    }

    private static void option_n() {
        System.out.println("[ 1 ]✊        [ 2 ] 🖐        [ 3 ] ✌        ");
    }

    private static void option_r() {
        System.out.println("[ 1 ]  [ 2 ]  [ 3 ]");
    }

    private static String g_result(String userHand, String enemyHand) {
        if (userHand.equals(enemyHand)) {
            return "tie";
        }
        if ((userHand.equals("✌") && enemyHand.equals("🖐")) ||
                (userHand.equals("✊") && enemyHand.equals("✌")) ||
                (userHand.equals("🖐") && enemyHand.equals("✊"))) {
            return "win";
        }
        return "lose";
    }

    private static void lives_display(String player, String enemy) {
        String userHeart = playerL > 0 ? "❤️ " + playerL : "💔 0";
        String enemyHeart = enemyL > 0 ? "❤️ " + enemyL : "💔 0";
        System.out.println("-------------------------------------");
        System.out.println(player + "  lives: " + userHeart);
        System.out.println(enemy + "  lives: " + enemyHeart);
        System.out.println("-------------------------------------");
    }

    private static int valid_input(Scanner scanner, int maxOption) {
        int choice = -1;

        while (choice < 0 || choice >= maxOption) {
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("⚠️  Invalid input: You cannot leave this blank. Please try again.");
                System.out.print("Choose your card: ");
                continue;
            }

            try {
                choice = Integer.parseInt(input) - 1;
                if (choice < 0 || choice >= maxOption) {
                    System.out.println("⚠️  Invalid choice! Please enter a number between 1 and " + maxOption + ".");
                    System.out.print("Choose your card: ");
                }
            } catch (NumberFormatException e) {
                System.out.println("⚠️  Invalid input: Please enter a valid number.");
                System.out.print("Choose your card: ");
            }
        }
        return choice + 1;
    }
}