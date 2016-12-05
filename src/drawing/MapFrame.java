package drawing;

import com.starkeffect.highway.GPSDevice;
import com.starkeffect.highway.GPSEvent;
import com.starkeffect.highway.GPSListener;
import navigation.DirectionsGenerator;
import navigation.Person;
import navigation.Vertex;
import parsing.Map;
import parsing.Node;
import parsing.OSMParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.LinkedList;

/**
 * Top-level frame holding the map, the notification area, etc.
 */
public class MapFrame extends JFrame implements GPSListener {
    private static final String DRIVE_MODE = "DRIVE_MODE";
    private static final String VIEW_MODE = "VIEW_MODE";
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
     * This MapFrame's mode. Can be DRIVE_THERE, or SELECTING_LOCATION, for ex.
     * Not yet implemented.
     */
    private String mode;

    private GPSDevice gpsDevice;

    private double currentLon;
    private double currentLat;

    /**
     * Initializes this MapFrame based upon the supplied Map object.
     *
     * @param map The Map to represent this MapFrame.
     */

    public MapFrame(Map map) {
        mode = VIEW_MODE;
        this.map = map;
        directionsGenerator = new DirectionsGenerator(map);
        // gpsDevice = new GPSDevice();
        //gpsDevice.addGPSListener(new Person());
        this.setLayout(new BorderLayout());
        this.mapPanel = new MapPanel(map);
        this.notificationPanel = new NotificationPanel(500, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int screenWidth = gd.getDisplayMode().getWidth();
        int screenHeight = gd.getDisplayMode().getHeight();
        int frameWidth = screenWidth - 100; // Makes the frame a bit smaller than the screen size.
        int frameHeight = screenHeight - 100;
        this.setSize(frameWidth, frameHeight);
        this.setLocation(screenWidth / 2 - frameWidth / 2, screenHeight / 2 - frameHeight / 2);
        this.add(mapPanel, BorderLayout.CENTER);
        this.add(notificationPanel, BorderLayout.SOUTH);
        this.setVisible(true);
        mapPanel.recenter();
        addMapPanelClickListener();
        addMenu();
        addNavigationPanel();
    }

    /**
     * Adds a left-click listener to the MapPanel, which sets the text
     * of the notificationPanel to the location of the click in degrees.
     */
    private void addMapPanelClickListener() {
        mapPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                double[] currentMouseCoords = mapPanel.getMouseLocationAsCoords(e);
                //currentMouseCoords[0] = Math.round(currentMouseCoords[0]*100.0)/100.0;
                //currentMouseCoords[1] = Math.round(currentMouseCoords[1]*100.0)/100.0;
                notificationPanel.setText("Coordinates clicked: " + currentMouseCoords[0] + ", " +
                        currentMouseCoords[1]);
                System.out.println("Map clicked");
                repaint();
            }
        });
    }

    private void addNavigationPanel(){
        JPanel navigationPanel = new JPanel();
        GridLayout gridLayout = new GridLayout(0, 1);
        navigationPanel.setLayout(gridLayout);
        JButton driveButton = new JButton("Drive");
        navigationPanel.add(driveButton);
        JButton getDirectionsButton = new JButton("Get directions");
        navigationPanel.add(getDirectionsButton);
        JButton stopButton = new JButton("Stop navigation");
        navigationPanel.add(stopButton);
        addNavigationButtonListeners(driveButton, getDirectionsButton, stopButton);
        this.add(navigationPanel, BorderLayout.WEST);
    }

    private void addNavigationButtonListeners(JButton driveButton, JButton getDirectionsButton, JButton stopButton){
        driveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                setMode(DRIVE_MODE);
            }
        });
        getDirectionsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (mapPanel.areBothLocationSelected()){
                    Node startingNode = mapPanel.getStartingNode();
                    Node endingNode = mapPanel.getEndingNode();
                    calculateAndSendPath(startingNode, endingNode);
                }
                else {
                    notificationPanel.setText("Need to select two locations.");
                }
            }
        });
        stopButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                mode = VIEW_MODE;
            }
        });
    }

    private void calculateAndSendPath(Node startingNode, Node endingNode){
        LinkedList<Vertex> path = directionsGenerator.findShortestPath(startingNode, endingNode);
        if (path != null){
            mapPanel.drawPath(path);
        }
    }

    private void addMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu appearanceMenu = new JMenu("Appearance");
        JMenuItem showHideBorders = new JMenuItem("Show/hide borders");
        JMenuItem clearSelection = new JMenuItem("Clear selection");
        showHideBorders.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mapPanel.setBoundariesShowing(!mapPanel.isBoundariesShowing());
                mapPanel.repaint();
            }
        });
        clearSelection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mapPanel.clearSelection();
            }
        });
        appearanceMenu.add(showHideBorders);
        appearanceMenu.add(clearSelection);
        menuBar.add(appearanceMenu);
        this.setJMenuBar(menuBar);
    }

    public static void main(String[] args) throws Exception {
        OSMParser parser = new OSMParser(new File(args[0]));
        parser.parse();
        MapFrame mapFrame = new MapFrame(parser.getMap());
        System.out.println("Hello");
    }

    @Override
    public void processEvent(GPSEvent e) {
        currentLon = e.getLongitude();
        currentLat = e.getLatitude();
        if (mode.equals(DRIVE_MODE)){
            drawPerson(currentLon, currentLat);
            Node currentNode = map.findNearestNode(currentLon, currentLat);
            boolean onPath = directionsGenerator.areCoordinatesOnPath(currentLon, currentLat);
            if (reachedTarget(currentNode)){
                setMode(VIEW_MODE);
            }
            if (!onPath){
                notificationPanel.setText("Off route. Recalculating...");

                calculateAndSendPath(currentNode, directionsGenerator.getCurrentTarget());
            }
            notificationPanel.setText("Driving on route...");
        }
    }

    private boolean reachedTarget(Node currentNode){
        return (currentNode.equals(directionsGenerator.getCurrentTarget()));
    }

    private void drawPerson(double lon, double lat){
        mapPanel.drawPerson(lon, lat);
    }

    /**
     * Returns the user-selected starting node.
     *
     * @return The user-selected starting node.
     */
    public Node getStartingNode() {
        return mapPanel.getStartingNode();
    }

    /**
     * Returns the user selected ending node.
     *
     * @return The user-selected ending node.
     */
    public Node getEndingNode() {
        return mapPanel.getEndingNode();
    }

    /**
     * Returns the map object associated with this MapFrame.
     *
     * @return The map object associated with this MapFrame.
     */
    public Map getMap() {
        return map;
    }

    /**
     * Returns the MapFrame's GPSDevice
     *
     * @return This MapFrame's GPSDevice
     */
    public void getGPSDevice() {
        // Not really void. Does not return GPSDevice for compilation purposes.
    }

    public void setMode(String newMode) {
        this.mode = newMode;
    }

    public MapPanel getMapPanel() {
        return mapPanel;
    }
}
