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
     * The default way color (white).
     */
    private static final Color DEFAULT_COLOR = new Color(254, 254, 254);

    /**
     * The thickness of the way (in pixels)
     */
    private float wayThickness;

    /**
     * The default way thickness (pixels)
     */
    private static final float DEFAULT_THICKNESS=2; // FOR NOW

    /**
     * Initializes a way with the given ID.
     *
     * @param id The ID of this way.
     */
    public Way(String id) {
        super(id);
        nodeRefs = new ArrayList<>();
        setColor(DEFAULT_COLOR);
        setWayThickness(DEFAULT_THICKNESS);
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
     * @param node The node to check for.
     * @return True if way contains this node. False otherwise.
     */
    public boolean hasNode(Node node){
        return nodeRefs.contains(node.getId());
    }

    /**
     * Sets the color of the way to the specified color.
     * @param color The new color of this way.
     */
    public void setColor(Color color){
        wayColor = color;
    }

    /**
     * Returns this way's color.
     * @return This way's color.
     */
    public Color getColor(){
        return wayColor;
    }

    /**
     * Returns this way's thickness.
     * @return This way's thickness.
     */
    public float getWayThickness() {
        return wayThickness;
    }

    /** Sets the thickness of this way to the specified thickness, in pixels.
     * @param wayThickness The new thickness of this way.
     */
    public void setWayThickness(float wayThickness) {
        this.wayThickness = wayThickness;
    }

    /**
     * Returns the default way color (white).
     * @return The default way color (white).
     */
    public static Color getDefaultColor() {
        return DEFAULT_COLOR;
    }
}
