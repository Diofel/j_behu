import java.util.Scanner;

public class menu {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            main_menu();
            int main_menuChoice = input_v(scanner, 3);

            switch (main_menuChoice) {
                case 1:
                    // behu game = new behu();
                    // game.startGame(scanner);
                    break;
                case 2:
                    how_to();
                    break;
                case 3:
                    System.out.println("\n" +
                            "\n\n+-------------------------------------------------------------+\n" +
                            "|  You've walked the path of fate. Until next time, warrior!  |\n" +
                            "+-------------------------------------------------------------+\n");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.print("Invalid choice. Please try again.");
            }
        }
    }

    private static void main_menu() {
        System.out.println("\n" +
                "╔════════════════════════════════════╗\n" +
                "║            HANDS OF FATE           ║\n" +
                "╚════════════════════════════════════╝\n" +
                "║ 1. Play Game                       ║\n" +
                "║ 2. How to Play                     ║\n" +
                "║ 3. Exit                            ║\n" +
                "╔════════════════════════════════════╝");
        System.out.print(" Enter your choice: ");
    }

    private static void how_to() {
        System.out.println("\n" +
                "╔═══════════════════════════════════════════════════════════════════════════════════════════════╗\n" +
                "║                                          How to Play                                          ║\n" +
                "╚═══════════════════════════════════════════════════════════════════════════════════════════════╝\n" +
                " - Choose your player and enemy.\n" +
                " - Choose a card (Rock ✊, Paper 🖐, or Scissors ✌) to play against your enemy.\n" +
                " - Rock beats Scissors, Scissors beats Paper, Paper beats Rock.\n" +
                " - Win rounds to defeat your enemy.\n" +
                " - After 3 consecutive wins, the game switches to \"Random Cards\" mode.\n" +
                " - In \"Random Cards\" mode, you won’t know which card is behind each option – it’s a surprise!\n" +
                "═════════════════════════════════════════════════════════════════════════════════════════════════\n");
    }

    private static int input_v(Scanner scanner, int maxOption) {
        int choice = -1;

        while (choice < 0 || choice >= maxOption) {
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.print(
                        "⚠️  Invalid input: You cannot leave this blank. Please try again.\nEnter your choice: ");
                continue;
            }

            try {
                choice = Integer.parseInt(input) - 1;
                if (choice < 0 || choice >= maxOption) {
                    System.out.print("⚠️  Invalid choice! Please enter a number between 1 and " + maxOption
                            + ".\nEnter your choice: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("⚠️  Invalid input: Please enter a valid number.\nEnter your choice: ");
            }
        }
        return choice + 1;
    }
}