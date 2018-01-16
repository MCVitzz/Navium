package utilities;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Arrays;

public class SaveManager {
    private String fileName = "highscores.txt";
    private SaveGame saveGame;

    public void loadSaveGame(PApplet applet) {
        saveGame = new SaveGame(new ArrayList<>(Arrays.asList(applet.loadStrings(fileName))));
    }

    public void saveSaveGame(PApplet applet) {
        applet.saveStrings(fileName, saveGameToString());
    }

    public void saveScore(int score) {
        saveGame.saveScore(score);
    }

    private String[] saveGameToString() {
        ArrayList<String> highscores = new ArrayList<>();
        for (SaveGame.HighScore highScore : saveGame.scores())
            highscores.add(highScore.score());
        return highscores.toArray(new String[highscores.size()]);
    }

    public SaveGame saveGame() {
        return this.saveGame;
    }
}
