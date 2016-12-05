package drawing;


import navigation.Vertex;
import parsing.Map;
import parsing.Node;
import parsing.Way;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * A JPanel that houses a drawn map, which is based off a supplied Map object.
 */
public class MapPanel extends JPanel {
    /**
     * The map that this MapPanel represents.
     */
    private Map map;
    /**
     * The zoom scale, in pixels per degree of this MapPanel.
     */
    private double zoom;
    /**
     * The center longitude, in map coordinates of this map.
     */
    private double centerLon;
    /**
     * The center latitude, in map coordinates of this map.
     */
    private double centerLat;

    /**
     * The amount of "clicks" the mouse wheel has moved from the default zoom.
     */
    private int mouseWheelClicks;
    /**
     * The node marked as the starting location.
     */
    private Node startingNode;
    /**
     * The node marked as the ending location.
     */
    private Node endingNode;
    /**
     * If true, boundaries are drawn.
     * If false, they are hidden.
     */
    private boolean boundariesShowing;
    /**
     * The driving path to draw, if exists.
     */
    private ArrayList<Node> pathToDraw;
    /**
     * Initializes the fields of this MapPanel, and not much else.
     *
     * @param map The Map of this MapPanel.
     */
    public MapPanel(Map map) {
        this.map = map;
        addZoomListener();
        addPanListener();
        centerLon = map.getCenterLon();
        centerLat = map.getCenterLat();
        addRightMouseClickListener();
        addDoubleClickListener();
    }

    public void drawPath(LinkedList<Vertex> path) {
        pathToDraw = new ArrayList<>();
        for (Vertex vertex : path) {
            pathToDraw.add(map.findNodeById(vertex.getId()));
        }
        repaint();
    }

    /**
     * Called once as part of initialization, in order to properly center and zoom map.
     */
    public void recenter() {
        double panelWidth = this.getWidth();
        double panelHeight = this.getHeight();
        double largerDimension;
        if (panelHeight > panelWidth)
            largerDimension = panelHeight;
        else
            largerDimension = panelWidth;
        double lonRange = map.getLonRange();
        double latRange = map.getLatRange();
        double largerRange;
        if (lonRange > latRange)
            largerRange = lonRange;
        else
            largerRange = latRange;
        zoom = largerDimension / largerRange;
    }

    /**
     * Adds a listener for user mouse panning.
     */
    private void addPanListener() {
        double[] initCoords = new double[2];

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                double[] currentCoords = getMouseLocationAsCoords(e);
                initCoords[0] = currentCoords[0];
                initCoords[1] = currentCoords[1];
            }

        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                double[] currentCoords = getMouseLocationAsCoords(e);
                pan((initCoords[0] - currentCoords[0]), (initCoords[1] - currentCoords[1]));
                repaint();
            }
        });
    }

    /**
     * Increments the center coordinates of the map by the specified amounts
     *
     * @param lonChange The amount of longitude to increment by, in degrees.
     * @param latChange The amount of latitude to increment by, in degrees.
     */
    private void pan(double lonChange, double latChange) {
        setCenterCoords(centerLon + lonChange, centerLat + latChange);
    }

    /**
     * Adds a listener for the right-mouse clicks.
     */
    private void addRightMouseClickListener() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (SwingUtilities.isRightMouseButton(e)) {
                    String[] possibilities = {"Mark as starting location", "Mark as ending location",
                            "Drive here from current location"};
                    String s = (String) JOptionPane.showInputDialog(MapPanel.this, null,
                            "Location selection", JOptionPane.PLAIN_MESSAGE,
                            null, possibilities, null);
                    double[] currentCoords = getMouseLocationAsCoords(e);
                    if (s.equals(possibilities[0])) {
                        startingNode = map.findNearestNode(currentCoords[0], currentCoords[1]);
                        repaint();
                    } else if (s.equals(possibilities[1])) {
                        endingNode = map.findNearestNode(currentCoords[0], currentCoords[1]);
                    } else {

                        // Activate "Drive there" mode
                    }
                }
            }

        });

    }

    private void addDoubleClickListener() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) {
                    double[] mapCoords = getMouseLocationAsCoords(e);
                    List<Way> wayList = map.findWaysByLonLat(mapCoords[0], mapCoords[1]);
                    for (Way way : wayList) {
                        System.out.println(way.getName());
                    }
                    System.out.println(map.findNearestNode(mapCoords[0], mapCoords[1]).getId());
                }
            }
        });
    }

    /**
     * Adds a listener for user mouse-wheel scrolling.
     */
    private void addZoomListener() {
        double[] prevMouseCoords = new double[2];
        this.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                double amountRotated = -1 * e.getPreciseWheelRotation();
                // Makes sure user can't zoom infinitely in or out.
                //if ((mouseWheelClicks >= MAXIMUM_ZOOM_OUT || amountRotated > 0) &&
                //     (mouseWheelClicks <= MAXIMUM_ZOOM_IN || amountRotated < 0)) {
                double scaleFactor = 10;    //10 chosen arbitrarily.
                double zoomScaleFactor = zoom / scaleFactor;
                double amountToZoom = amountRotated * zoomScaleFactor;
                zoom += amountToZoom;

                mouseWheelClicks += amountRotated;
                double[] currentCoords = getMouseLocationAsCoords(e);

                if (currentCoords[0] != prevMouseCoords[0] || currentCoords[1] != prevMouseCoords[1]) {
                    if (amountRotated > 0) {
                        setCenterCoords(centerLon + ((currentCoords[0] - centerLon) / scaleFactor), centerLat + ((currentCoords[1] - centerLat) / scaleFactor));
                    } else {
                        setCenterCoords(centerLon - ((currentCoords[0] - centerLon) / scaleFactor), centerLat - ((currentCoords[1] - centerLat) / scaleFactor));
                    }
                    prevMouseCoords[0] = currentCoords[0];
                    prevMouseCoords[1] = currentCoords[1];
                }
                repaint();
            }

            //  }
        });
    }


    /**
     * Sets the center coordinates of this map.
     *
     * @param coordLon The new center longitude of this map.
     * @param coordLat The new cetner latitude of this map.
     */
    private void setCenterCoords(double coordLon, double coordLat) {
        centerLon = coordLon;
        centerLat = coordLat;
    }


    /**
     * Queries the map field for a list of ways, and then draws all the nodes
     * in the ways as dots, connecting them with lines.
     * Also draws any markers set by the user.
     *
     * @param g The Graphics object the MapPanel uses for drawing.
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);
        this.setBackground(new Color(234, 234, 234));
        List<Way> wayList = map.getWayList();
        for (Way way : wayList) {
            int wayPriority = way.getWayPriority();
            // Checks that map is zoomed in enough.
            if (wayPriority * 1000 < zoom) {
                // Checks if way is boundary, and if so, if it should be drawn.
                if (!way.isBoundary() || boundariesShowing) {
                    g.setColor(way.getColor());
                    List<Node> nodesInWay = map.findNodesInWay(way);
                    Node firstNode = nodesInWay.get(0);
                    double firstLat = convertLatToPixels(firstNode.getLat());
                    double firstLon = convertLonToPixels(firstNode.getLon(), firstNode.getLat());
                    Path2D.Double wayLine = new Path2D.Double();
                    wayLine.moveTo(firstLon, firstLat);
                    for (int i = 1; i < nodesInWay.size(); i++) {
                        double lat = nodesInWay.get(i).getLat();
                        double lon = nodesInWay.get(i).getLon();
                        double pixelLat = convertLatToPixels(lat);
                        double pixelLon = convertLonToPixels(lon, lat);
                        wayLine.lineTo(pixelLon, pixelLat);
                    }
                    g2.setColor(way.getColor());
                    g2.setStroke(new BasicStroke(way.getWayThickness() + mouseWheelClicks / 10));
                /*
                If the way is a water feature, we fill it.
                 */
                    if (way.isWater()) {
                        // wayLine.closePath();
                        g2.fill(wayLine);
                    }
                    g2.draw(wayLine);
                }
            }
        }

        if (startingNode != null) {
            // User just selected location
            drawLocationPin(g2, startingNode, Color.GREEN);
        }
        if (endingNode != null) {
            drawLocationPin(g2, endingNode, Color.RED);
        }
        if (pathToDraw != null) {
            g2.setColor(Color.RED);
            Node firstNode = pathToDraw.get(0);
            double firstLat = convertLatToPixels(firstNode.getLat());
            double firstLon = convertLonToPixels(firstNode.getLon(), firstNode.getLat());
            Path2D.Double wayLine = new Path2D.Double();
            wayLine.moveTo(firstLon, firstLat);
            for (int i = 1; i < pathToDraw.size(); i++) {
                double lat = pathToDraw.get(i).getLat();
                double lon = pathToDraw.get(i).getLon();
                double pixelLat = convertLatToPixels(lat);
                double pixelLon = convertLonToPixels(lon, lat);
                wayLine.lineTo(pixelLon, pixelLat);
            }
            g2.draw(wayLine);
        }

    }

    /**
     * Draws a location pin (upside-down triangle), who's
     * tip begins at the specified node.
     *
     * @param g2             The Graphics object the MapPanel uses for drawing.
     * @param markerLocation The Vertex specifying the pin's location.
     * @param color          The color of the pin.
     */
    private void drawLocationPin(Graphics2D g2, Node markerLocation, Color color) {
        double lat = markerLocation.getLat();
        double lon = markerLocation.getLon();
        double pixelLat = convertLatToPixels(lat);
        double pixelLon = convertLonToPixels(lon, lat);
        setLayout(null);
        /**
         JLabel startPin = new JLabel(new ImageIcon(STARTING_PIN_ICON));
         startPin.setBounds((int)pixelLon, (int)pixelLat, startPin.getPreferredSize().width, startPin.getPreferredSize().height);
         if (startingPin == null){
         add(startPin);
         startingPin = startPin;
         revalidate();
         repaint();
         }
         // Pin already on the map.
         else {
         remove(startingPin);
         add(startPin);
         startingPin = startPin;
         revalidate();
         repaint();
         }
         **/
        double triHeight = 20;
        double triWidth = 15;
        double[] triangleX = new double[]{pixelLon, pixelLon - (triWidth / 2), pixelLon + (triWidth / 2)};
        double[] triangleY = new double[]{pixelLat, pixelLat - triHeight, pixelLat - triHeight};
        Path2D.Double triangle = new Path2D.Double();
        triangle.moveTo(triangleX[0], triangleY[0]);
        for (int i = 1; i < triangleX.length; i++) {
            triangle.lineTo(triangleX[i], triangleY[i]);
        }
        triangle.closePath();
        g2.setColor(color);
        g2.fill(triangle);
        g2.draw(triangle);
        g2.setColor(Color.BLACK);
    }

    /**
     * A convenience method that given a MouseEvent, returns an array
     * containing the coordinates of the MouseEvent in degrees.
     *
     * @param e The MouseEvent
     * @return An array of size 2 containing the longitude in the first index, and the
     * latitude in the second.
     */
    protected double[] getMouseLocationAsCoords(MouseEvent e) {
        double[] lonLatArray = new double[2];
        lonLatArray[0] = convertPixelToLon(e.getX(), e.getY());
        lonLatArray[1] = convertPixelToLat(e.getY());
        return lonLatArray;
    }

    /**
     * Converts latitude in map coordinates to pixels.
     *
     * @param coordLat The latitude (in map coordinates) to convert.
     * @return Converted latitude (in pixels).
     */
    private double convertLatToPixels(double coordLat) {
        double pixelLat = (centerLat - coordLat) * (zoom) + (this.getHeight() / 2);
        return pixelLat;
    }

    /**
     * Converts longitude in map coordinates to pixels.
     *
     * @param coordLat The latitude (in map coordinates). Necessary for conversion calculations.
     * @param coordLon The longitude (in map coordinates) to convert.
     * @return Converted longitude (in pixels).
     */
    private double convertLonToPixels(double coordLon, double coordLat) {
        double lonScaleFactor = (zoom) * Math.cos(Math.toRadians(coordLat));
        double pixelLon = (coordLon - centerLon) * lonScaleFactor + (this.getWidth() / 2);
        return pixelLon;
    }

    /**
     * Converts latitude in pixels to map coordinates.
     *
     * @param pixelLat The latitude (in pixels) to convert.
     * @return Converted latitude (in map coordinates).
     */
    private double convertPixelToLat(double pixelLat) {
        double coordLat = pixelLat - (this.getHeight() / 2);
        coordLat = coordLat / zoom;
        coordLat -= centerLat;
        coordLat *= -1;
        return coordLat;
    }

    /**
     * Converts longitude in pixels to map coordinates.
     *
     * @param pixelLat The latitude (in pixels). Necessary for conversion calculations.
     * @param pixelLon The longitude (in pixels) to convert.
     * @return Converted longitude (in map coordinates).
     */
    private double convertPixelToLon(double pixelLon, double pixelLat) {
        double coordLat = convertPixelToLat(pixelLat);
        double coordLon = pixelLon - (this.getWidth() / 2);
        coordLon = coordLon / ((zoom) * Math.cos(Math.toRadians(coordLat)));
        coordLon += centerLon;
        return coordLon;

    }

    /**
     * Returns this MapPanel's current zoom scale.
     *
     * @return The current zoom scale.
     */
    public double getZoom() {
        return zoom;
    }


    /**
     * Sets the zoom scale.
     *
     * @param newZoom The new zoom.
     */
    public void setZoom(double newZoom) {
        zoom = newZoom;
    }

    /**
     * Returns the starting node.
     *
     * @return The starting node.
     */
    public Node getStartingNode() {
        return startingNode;
    }

    /**
     * Returns the ending node.
     *
     * @return The ending node.
     */
    public Node getEndingNode() {
        return endingNode;
    }

    /**
     * If set to true, map boundaries will be shown.
     * If false, they wil be hidden.
     *
     * @param boundariesShowing Value specifying if boundaries should be shown.
     */
    public void setBoundariesShowing(boolean boundariesShowing) {
        this.boundariesShowing = boundariesShowing;
    }

    /**
     * Returns a vlaue specifying if boundaries are shown.
     *
     * @return True, if boundaries are shown. False otherwise.
     */
    public boolean isBoundariesShowing() {
        return boundariesShowing;
    }

    public void setPath(LinkedList<Vertex> path){
        drawPath(path);
    }

    public boolean areBothLocationSelected(){
        return (startingNode != null && endingNode != null);
    }

    public boolean isStartingLocationSelected(){
        return (startingNode != null);
    }

    public boolean isEndingLocationSelected(){
        return (endingNode != null);
    }
}
