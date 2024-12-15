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

    public static void lobbyMusic() {
        playAudio("game_assets/intro1.wav"); // Play the intro1.wav file from the game_assets folder
    }

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
}
