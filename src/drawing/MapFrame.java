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
    private Map map;
    private ArrayList<MapView> views;
    /**
     * Displays notifications to user. Hidden until needed.
     */
    private NotificationPanel notificationPanel;

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

    private void addObserver(MapView view){
        views.add(view);


    }

    private void addMapPanelClickListener(){
        mapPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                double[] currentMouseCoords = mapPanel.getMouseLocationAsCoords(e.getX(), e.getY());
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
