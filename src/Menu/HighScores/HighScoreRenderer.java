package Menu.HighScores;

import javax.swing.*;
import java.awt.*;

public class HighScoreRenderer extends JLabel implements ListCellRenderer<HighScoreEntry>{
    private ImageIcon crownIcon;
    String rodzajPisma = Font.SERIF;
    int rozmiarPisma = 34;
    int typPisma = Font.BOLD | Font.ITALIC;

    Font font = new Font(rodzajPisma,typPisma,rozmiarPisma);

    public HighScoreRenderer(){
        crownIcon = new ImageIcon("./img/crown.png");
        setOpaque(true);
        String rodzajPisma = Font.SERIF;
        int rozmiarPisma = 34;
        int typPisma = Font.BOLD | Font.ITALIC;

        Font font = new Font(rodzajPisma,typPisma,rozmiarPisma);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends HighScoreEntry> list, HighScoreEntry value, int index, boolean isSelected, boolean cellHasFocus) {
        String text = (index + 1) + ". " + value.toString();
       setText(text);
       setFont(font);
       if (index==0){
           setIcon(crownIcon);
       }else{
           setIcon(null);
       }
       if (isSelected){
           setBackground(list.getSelectionBackground());
           setForeground(list.getSelectionForeground());
       }else{
           setBackground(list.getBackground());
           setForeground(list.getForeground());
       }
       return this;
    }
}
