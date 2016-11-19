package drawing;

import javax.swing.*;

/**
 * Panel responsible  for displaying notifications/info to the user.
 */
public class NotificationPanel extends JPanel implements MapView {

    private int width;
    private int height;
    private JLabel text;


    public NotificationPanel(int width, int height){
        this.width = width;
        this.height = height;
        this.setSize(width, height);
        text = new JLabel();
    }


}
