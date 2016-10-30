package skeleton;

import org.xml.sax.Attributes;

/**
 * An object of this class is used in conjunction with the OSMParser
 * to convert elements in an osm file to OSMElement objects.
 */
public class OSMElementHandler {

    /**
     * The current OSMElement being handled.
     */
    private OSMElement currentPrimaryElement;
    /**
     * The current Tag being handled.
     */
    private Tag currentTag;
    /**
     * The current RelationMember being handled.
     */
    private RelationMember currentRelationMember;




    /**
     * Determines which primary element has been ecountered, and calls appropriate method.
     *
     * @param qName Name of primary element (node, way, relation).
     */
    public void choosePrimaryElement(String qName) {
        switch (qName) {
            case "node":
                break;
            case "way":
                break;
            case "relation":
                break;
            default:
                break;
        }
    }


    /**
     * Handles a node element.
     * @param atts Attributes of this node.
     */
    public void handleNode(Attributes atts) {
        for (int i = 0; i < atts.getLength(); i++) {
            String qName = atts.getQName(i);
            String type = atts.getType(i);
            String value = atts.getValue(i);
            switch (qName) {
                case "lat":
                    break;
                case "lon":
                    break;
                case "id":
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Handles a wat element.
     * @param atts Attributes of this way.
     */
    public void handleWay(Attributes atts) {
        for (int i = 0; i < atts.getLength(); i++) {
            String qName = atts.getQName(i);
            String type = atts.getType(i);
            String value = atts.getValue(i);
            switch (qName) {
                case "id":

                default:
                    break;
            }
        }
    }

    /**
     * Handles a relation element.
     * @param atts Attributes of this relation.
     */
    public void handleRelation(Attributes atts) {
        for (int i = 0; i < atts.getLength(); i++) {
            String qName = atts.getQName(i);
            String type = atts.getType(i);
            String value = atts.getValue(i);
            switch (qName) {
                case "id":
                default:
                    break;
            }
        }

    }


    /**
     * Handles a tag element.
     * @param atts Attributes of this tag.
     */
    public void handleTag(Attributes atts) {
        for (int i = 0; i < atts.getLength(); i++) {
            String qName = atts.getQName(i);
            String type = atts.getType(i);
            String value = atts.getValue(i);
            switch (qName) {
                // Encountered key.
                case "k":
                    break;
                // Encountered value.
                case "v":

                default:
                    break;
            }
        }
    }

    /**
     * Handles a relation member element.
     * @param atts Attributes of this relation member.
     */
    public void handleRelationMember(Attributes atts) {
        for (int i=0; i < atts.getLength(); i++){
            String qName = atts.getQName(i);
            String type = atts.getType(i);
            String value = atts.getValue(i);
            switch (qName) {
                case "type":
                    break;
                case "ref":
                    break;
                case "role":
                    break;
                default:
                    break;
            }
        }
    }
}
