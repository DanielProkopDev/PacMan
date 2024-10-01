package Menu.HighScores;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import Menu.MenuPacman;


public class HighScoresPanel extends JPanel {
    private JList<HighScoreEntry> highScoresList;
    private HighScoresManager highScoresManager;

    private MenuPacman menu;

    private JPanel panel;

    public HighScoresPanel(MenuPacman menu){
        panel = this;
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
        highScoresManager= HighScoresManager.getInstance();
        List<HighScoreEntry> highScores = highScoresManager.getHighScores();
        this.menu=menu;

        String rodzajPisma = Font.SERIF;
        int rozmiarPisma = 34;
        int typPisma = Font.BOLD | Font.ITALIC;

        Font font = new Font(rodzajPisma,typPisma,rozmiarPisma);

        DefaultListModel<HighScoreEntry> listModel = new DefaultListModel<>();
        for (HighScoreEntry entry : highScores){
            listModel.addElement(entry);
        }

        highScoresList = new JList<>(listModel);
        highScoresList.setBackground(Color.BLACK);
        highScoresList.setForeground(Color.WHITE);
        highScoresList.setCellRenderer(new HighScoreRenderer());


        JLabel title = new JLabel("HighScores");
        title.setFont(font);
        title.setForeground(Color.CYAN);
        title.setHorizontalAlignment(JLabel.CENTER);

        JButton backButton= new JButton("Back");
        backButton.setFont(font);
        backButton.setForeground(Color.CYAN);
        backButton.setBackground(Color.BLACK);
        backButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
               menu.showMainMenu(panel);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                backButton.setForeground(Color.MAGENTA);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                backButton.setForeground(Color.CYAN);
            }
        });

        add(new JScrollPane(highScoresList),BorderLayout.CENTER);
        add(title,BorderLayout.NORTH);
        add(backButton,BorderLayout.SOUTH);
    }
}
