package Game;

import character.ghost.util.GhostHandler;
import Menu.MapSizeSelectionPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameFrame extends JFrame {

   private static JFrame frame;
    private Dimension dimension;

    private JPanel mainPanel;

    private GamePanel gamePanel;
    final int BASE_SCREEN_WIDTH = 1024;
    final int BASE_SCREEN_HEIGHT = 768;
    final int BASE_CELL_SIZE = 25;
    final int BASE_CHARACTER_SIZE = 20;
    final int BASE_LEFT_PANEL_WIDTH=155;
    final int BASE_LEFT_PANEL_HEIGHT=250;
    final int BASE_RIGHT_PANEL_WIDTH=200;
    final int BASE_RIGHT_PANEL_HEIGHT=310;
    final int BASE_STATUS_PANEL_HEIGHT=45;
    final int BASE_STATUS_PANEL_WIDTH=400;

    private GhostHandler ghostHandler;
    public GameFrame(JFrame menu, int width, int height){
        frame =new JFrame("Pac-Man by LoLo");
        mainPanel=new JPanel();

        mainPanel.setLayout(new BorderLayout());

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;



        double widthScale = screenWidth / (double) BASE_SCREEN_WIDTH;
        double heightScale = screenHeight / (double) BASE_SCREEN_HEIGHT;
        double scale = Math.min(widthScale, heightScale);
        if (screenWidth==1920 && screenHeight==1080){
            widthScale -= 0.05;
            heightScale -= 0.05;
            if (width==1200&&height==1200){
                widthScale += 0.05;
                heightScale += 0.05;
            }
            if ((width==1400&&height==1200)){
                widthScale += 0.8;
                heightScale += 0.8;
            }
        }

        int scaledCellSize = (int) (BASE_CELL_SIZE * scale);
        int scaledCharacterSize = (int) (BASE_CHARACTER_SIZE * scale);
        int scaledLeftPanelWidth=(int) (BASE_LEFT_PANEL_WIDTH * scale);
        int scaledLeftPanelHeight=(int) (BASE_LEFT_PANEL_HEIGHT * scale);
        int scaledRightPanelWidth=(int) (BASE_RIGHT_PANEL_WIDTH* scale);
        int scaledRightPanelHeight=(int) (BASE_RIGHT_PANEL_HEIGHT * scale);
        int scaledStatusPanelWidth=(int) (BASE_STATUS_PANEL_WIDTH* scale);
        int scaledStatusPanelHeight=(int) (BASE_STATUS_PANEL_HEIGHT * scale);



        RescaleUnit rescaleUnit=RescaleUnit.getInstance(widthScale);
        StatusPanel statusPanel = new StatusPanel(scaledStatusPanelWidth,scaledStatusPanelHeight,widthScale,heightScale);
        RightPanel2 rightPanel = new RightPanel2(widthScale,heightScale,scaledRightPanelWidth,scaledRightPanelHeight,20,0,0,0);
        LeftPanel2 leftPanel = new LeftPanel2(widthScale,heightScale,scaledLeftPanelWidth,scaledLeftPanelHeight,20,0,0,0);
        gamePanel = new GamePanel(GameFrame::endGame,statusPanel,rightPanel,leftPanel,width,height,scaledCharacterSize,scaledCellSize,widthScale,heightScale);

        gamePanel.requestFocusInWindow();
        ghostHandler=gamePanel.getGhostHandler();

        mainPanel.add(statusPanel,BorderLayout.NORTH);
        mainPanel.add(gamePanel,BorderLayout.CENTER);

        frame.setBackground(Color.BLACK);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().add(mainPanel,BorderLayout.CENTER);
        frame.getContentPane().add(rightPanel,BorderLayout.EAST);
       frame.getContentPane().add(leftPanel,BorderLayout.WEST);


       int x = (screenWidth - frame.getWidth()) /6;
       int y = (screenHeight - frame.getHeight()) /9;

        frame.setLocation(x,y);
        frame.setResizable(true);
        frame.setVisible(true);
        frame.pack();


   frame.addWindowListener(new WindowListener() {
       @Override
       public void windowOpened(WindowEvent e) {

       }

       @Override
       public void windowClosing(WindowEvent e) {
        gamePanel.setCancelGame(true);
       }

       @Override
       public void windowClosed(WindowEvent e) {

       }

       @Override
       public void windowIconified(WindowEvent e) {
      //  ghostHandler.pause();
       }

       @Override
       public void windowDeiconified(WindowEvent e) {
         //  ghostHandler.wakeUpNotified();
       }

       @Override
       public void windowActivated(WindowEvent e) {

       }

       @Override
       public void windowDeactivated(WindowEvent e) {
      //    ghostHandler.pause();
       }
   });
        gamePanel.setInitialized(true);


    }

    public static void endGame() {
        JOptionPane.showMessageDialog(frame, "Game Over!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        MapSizeSelectionPanel.gameOn=0;
       // frame.dispose();
    }

    public static void gameWon(int points, long time){
       SwingUtilities.invokeLater(()->{
           JFrame endGameFrame = new JFrame("Score");
           EndGamePanel endGamePanel = new EndGamePanel(points,time, endGameFrame);
           endGameFrame.setContentPane(endGamePanel);
           endGameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           endGameFrame.pack();
           endGameFrame.setLocationRelativeTo(null);
           endGameFrame.setVisible(true);


       });
    }


}
