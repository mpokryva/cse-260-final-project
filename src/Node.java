import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import org.omg.CORBA.PRIVATE_MEMBER;

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

    public void setID

}
