package Menu;

import Game.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MapSizeSelectionPanel extends JPanel {
    private final JFrame frame;
    private final MenuPacman menu;

    private final JPanel panel;

    private JFrame game;

    public static int gameOn=0;

    public MapSizeSelectionPanel(JFrame frame, MenuPacman menu) {
        this.frame = frame;
        this.menu = menu;
        this.panel=this;
        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);

        JLabel label = new JLabel("Select Map Size");
        label.setFont(new Font(Font.SERIF, Font.BOLD | Font.ITALIC, 34));
        label.setForeground(Color.CYAN);

        String[] mapSizes = {"800x800","1000x1000", "1200x1200", "1400x1200","1600x1200"};
        JComboBox<String> mapSizeComboBox = new JComboBox<>(mapSizes);
        mapSizeComboBox.setFont(new Font(Font.SERIF, Font.PLAIN, 24));
        mapSizeComboBox.setBackground(Color.BLACK);
        mapSizeComboBox.setForeground(Color.CYAN);

        JButton startGameButton = new JButton("Start Game");
        startGameButton.setBackground(Color.BLACK);
        startGameButton.setForeground(Color.CYAN);
        startGameButton.setFont(new Font(Font.SERIF, Font.BOLD | Font.ITALIC, 34));
        startGameButton.setBorder(null);
        startGameButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String selectedSize = (String) mapSizeComboBox.getSelectedItem();
                int height=0;
                int width=0;
                switch (selectedSize){
                    case "800x800":
                        height=800;
                        width=800;
                        break;
                    case "1000x1000":
                        height=1000;
                        width=1000;
                        break;
                    case "1200x1200":
                        height=1200;
                        width=1200;
                        break;
                    case "1400x1200":
                        height=1200;
                        width=1400;
                        break;
                    case "1600x1200":
                        height=1200;
                        width=1600;
                        break;
                    default:
                        JOptionPane.showMessageDialog(frame, "You have to choose size first", "Wrong size", JOptionPane.INFORMATION_MESSAGE);

                }
                if(gameOn==0){
                game = new GameFrame(frame, width,height);
                gameOn=1;
                }

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                startGameButton.setForeground(Color.MAGENTA);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                startGameButton.setForeground(Color.CYAN);

            }
        });


        JButton backButton = new JButton("Back");
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.CYAN);
        backButton.setFont(new Font(Font.SERIF, Font.BOLD | Font.ITALIC, 34));
        backButton.setBorder(null);
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

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 30, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(label, gbc);
        gbc.gridy++;
        add(mapSizeComboBox, gbc);
        gbc.gridy++;
        add(startGameButton, gbc);
        gbc.gridy++;
        add(backButton, gbc);
    }
}

