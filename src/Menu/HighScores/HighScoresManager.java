package Menu.HighScores;

import map.MapDeck;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HighScoresManager {

    public static HighScoresManager instance=null;
    private static final String FILE_NAME= "./highscores.ser";
    private List<HighScoreEntry> highScores;

    private HighScoresManager(){
        highScores=new ArrayList<>();

    }
    public static HighScoresManager getInstance(){
        if (instance==null){
            synchronized (HighScoresManager.class){
                if (instance==null){
                    instance=new HighScoresManager();
                }
            }
        }
        return instance;
    }
    private void loadHighScores() {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))){
            highScores = (List<HighScoreEntry>) ois.readObject();
        }catch (FileNotFoundException e){
            highScores = new ArrayList<>();
            saveHighScores();
            loadHighScores();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void saveHighScores(){
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))){
            oos.writeObject(highScores);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public List<HighScoreEntry> getHighScores(){
        loadHighScores();
        return highScores;
    }

    public void addHighScore(HighScoreEntry entry){
        loadHighScores();
        highScores.add(entry);
        Collections.sort(highScores, (e1, e2) -> {
            int pointsCompare = Integer.compare(e2.getPoints(), e1.getPoints());
            if (pointsCompare != 0) {
                return pointsCompare;
            } else {
                return Long.compare(e1.getTime(), e2.getTime());
            }
        });
        saveHighScores();
    }
}
