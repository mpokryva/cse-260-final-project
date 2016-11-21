package drawing;

import navigation.DirectionsGenerator;
import navigation.Person;
import parsing.Map;
import parsing.Node;
import parsing.OSMParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

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
     * Panel that displays notifications to user. Hidden until needed.
     */
    private NotificationPanel notificationPanel;

    /**
     * Calculates directions from one point to another.
     */
    private DirectionsGenerator directionsGenerator;
    /**
     * A person located somewhere on this map, whose location changes.
     */
    private Person person;
    /**
     * Initializes this MapFrame based upon the supplied Map object.
     * @param map The Map to represent this MapFrame.
     */
    public MapFrame(Map map){
        this.map = map;
        directionsGenerator = new DirectionsGenerator(map);
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

    /**
     * Returns the user-selected starting node.
     * @return The user-selected starting node.
     */
    public Node getStartingNode(){
        return mapPanel.getStartingNode();
    }

    /**
     * Returns the user selected ending node.
     * @return The user-selected ending node.
     */
    public Node getEndingNode(){
        return mapPanel.getEndingNode();
    }

    /**
     * Returns the map object associated with this MapFrame.
     * @return The map object associated with this MapFrame.
     */
    public Map getMap(){
        return map;
    }

    /**
     * Returns the MapFrame's GPSDevice
     * @return This MapFrame's GPSDevice
     */
    public void getGPSDevice(){
        // Not really void. Does not return GPSDevice for compilation purposes.
    }

}
