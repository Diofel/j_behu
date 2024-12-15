import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WavPlayer {
    private static List<Clip> clips = new ArrayList<>();
    private static boolean isMuted = false;

    // This class plays WAV audio files and provides mute functionality.
    // To add more music files, simply call the playAudio method with the file name as an argument.
    // Example: playAudio("anotherSong.wav");
    // You can also call the mainMenu method from another class to start playing intro1.wav.
    // Example: WavPlayer.mainMenu();

    public static void main(String[] args) {
        lobbyMusic();
        startInteractiveMenu();
    }

//lobby music kakegurui op1 instrumental
    public static void lobbyMusic() {
        playAudio("game_assets/sfx/intro1.wav"); // Play the intro1.wav file from the game_assets folder
    }

    //add more music if needed

    public static void startInteractiveMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String command = scanner.nextLine();
            if (command.equalsIgnoreCase("m")) {
                toggleMute();
            } else if (command.equalsIgnoreCase("q")) {
                break;
            }
        }
        scanner.close();
    }

    private static void playAudio(String filePath) {
        try {
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clips.add(clip);
            clip.start();
        } catch (Exception e) {
            System.out.println("Error playing audio file: " + filePath);
            e.printStackTrace();
        }
    }

    private static void toggleMute() {
        isMuted = !isMuted;
        for (Clip clip : clips) {
            if (isMuted) {
                clip.stop();
            } else {
                clip.start();
            }
        }
        System.out.println(isMuted ? "Muted" : "Unmuted");
    }

    public static void stopMusic() {
        for (Clip clip : clips) {
            if (clip.isRunning()) {
                clip.stop();
                clip.close();
            }
        }
        clips.clear();
    }

    //UI Sounds
    public static void playClick() {
        playAudio("game_assets/sfx/click.wav");
    }

    public static void playCharacterSelect() {
        playAudio("game_assets/sfx/character_select.wav");
    }

    //Rock Paper Scissors Sounds
    public static void playRock() {
        playAudio("game_assets/sfx/rock.wav");
    }

    public static void playPaper() {
        playAudio("game_assets/sfx/paper.wav");
    }

    public static void playScissors() {
        playAudio("game_assets/sfx/scissors.wav");
    }

    public static void playVersus() {
        playAudio("game_assets/sfx/versus.wav");
    }

    public static void playWinRound() {
        playAudio("game_assets/sfx/win_round.wav");
    }

    public static void playLoseRound() {
        playAudio("game_assets/sfx/lose_round.wav");
    }

    public static void playTie() {
        playAudio("game_assets/sfx/tie.wav");
    }

    //Game Outcome Sounds
    public static void playWinGame() {
        playAudio("game_assets/sfx/win_game.wav");
    }

    public static void playLoseGame() {
        playAudio("game_assets/sfx/lose_game.wav");
    }

    //Fighting Game Sounds
    public static void playPlayerAttack() {
        playAudio("game_assets/sfx/player_attack.wav");
    }

    public static void playPlayerDamaged() {
        playAudio("game_assets/sfx/player_damaged.wav");
    }

    public static void playEnemyAttack() {
        playAudio("game_assets/sfx/enemy_attack.wav");
    }

    public static void playEnemyDamaged() {
        playAudio("game_assets/sfx/enemy_damaged.wav");
    }

    public static void playHeartbeat() {
        playAudio("game_assets/sfx/heartbeat.wav");
    }

    public static void playLifeLost() {
        playAudio("game_assets/sfx/life_lost.wav");
    }
}
