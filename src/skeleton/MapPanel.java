package skeleton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.io.File;
import java.util.List;

/**
 * Created by mpokr on 10/13/2016.
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
     * Initializes the fields of this MapPanel, and not much else.
     * @param map The Map of this MapPanel.
     */
    public MapPanel(Map map) {

    }


    /**
     * Adds a listener for user mouse panning.
     */
    private void addPanListener() {

    }


    /**
     * Adds a listener for user mouse-wheel scrolling.
     */
    private void addZoomListener() {

    }

    /**
     * Sets the center coordinates of this map.
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
     * @param g The Graphics object the MapPanel uses for drawing.
     */
    @Override
    protected void paintComponent(Graphics g) {

    }

    /**
     * Converts latitude in map coordinates to pixels.
     * @param coordLat The latitude (in map coordinates) to convert.
     * @return Converted latitude (in pixels).
     */
    private double convertLatToPixels(double coordLat) {

    }

    /**
     * Converts longitude in map coordinates to pixels.
     * @param coordLat The latitude (in map coordinates). Necessary for conversion calculations.
     * @param  coordLon The longitude (in map coordinates) to convert.
     * @return Converted longitude (in pixels).
     */
    private double convertLonToPixels(double coordLon, double coordLat) {

    }

    /**
     * Converts latitude in pixels to map coordinates.
     * @param pixelLat The latitude (in pixels) to convert.
     * @return Converted latitude (in map coordinates).
     */
    private double convertPixelToLat(double pixelLat) {

    }

    /**
     * Converts longitude in pixels to map coordinates.
     * @param pixelLat The latitude (in pixels). Necessary for conversion calculations.
     * @param pixelLon The longitude (in pixels) to convert.
     * @return Converted longitude (in map coordinates).
     */
    private double convertPixelToLon(double pixelLon, double pixelLat) {


    }


    /**
     * Sets the zoom scale.
     * @param newZoom The new zoom.
     */
    public void setZoom(double newZoom){
        zoom = newZoom;
    }


}
