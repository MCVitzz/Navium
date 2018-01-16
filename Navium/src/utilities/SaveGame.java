package utilities;

import java.util.ArrayList;

public class SaveGame {
    private ArrayList<HighScore> scores;

    SaveGame(ArrayList<String> saveGame) {
        scores = new ArrayList<>(10);
        for(String scoreString : saveGame)
            scores.add(new HighScore(scoreString));
    }

    public ArrayList<HighScore> scores() {
        return this.scores;
    }

    public void saveScore(int score) {
        int index = isHighScore(score);
        if(index > 0) {
            scores.add(index, new HighScore(Integer.toString(score)));
            scores.trimToSize();
        }
    }

    public int isHighScore(int score) {
        for(HighScore highScore : scores)
            if(Integer.parseInt(highScore.score()) < score)
                return  scores.indexOf(highScore);
        return -1;
    }

    public class HighScore {
        private String score;

        HighScore(String scoreString) {
            score = scoreString;
        }

        public String score() {
            return this.score;
        }
    }

}
