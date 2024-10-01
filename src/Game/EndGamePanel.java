package Game;

import Menu.HighScores.HighScoreEntry;
import Menu.HighScores.HighScoresManager;
import Menu.MapSizeSelectionPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Date;

public class EndGamePanel extends JPanel {
    private JTextField nameField;
    private JLabel pointsLabel;
    private JLabel timeLabel;
    private JLabel dateLabel;
    private JButton saveButton;
    private HighScoresManager highScoresManager;

    private EndGamePanel panel;

    private JFrame endFrame;

    public EndGamePanel(int points, long time, JFrame frame){
        this.highScoresManager = HighScoresManager.getInstance();
        setLayout(new GridLayout(5,2));
        setBackground(Color.BLACK);
        LocalDate currentDate = LocalDate.now();
        panel=this;
        endFrame=frame;

        String rodzajPisma = Font.SERIF;
        int rozmiarPisma = 34;
        int typPisma = Font.BOLD | Font.ITALIC;

        Font font = new Font(rodzajPisma,typPisma,rozmiarPisma);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(font);
        nameLabel.setForeground(Color.CYAN);
        nameField = new JTextField();
        nameField.setFont(font);
        JLabel pointsTextLabel = new JLabel("Points:");
        pointsTextLabel.setFont(font);
        pointsTextLabel.setForeground(Color.CYAN);
        pointsLabel = new JLabel(String.valueOf(points));
        pointsLabel.setFont(font);
        pointsLabel.setForeground(Color.CYAN);
        JLabel timeTextLabel = new JLabel("Time (s):");
        timeTextLabel.setFont(font);
        timeTextLabel.setForeground(Color.CYAN);
        timeLabel = new JLabel(String.valueOf(time));
        timeLabel.setFont(font);
        timeLabel.setForeground(Color.CYAN);
        JLabel dateTextLabel = new JLabel("Date:");
        dateTextLabel.setFont(font);
        dateTextLabel.setForeground(Color.CYAN);
        dateLabel = new JLabel(String.valueOf(currentDate));
        dateLabel.setFont(font);
        dateLabel.setForeground(Color.CYAN);
        saveButton = new JButton("Save");
        saveButton.setFont(font);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.saveHighScore(points,time,currentDate);
            }
        });

        add(nameLabel);
        add(nameField);
        add(pointsTextLabel);
        add(pointsLabel);
        add(timeTextLabel);
        add(timeLabel);
        add(dateTextLabel);
        add(dateLabel);
        add(new JLabel());
        add(saveButton);

    }

    private void saveHighScore(int points, long time, LocalDate date){
      String playerName = nameField.getText();
      if (validateName(playerName)){
          HighScoreEntry entry = new HighScoreEntry(playerName,time,points,date);
          highScoresManager.addHighScore(entry);
          JOptionPane.showMessageDialog(endFrame, "Score saved!", "Success", JOptionPane.INFORMATION_MESSAGE);
          MapSizeSelectionPanel.gameOn=0;
          endFrame.dispose();

      }else {
          JOptionPane.showMessageDialog(endFrame, "Invalid name! Use only letters and numbers.");
          return;
      }

    }
    private boolean validateName(String name){
        return name.matches("[a-zA-Z0-9]+");
    }
}
