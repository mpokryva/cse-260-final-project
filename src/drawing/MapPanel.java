package drawing;

import javafx.scene.shape.Circle;
import parsing.Map;
import parsing.Node;
import parsing.OSMParser;
import parsing.Way;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.Point;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mpokr on 10/13/2016.
 */
public class MapPanel extends JPanel {
    private Map map;
    private double zoom; // Unit is pixels per degree
    private final double RIGHT_SHIFT = 0.2; // Shift map to left to avoid showing long tail.
    private double centerLon;
    private double centerLat;
    private static BufferedImage STARTING_PIN_ICON;
    private static BufferedImage ENDING_PIN_ICON;
    private JLabel startingPin;
    private JLabel endingPin;
    /**
     * List of selected points (in pixel coordinates)
     */

    private Node startingNode;
    private Node endingNode;

    public MapPanel(Map map) {
        this.map = map;
        addZoomListener();
        addPanListener();
        double defaultZoom = 6000;
        zoom = defaultZoom;
        centerLon = map.getCenterLon() + RIGHT_SHIFT;
        centerLat = map.getCenterLat();
        addLocationSelectionListener();
        try {
            STARTING_PIN_ICON = ImageIO.read(new File("GreenPinIcon.png"));
            ENDING_PIN_ICON = ImageIO.read(new File("RedPinIcon.png"));

        } catch (IOException e) {
            System.out.print("No such file exists");
        }
    }

    private void setStartingPin(Node node){

    }


    private void addPanListener() {
        double[] initCoords = new double[2];

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                double[] currentCoords = getMouseLocationAsCoords(e.getX(), e.getY());
                initCoords[0] = currentCoords[0];
                initCoords[1] = currentCoords[1];
            }

        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                double[] currentCoords = getMouseLocationAsCoords(e.getX(), e.getY());
                pan((initCoords[0] - currentCoords[0]), (initCoords[1] - currentCoords[1]));
                repaint();
            }
        });
    }

    private void pan(double lonChange, double latChange) {
        setCenterCoords(centerLon + lonChange, centerLat + latChange);
    }

    private void addLocationSelectionListener() {
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
                    double[] currentCoords = getMouseLocationAsCoords(e.getX(), e.getY());
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


    private void addZoomListener() {
        double[] prevMouseCoords = new double[2];
        this.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                double amountRotated = -1 * e.getPreciseWheelRotation();
                double scaleFactor = 10;
                double zoomScaleFactor = zoom / scaleFactor; //10 chosen randomly.
                double amountToZoom = amountRotated * zoomScaleFactor;
                zoom += amountToZoom;

                double[] currentCoords = getMouseLocationAsCoords(e.getX(), e.getY());


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
        });
    }

    private void setCenterCoords(double coordLon, double coordLat) {
        centerLon = coordLon;
        centerLat = coordLat;
    }


    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);
        List<Way> wayList = map.getWayList();
        for (Way way : wayList) {
            List<Node> nodesInWay = map.findNodesInWay(way);
            double[] previousCoords = new double[2];
            for (Node node : nodesInWay) {
                double lat = node.getLat();
                double lon = node.getLon();
                double pixelLat = convertLatToPixels(lat);
                double pixelLon = convertLonToPixels(lon, lat);
                Line2D.Double dot = new Line2D.Double(pixelLon, pixelLat, pixelLon, pixelLat);
                Line2D.Double prevLine = new Line2D.Double(previousCoords[0], previousCoords[1], pixelLon, pixelLat);
                if (this.getBounds().contains(dot.getX1(), dot.getY1())) {
                    if (previousCoords[0] != 0) {
                        g2.draw(prevLine);
                    }
                }
                previousCoords[0] = pixelLon;
                previousCoords[1] = pixelLat;
                g2.draw(dot);
            }
        }
        if (startingNode != null) {
            // User just selected location
            double lat = startingNode.getLat();
            double lon = startingNode.getLon();
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
            Ellipse2D.Double marker = new Ellipse2D.Double(pixelLon, pixelLat, 5, 5);
            g2.setColor(Color.GREEN);
            g2.fill(marker);
            g2.draw(marker);
            g2.setColor(Color.BLACK);
        }
        if (endingNode != null) {
            double lat = endingNode.getLat();
            double lon = endingNode.getLon();
            double pixelLat = convertLatToPixels(lat);
            double pixelLon = convertLonToPixels(lon, lat);
            Ellipse2D.Double marker = new Ellipse2D.Double(pixelLon, pixelLat, 5, 5);
            g2.setColor(Color.RED);
            g2.fill(marker);
            g2.draw(marker);
            g2.setColor(Color.BLACK);
        }
    }

    protected double[] getMouseLocationAsCoords(double x, double y) {
        double[] lonLatArray = new double[2];
        lonLatArray[0] = convertPixelToLon(x, y);
        lonLatArray[1] = convertPixelToLat(y);
        return lonLatArray;
    }

    private double convertLatToPixels(double coordLat) {
        double pixelLat = (centerLat - coordLat) * (zoom) + (this.getHeight() / 2);
        return pixelLat;
    }

    private double convertLonToPixels(double coordLon, double coordLat) {
        double lonScaleFactor = (zoom) * Math.cos(Math.toRadians(coordLat));
        double pixelLon = (coordLon - centerLon) * lonScaleFactor + (this.getWidth() / 2);
        return pixelLon;
    }

    private double convertPixelToLat(double pixelLat) {
        double coordLat = pixelLat - (this.getHeight() / 2);
        coordLat = coordLat / zoom;
        coordLat -= centerLat;
        coordLat *= -1;
        return coordLat;
    }

    private double convertPixelToLon(double pixelLon, double pixelLat) {
        double coordLat = convertPixelToLat(pixelLat);
        double coordLon = pixelLon - (this.getWidth() / 2);
        coordLon = coordLon / ((zoom) * Math.cos(Math.toRadians(coordLat)));
        coordLon += centerLon;
        return coordLon;

    }

    public double getZoom() {
        return zoom;
    }

    public void setZoom(double newZoom) {
        zoom = newZoom;
    }


}
