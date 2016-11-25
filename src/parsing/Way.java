package parsing;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * An object of this class represents a way in an OSM file.
 */
public class Way extends OSMElement {

    /**
     * A list of this ways's node references.
     */
    private List<String> nodeRefs;

    /**
     * The color of this way.
     */
    private Color wayColor;

    /**
     * The thickness of the way (in pixels)
     */
    private float wayThickness;
    /**
     * The thickness of the way (useful for drawing a clearer map). Scale of 1-10. 1 is highest thickness.
     */
    private int wayPriority;


    /**
     * The significance of the way (helps with drawing a cleaner map). Scale of 1-10.
     * Lower numbers correspond to a blank significance.
     */
    /**
     * Initializes a way with the given ID.
     *
     * @param id The ID of this way.
     */
    public Way(String id) {
        super(id);
        nodeRefs = new ArrayList<>();
        setColor(WayColor.DEFAULT.getColor());
        setWayThickness(Thickness.DEFAULT.getThickness());
    }

    /**
     * Adds a node reference ID to the node reference list.
     *
     * @param nodeRefID The node ref ID to add.
     */
    public void addNodeRef(String nodeRefID) {
        nodeRefs.add(nodeRefID);
    }

    /**
     * Returns this way's node references as a list.
     *
     * @return A list of this way's node references.
     */
    public List<String> getNodeRefList() {
        return nodeRefs;
    }

    /**
     * Checks if this way has the specified node.
     *
     * @param node The node to check for.
     * @return True if way contains this node. False otherwise.
     */
    public boolean hasNode(Node node) {
        return nodeRefs.contains(node.getId());
    }

    /**
     * Sets the color of the way to the specified color.
     *
     * @param color The new color of this way.
     */
    public void setColor(Color color) {
        wayColor = color;
    }

    /**
     * Returns this way's color.
     *
     * @return This way's color.
     */
    public Color getColor() {
        return wayColor;
    }

    /**
     * Returns this way's thickness.
     *
     * @return This way's thickness.
     */
    public float getWayThickness() {
        return wayThickness;
    }

    /**
     * Sets the thickness of this way to the specified thickness, in pixels.
     *
     * @param wayThickness The new thickness of this way.
     */
    public void setWayThickness(float wayThickness) {
        this.wayThickness = wayThickness;
    }

    /**
     * Returns the default way color (white).
     *
     * @return The default way color (white).
     */
    public static Color getDefaultColor() {
        return WayColor.DEFAULT.getColor();
    }

    /**
     * Returns the thickness of this way.
     *
     * @return The thickness of this way.
     */
    public int getWayPriority() {
        return wayPriority;
    }

    /**
     * Sets the thickness of this way to the specified thickness.
     *
     * @param wayPriority The new thickness of this way.
     */
    public void setWayPriority(int wayPriority) {
        this.wayPriority = wayPriority;
    }

    /**
     * Returns the default way thickness.
     *
     * @return The default way thickness.
     */
    public static int getDefaultPriority() {
        return Priority.DEFAULT.getPriority();
    }


    /**
     * Created by mpokr on 11/25/2016.
     */
    public enum Priority {

        MOTORWAY(1),
        TRUNK(2),
        PRIMARY(3),
        SECONDARY(4),
        TERTIARY(5),
        UNCLASSIFIED(6),
        RESIDENTIAL(6),
        BUILDING(7),
        DEFAULT(6);

        private int priority;

        Priority(int priority) {
            this.priority = priority;
        }

        public int getPriority() {
            return priority;
        }
    }

    public enum Thickness {

        MOTORWAY(5),
        TRUNK(5),
        PRIMARY(4),
        SECONDARY(3),
        TERTIARY(3),
        UNCLASSIFIED(2),
        RESIDENTIAL(2),
        BUILDING(1),
        DEFAULT(2);

        private float thickness;

        Thickness(int priority) {
            this.thickness = priority;
        }

        public float getThickness() {
            return thickness;
        }
    }

    public enum WayColor {
        WATER(new Color(167, 205, 242)),
        MOTORWAY(new Color(242, 178, 75)),
        TRUNK(new Color(253, 232, 173)),
        PRIMARY(new Color(253, 232, 173)),
        SECONDARY(new Color(254, 254, 254)),
        TERTIARY(new Color(254, 254, 254)),
        UNCLASSIFIED(new Color(254, 254, 254)),
        RESIDENTIAL(new Color(254, 254, 254)),
        BUILDING(new Color(254, 254, 254)),
        DEFAULT(new Color(254, 254, 254));

        Color color;

        WayColor(Color color) {
            this.color = color;
        }

        public Color getColor() {
            return color;
        }

    }


}
