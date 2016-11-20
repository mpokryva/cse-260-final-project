package parsing;

/**
 * An object of this class represents a node in an OSM file.
 */
public class Node extends OSMElement {

    /**
     * The latitude of this node.
     */
    private double lat;
    /**
     * The longitutde of this node.
     */
    private double lon;

    /**
     * Default constructor.
     */
    public Node() {

    }

    /**
     * Returns the latitude of this node.
     *
     * @return The latitude of this node.
     */
    public double getLat() {
        return lat;
    }

    /**
     * Sets the latitude of this node to the given latitude.
     *
     * @param lat The new latitude.
     */
    public void setLat(String lat) {
        this.lat = Double.parseDouble(lat);
    }

    /**
     * Returns the longitude of this node.
     *
     * @return The longitude of this node.
     */
    public double getLon() {
        return lon;
    }

    /**
     * Sets the longitude of this node to the given longitude.
     *
     * @param lon The new longitude.
     */
    public void setLon(String lon) {
        this.lon = Double.parseDouble(lon);
    }

}
