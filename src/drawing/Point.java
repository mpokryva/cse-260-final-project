package drawing;

/**
 * Represents a point with x and y coordinates.
 */
public class Point {

    private double lon;
    private double lat;

    public Point(double lon, double lat){
        this.lon = lon;
        this.lat = lat;
    }

    public Point(){

    }

    public double getLon() {
        return lon;
    }

    public double getLat() {
        return lat;
    }


}
