package Menu;

import Game.GameFrame;
import Menu.HighScores.HighScoresPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MenuPacman extends JFrame {

    BufferedImage menuGhost;
    BufferedImage menuPacmanLogo;

    BufferedImage menuGhost1;
    BufferedImage menuGhost2;

    private final JFrame frame;

    private final JPanel mainPanel;

    private HighScoresPanel highScoresPanel;

    public MenuPacman() {
        frame = new JFrame();
        frame.setTitle("Pacman Menu");

        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        final int BASE_SCREEN_WIDTH = 1024;
        final int BASE_SCREEN_HEIGHT = 768;
        final int BASE_MENU_WIDTH=600;
        final int BASE_MENU_HEIGHT=400;
        double widthScale = screenWidth / (double) BASE_SCREEN_WIDTH;
        double heightScale = screenHeight / (double) BASE_SCREEN_HEIGHT;
        double scale = Math.min(widthScale, heightScale);

        int scaledWidth = (int) (BASE_MENU_WIDTH*scale);
        int scaledHeight = (int) (BASE_MENU_HEIGHT*scale);
        frame.setPreferredSize(new Dimension(scaledWidth, scaledHeight));
        String rodzajPisma = Font.SERIF;
        int rozmiarPisma = 34;
        int typPisma = Font.BOLD | Font.ITALIC;

        Font font = new Font(rodzajPisma,typPisma,rozmiarPisma);

        try {
            this.menuGhost = ImageIO.read(new File("./img/ghostRedMenu1.png"));
            this.menuGhost1=ImageIO.read(new File("./img/filotetowy.png"));
            this.menuGhost2=ImageIO.read(new File("./img/zielone.png"));
            this.menuPacmanLogo = ImageIO.read(new File("./img/PacmanMenu.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }


         mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, getWidth(), getHeight());
                // Skalowanie obrazów w zależności od rozmiaru panelu
                int panelWidth = getWidth();
                int panelHeight = getHeight();

                int pacmanLogoWidth = panelWidth / 3;
                int pacmanLogoHeight = (int) (menuPacmanLogo.getHeight() * ((double) pacmanLogoWidth / menuPacmanLogo.getWidth()));

                int ghostSize = panelWidth / 5;

                g.drawImage(menuPacmanLogo, (panelWidth - pacmanLogoWidth) / 2, panelHeight / 10, pacmanLogoWidth, pacmanLogoHeight, null);
                g.drawImage(menuGhost, (int) (panelWidth * 0.75) - ghostSize / 2, (int) (panelHeight * 0.75) - ghostSize / 2, ghostSize, ghostSize, null);
                g.drawImage(menuGhost1, (int) (panelWidth * 0.25) - ghostSize / 2, (int) (panelHeight * 0.75) - ghostSize / 2, ghostSize, ghostSize, null);
                g.drawImage(menuGhost2, (int) (panelWidth * 0.25) - (ghostSize / 2)-40, (int) (panelHeight * 0.25) - ghostSize / 2, ghostSize, ghostSize, null);
            }
        };
        mainPanel.setLayout(new GridBagLayout());


        JButton newGameButton = new JButton("New Game");
        JButton highScoresButton = new JButton("High Scores");
        JButton quitButton = new JButton("Exit");


        newGameButton.setBackground(Color.BLACK);
        newGameButton.setForeground(Color.CYAN);
        newGameButton.setFont(font);
        newGameButton.setBorder(null);
        newGameButton.setToolTipText("New Game");
        newGameButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

                showMapSizeSelection();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
               newGameButton.setForeground(Color.MAGENTA);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                newGameButton.setForeground(Color.CYAN);

            }
        });

        highScoresButton.setBackground(Color.BLACK);
        highScoresButton.setForeground(Color.CYAN);
        highScoresButton.setFont(font);
        highScoresButton.setBorder(null);
        highScoresButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                        showHighScores();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                highScoresButton.setForeground(Color.MAGENTA);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                highScoresButton.setForeground(Color.CYAN);

            }
        });

        quitButton.setBackground(Color.BLACK);
        quitButton.setForeground(Color.CYAN);
        quitButton.setFont(font);
        quitButton.setBorder(null);
        quitButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
             System.exit(0);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                quitButton.setForeground(Color.MAGENTA);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                quitButton.setForeground(Color.CYAN);

            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 30, 10);
       gbc.gridx = 0;
       gbc.gridy = 0;

        gbc.gridy++;
        mainPanel.add(newGameButton, gbc);
        gbc.gridy++;
        mainPanel.add(highScoresButton, gbc);
        gbc.gridy++;
        mainPanel.add(quitButton, gbc);

        frame.getContentPane().add(mainPanel);

        frame.pack();

        frame.setVisible(true);
    }

    public void showMainMenu(JPanel panel){
        frame.getContentPane().remove(panel);
        frame.getContentPane().add(mainPanel);
        frame.revalidate();
        frame.repaint();
    }
    private void showMapSizeSelection() {
        frame.getContentPane().remove(mainPanel);
        JPanel mapSizeSelectionPanel = new MapSizeSelectionPanel(frame, this);
        frame.getContentPane().add(mapSizeSelectionPanel);
        frame.revalidate();
        frame.repaint();
    }
    private void showHighScores(){
        frame.getContentPane().remove(mainPanel);
        highScoresPanel = new HighScoresPanel(this);
        frame.getContentPane().add(highScoresPanel);
        frame.revalidate();
        frame.repaint();
    }
}
