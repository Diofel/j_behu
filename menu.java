import java.util.Scanner;

public class menu {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            menu m = new menu();
            while (true) {
                main_menu();
                int mainMenuChoice = input_v(scanner, 3);

                switch (mainMenuChoice) {
                    case 1:
                        //game start missing
                        m.clearScreen();
                        break;
                    case 2:
                        m.clearScreen();
                        how_to(scanner);
                        m.clearScreen();
                        break;
                    case 3:
                        m.clearScreen();
                        System.out.println("\n" +
                                "\n\n+-------------------------------------------------------------+\n" +
                                "|  You've walked the path of fate. Until next time, warrior.  |\n" +
                                "+-------------------------------------------------------------+\n");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (Exception e) {
            System.err.println("A critical error occurred: " + e.getMessage());
        }
    }

    private static void main_menu() {
        title();
        System.out.println("\n" +
                "╔════════════════════════════════════╗\n" +
                "║            HANDS OF FATE           ║\n" +
                "╚════════════════════════════════════╝\n" +
                "║ 1. Play Game                       ║\n" +
                "║ 2. How to Play                     ║\n" +
                "║ 3. Exit                            ║\n" +
                "╚════════════════════════════════════╝");
    }

    private static void how_to(Scanner scanner) {
        boolean instructions = true;
        while (instructions) {
            System.out.println("\n" +
                    "╔═══════════════════════════════════════════════════════════════════════════════════════════════╗\n" +
                    "║                                          HOW TO PLAY                                          ║\n" +
                    "╚═══════════════════════════════════════════════════════════════════════════════════════════════╝\n" +
                    " - Choose your player and enemy.\n" +
                    " - Choose a card (Rock, Paper , or Scissors) to play against your enemy.\n" +
                    " - Rock beats Scissors, Scissors beats Paper, Paper beats Rock.\n" +
                    " - Win rounds to defeat your enemy.\n" +
                    " - After 3 consecutive wins, the game switches to \"Random Cards\" mode.\n" +
                    " - In \"Random Cards\" mode, you will not know which card is behind each option – it is a surprise!\n" +
                    "═════════════════════════════════════════════════════════════════════════════════════════════════\n");
            System.out.print("Enter 1 to return to the main menu: ");

            String input = scanner.nextLine().trim();
            if (input.equals("1")) {
                instructions = false;
            } else if (input.isEmpty()) {
                System.out.println("!! Invalid input. Please enter a valid number.");
            } else {
                System.out.println("!! Invalid choice. Please enter 1 to return to the main menu.");
            }
        }
    }

    private static int input_v(Scanner scanner, int maxOption) {
        int choice = -1;
        while (choice < 1 || choice > maxOption) {
            System.out.print("Enter your choice: ");
            String input = scanner.nextLine().trim();
            if (!input.isEmpty() && input.matches("\\d+")) {
                choice = Integer.parseInt(input);
                if (choice < 1 || choice > maxOption) {
                    System.out.println("!! Invalid choice. Please enter a number between 1 and " + maxOption + ".");
                }
            } else {
                System.out.println("!! Invalid input. Please enter a valid number.");
            }
        }
        return choice;
    }

    private static void title() {
        System.out.println("     __  __  ______  _   _  _    _ ");
        System.out.println("    |  \\/  ||  ____|| \\ | || |  | |");
        System.out.println("    | \\  / || |__   |  \\| || |  | |");
        System.out.println("    | |\\/| ||  __|  | . ` || |  | |");
        System.out.println("    | |  | || |____ | |\\  || |__| |");
        System.out.println("    |_|  |_||______||_| \\_| \\____/ ");
    }

    public void clearScreen() {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("\n".repeat(50));
        }
    }
}
