package navigation;

import parsing.Map;
import parsing.Way;

/**
 * Class that represents a person on the map. Implements the GPSListener interface
 * Updates his/her current coordinates from the GPSListener method processEvent(GPSEvent e).
 */
public class Person // implements GPSListener
 {
    /**
     * This person's current longitude
     */
    private double currentLon;
    /**
     * This person's current latitude.
     */
    private double currentLat;
     /**
      * The map this person is on.
      */
     private Map map;
     /**
      * The current way the person is on
      */
     private Way currentWay;
     /**
      * Default constructor, for now.
      */
    public Person(){

    }

    /**
    public void processEvent(GPSEvent e){

    }
     **/

    public double getCurrentLon() {
        return currentLon;
    }

    public double getCurrentLat() {
        return currentLat;
    }
}
