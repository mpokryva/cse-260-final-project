package handin1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.io.File;
import java.util.List;

/**
 * Created by mpokr on 10/13/2016.
 */
public class MapDisplay extends JPanel {
    private Map map;
    private double zoom; // Unit is pixels per degree
    private final double RIGHT_SHIFT = 0.2; // Shift map to left to avoid showing long tail.
    private double centerLon;
    private double centerLat;


    public MapDisplay(Map map) {
        this.map = map;
        addZoomListener();
        addPanListener();
        double defaultZoom = 6000;
        zoom = defaultZoom;
        centerLon = map.getCenterLon() + RIGHT_SHIFT;
        centerLat = map.getCenterLat();
    }


    private void addPanListener() {
        double[] initCoords = new double[2];

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                double pixelLon = e.getX();
                double pixelLat = e.getY();
                double coordLon = convertPixelToLon(pixelLon, pixelLat);
                double coordLat = convertPixelToLat(pixelLat);
                initCoords[0] = coordLon;
                initCoords[1] = coordLat;
            }

        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                double pixelLon = e.getX();
                double pixelLat = e.getY();
                double coordLon = convertPixelToLon(pixelLon, pixelLat);
                double coordLat = convertPixelToLat(pixelLat);
                pan((initCoords[0] - coordLon), (initCoords[1] - coordLat));
                repaint();
            }
        });
    }

    private void pan(double lonChange, double latChange){
        setCenterCoords(centerLon + lonChange, centerLat + latChange);
    }


    private void addZoomListener() {
        double[] prevMouseCoords = new double[2];
        this.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                double amountRotated = -1 * e.getPreciseWheelRotation();
                double scaleFactor = 10;
                double zoomScaleFactor = zoom/scaleFactor; //10 chosen randomly.
                double amountToZoom = amountRotated * zoomScaleFactor;
                zoom += amountToZoom;

                double pixelLon = e.getX();
                double pixelLat = e.getY();
                double mouseCoordLon = convertPixelToLon(pixelLon, pixelLat);
                double mouseCoordLat = convertPixelToLat(pixelLat);

                if (mouseCoordLon != prevMouseCoords[0] || mouseCoordLat != prevMouseCoords[1]){
                    if (amountRotated > 0){
                        setCenterCoords(centerLon + ((mouseCoordLon-centerLon)/scaleFactor), centerLat + ((mouseCoordLat-centerLat)/scaleFactor));
                    }
                    else {
                        setCenterCoords(centerLon - ((mouseCoordLon-centerLon)/scaleFactor), centerLat - ((mouseCoordLat-centerLat)/scaleFactor));
                    }
                    prevMouseCoords[0] = mouseCoordLon;
                    prevMouseCoords[1] = mouseCoordLat;
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
                Shape prevLine = new Line2D.Double(previousCoords[0], previousCoords[1], pixelLon, pixelLat);
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

    public double getZoom(){
        return zoom;
    }

    public void setZoom(double newZoom){
        zoom = newZoom;
    }


    public static void main(String[] args) throws Exception {
        OSMParser parser = new OSMParser(new File(args[0]));
        parser.parse();

        JFrame mainFrame = new JFrame("handin1.Map display");
        mainFrame.setLayout(new BorderLayout());

        MapDisplay mapDisplay = new MapDisplay(parser.getMap());
        mainFrame.add(mapDisplay);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setVisible(true);
    }
}
