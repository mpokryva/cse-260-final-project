package drawing;

import parsing.Map;
import parsing.OSMParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

/**
 * Top-level frame holding the map, the notification area, etc.
 */
public class MapFrame extends JFrame {
    /**
     * Contains the actual map
     */
    private MapPanel mapPanel;
    /**
     * The map model this MapFrame is based upon.
     */
    private Map map;
    /**
     * Displays notifications to user. Hidden until needed.
     */
    private NotificationPanel notificationPanel;

    /**
     * Initializes this MapFrame based upon the supplied Map object.
     * @param map The Map to represent this MapFrame.
     */
    public MapFrame(Map map){
        this.map = map;
        this.setLayout(new BorderLayout());
        this.mapPanel = new MapPanel(map);
        this.notificationPanel = new NotificationPanel(500, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.add(mapPanel, BorderLayout.CENTER);
        this.add(notificationPanel, BorderLayout.SOUTH);
        this.setVisible(true);
        addMapPanelClickListener();
    }

    /**
     * Adds a left-click listener to the MapPanel, which sets the text
     * of the notificationPanel to the location of the click in degrees.
     */
    private void addMapPanelClickListener(){
        mapPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                double[] currentMouseCoords = mapPanel.getMouseLocationAsCoords(e);
                currentMouseCoords[0] = Math.round(currentMouseCoords[0]*100.0)/100.0;
                currentMouseCoords[1] = Math.round(currentMouseCoords[1]*100.0)/100.0;
                notificationPanel.setText("Coordinates clicked: " + currentMouseCoords[0] + ", " +
                                                currentMouseCoords[1]);
                System.out.println("Map clicked");
                repaint();
            }
        });
    }

    public static void main(String[] args) throws Exception {
        OSMParser parser = new OSMParser(new File(args[0]));
        parser.parse();
        MapFrame mapFrame = new MapFrame(parser.getMap());
        System.out.println("Hello");
    }

}
