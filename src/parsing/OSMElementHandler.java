package parsing;

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
     * Default constructor.
     */
    public OSMElementHandler() {

    }

    /**
     * Determines which primary element has been ecountered, and calls appropriate method.
     *
     * @param qName Name of primary element (node, way, relation).
     */
    public void choosePrimaryElement(String qName) {
        switch (qName) {
            case "node":
                currentPrimaryElement = new Node(null);
                break;
            case "way":
                currentPrimaryElement = new Way(null);
                break;
            case "relation":
                currentPrimaryElement = new Relation(null);
                break;
            default:
                break;
        }
    }


    /**
     * Handles a node element.
     *
     * @param atts Attributes of this node.
     */
    public void handleNode(Attributes atts) {
        for (int i = 0; i < atts.getLength(); i++) {
            String qName = atts.getQName(i);
            String type = atts.getType(i);
            String value = atts.getValue(i);
            // Just put attributes in list, with a few exceptions.
            switch (qName) {
                case "lat":
                    ((Node) currentPrimaryElement).setLat(value);
                    break;
                case "lon":
                    ((Node) currentPrimaryElement).setLon(value);
                    break;
                case "id":
                    // Assumes that ID comes first. Maybe needs revision later on.
                    currentPrimaryElement = new Node(value);
                    // Put attribute in list
                default:
                    currentPrimaryElement.addAttribute(qName, value);
                    break;
            }
        }
    }

    /**
     * Handles a way element.
     *
     * @param atts Attributes of this way.
     */
    public void handleWay(Attributes atts) {
        for (int i = 0; i < atts.getLength(); i++) {
            String qName = atts.getQName(i);
            String type = atts.getType(i);
            String value = atts.getValue(i);
            // Just put attributes in list, with a few exceptions.
            switch (qName) {
                case "id":
                    currentPrimaryElement = new Way(value);
                    // Put attribute in list
                default:
                    currentPrimaryElement.addAttribute(qName, value);
                    break;
            }
        }
    }

    /**
     * Handles a relation element.
     *
     * @param atts Attributes of this relation.
     */
    public void handleRelation(Attributes atts) {
        for (int i = 0; i < atts.getLength(); i++) {
            String qName = atts.getQName(i);
            String type = atts.getType(i);
            String value = atts.getValue(i);
            // Just put attributes in list, with a few exceptions.
            switch (qName) {
                case "id":
                    currentPrimaryElement = new Relation(value);
                    // Put attribute in list
                default:
                    currentPrimaryElement.addAttribute(qName, value);
                    break;
            }
        }

    }


    /**
     * Handles a tag element.
     *
     * @param atts Attributes of this tag.
     */
    public void handleTag(Attributes atts) {
        String tempValue = null;
        boolean hasName = false;
        for (int i = 0; i < atts.getLength(); i++) {
            String qName = atts.getQName(i);
            String type = atts.getType(i);
            String value = atts.getValue(i);
            // Value of tag. Used to take care of case where "v" precedes "k".
            switch (qName) {
                // Encountered key.
                case "k":
                    if (value.equals("name")) {
                        hasName = true;
                    }
                    currentTag = new Tag(value, tempValue);
                    // Put attribute in list
                    break;
                // Encountered value.
                case "v":
                    if (hasName) {
                        currentPrimaryElement.setName(value);
                    }
                    currentTag = new Tag(currentTag.getKey(), value);
                    tempValue = value;
                default:
                    break;
            }
        }
        // parsing.Tag is complete. Reset tag.
        if (!hasName) {
            currentPrimaryElement.addTag(currentTag);
        }
        currentTag = null;
    }


    /**
     * Handles a relation member element.
     *
     * @param atts Attributes of this relation member.
     */
    public void handleRelationMember(Attributes atts) {
        currentRelationMember = new RelationMember();
        for (int i = 0; i < atts.getLength(); i++) {
            String qName = atts.getQName(i);
            String type = atts.getType(i);
            String value = atts.getValue(i);
            switch (qName) {
                case "type":
                    currentRelationMember.setType(value);
                    break;
                case "ref":
                    currentRelationMember.setRef(value);
                    break;
                case "role":
                    currentRelationMember.setRole(value);
                    break;
                default:
                    currentRelationMember.addAttribute(value);
                    break;
            }
        }
        ((Relation) currentPrimaryElement).addMember(currentRelationMember);
        currentRelationMember = null;
    }

    /**
     * Returns the current primary element of this OSMElementHandler.
     *
     * @return This handler's current primary element.
     */
    public OSMElement getCurrentPrimaryElement() {
        return currentPrimaryElement;
    }


}
