package drawing;

import javax.swing.*;

/**
 * Panel responsible  for displaying notifications/info to the user.
 */
public class NotificationPanel extends JPanel{

    private int width;
    private int height;
    private JLabel textArea;


    public NotificationPanel(int width, int height){
        this.width = width;
        this.height = height;
        this.setSize(width, height);
        textArea = new JLabel();
        this.add(textArea);
        setVisible(false);
    }

    protected void setText(String text){
        textArea.setText(text);
        this.setVisible(true);
    }


}
