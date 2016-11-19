package drawing;

import parsing.Map;
import parsing.OSMParser;

import javax.swing.*;
import java.awt.*;
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
    NotificationPanel notificationPanel;

    public MapFrame(Map map){
        this.map = map;
        this.setLayout(new BorderLayout());
        this.mapPanel = new MapPanel(map);
        this.add(mapPanel, BorderLayout.CENTER);
        this.notificationPanel = new NotificationPanel(300, 100);
        this.add(notificationPanel, BorderLayout.SOUTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
    }

    private void addObserver(MapView view){
        views.add(view);
    }

    public static void main(String[] args) throws Exception {
        OSMParser parser = new OSMParser(new File(args[0]));
        parser.parse();
        MapFrame mapFrame = new MapFrame(parser.getMap());
    }

}
