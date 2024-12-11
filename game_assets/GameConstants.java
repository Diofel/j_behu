package game_assets;
import java.util.Random;

public class GameConstants {
    // Unicode constants for game symbols
    public static final String ROCK = "\u270A"; // ✊
    public static final String PAPER = "\u270B"; // ✋
    public static final String SCISSORS = "\u270C"; // ✌
    public static final String STAR = "\u2B50"; // ⭐
    public static final String HEART = "\u2764"; // ❤
    public static final String BROKEN_HEART = "\uD83D\uDC94"; // 💔
    public static final String PARTY = "\uD83C\uDF89"; // 🎉
    public static final String HANDSHAKE = "\uD83E\uDD1D"; // 🤝
    public static final String SAD = "\uD83D\uDE14"; // 😔
    public static final String FIRE = "\uD83D\uDD25"; // 🔥
    public static final String WARNING = "\u26A0"; // ⚠

    public static final String[] hand = { ROCK, PAPER, SCISSORS };
    public static final String[] PLAYER = {
            "\uD83D\uDC68", // 👨
            "\uD83D\uDC69", // 👩
            "\uD83D\uDC74", // 👴
            "\uD83D\uDC75" // 👵
    };
    public static final String[] ENEMY = {
            "\uD83D\uDC79", // 👹
            "\uD83D\uDC7B", // 👻
            "\uD83D\uDC0D", // 🐍
            "\uD83D\uDC7E" // 👾
    };

    public static final Random random = new Random();
}