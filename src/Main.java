
import javax.swing.*;
import Menu.MenuPacman;
public class Main {
   private static JFrame frame;
        public static void main (String[]args){
            SwingUtilities.invokeLater(() -> {
                frame=new MenuPacman();

            });
        }

    }
