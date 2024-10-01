package Menu.HighScores;

import java.io.Serial;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class HighScoreEntry implements Serializable {

    private String name;
    private long time;
    private int points;
    private LocalDate date;

    @Serial
    private static final long serialVersionUID = 1L;

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public HighScoreEntry(String name, long time, int points, LocalDate date){
        this.name=name;
        this.time=time;
        this.points=points;
        this.date=date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return String.format("%s - %d points - %d seconds - %s", name, points, time, dateFormatter.format(date));
    }

}
