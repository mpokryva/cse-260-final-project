import org.xml.sax.Attributes;

/**
 * Created by mpokr on 10/12/2016.
 */
public class OSMElementHandler {

    private OSMElement currentPrimaryElement;
    private Tag currentTag;
    private RelationMember currentRelationMember;
    // Holds ID value of current primary element. Added in case "id" field is not first in the xml file.
    private String tempPrimaryId;


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
                    if (value.equals("name")){
                        hasName = true;
                    }
                    currentTag = new Tag(value, tempValue);
                    // Put attribute in list
                    break;
                // Encountered value.
                case "v":
                    if (hasName){
                        currentPrimaryElement.setName(value);
                    }
                    currentTag = new Tag(currentTag.getKey(), value);
                    tempValue = value;
                default:
                    break;
            }
        }
        // Tag is complete. Reset tag.
        if (!hasName) {
            currentPrimaryElement.addTag(currentTag);
        }
        currentTag = null;
    }


    public void handleRelationMember(Attributes atts) {
        currentRelationMember = new RelationMember();
        for (int i=0; i < atts.getLength(); i++){
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

    public void createEmptyTag() {
        currentTag = new Tag(null, null);
    }

    public void createEmptyRelationMember() {
        currentRelationMember = new RelationMember();
    }


    public OSMElement getCurrentPrimaryElement() {
        return currentPrimaryElement;
    }


    public Tag getCurrentTag() {
        return currentTag;
    }

    public RelationMember getCurrentRelationMember() {
        return currentRelationMember;
    }

    public boolean isRelationMemberComplete() {
        return (currentRelationMember != null && currentRelationMember.getType() == null);
    }

    /**
     * Checks if tag is non null and completely constructed.
     *
     * @return True if tag is nun OR true if non-null, its key is non-null, and its value is non-null. False otherwise.
     */
    public boolean isTagComplete() {
        if (currentTag != null) {
            return (currentTag.getKey() != null && currentTag.getValue(currentTag.getKey()) != null);
        } else {
            return true;
        }


    }


}
