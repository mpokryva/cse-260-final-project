package parsing;

public class Node extends OSMElement {

    private double lat;
    private double lon;


    public double getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = Double.parseDouble(lat);
    }

    public double getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = Double.parseDouble(lon);
    }

    public Node(String id) {
        super(id);
    }

    public Node() {

    }



}
