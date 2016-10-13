import org.xml.sax.Attributes;

/**
 * Created by mpokr on 10/12/2016.
 */
public class OSMElementHandler {

    private OSMElement currentPrimaryElement;
    private Tag currentTag;
    private RelationMember currentRelationMember;
    // Holds ID value of current primary element. Added in case "id" field is not first in the xml file.
    private String tempPrimaryID:


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

    public void handleNode(String qName, String value) {
        // Checks if first time seeing node
        if (currentPrimaryElement.getId() == null) {
            // value is ID
            currentPrimaryElement = new Node(value);
        }
        // Just put attributes in list, with a few exceptions.
        else {
            if (qName.equals("lat")) {
                ((Node) currentPrimaryElement).setLat(value);
            } else if (qName.equals("lon")) {
                ((Node) currentPrimaryElement).setLon(value);
            }
            // Put attribute in list
            else {
                currentPrimaryElement.addAttribute(qName, value);
            }
        }
    }

    public void handleNode(Attributes atts) {
        String qName = null;
        String type = null;
        String value = null;
        for (int i = 0; i < atts.getLength(); i++) {
            qName = atts.getQName(i);
            type = atts.getType(i);
            value = atts.getValue(i);
        }
        // Checks if first time seeing node
        if (currentPrimaryElement.getId() == null) {
            // value is ID
            currentPrimaryElement = new Node(value);
        }
        // Just put attributes in list, with a few exceptions.
        else {
            if (qName.equals("lat")) {
                ((Node) currentPrimaryElement).setLat(value);
            } else if (qName.equals("lon")) {
                ((Node) currentPrimaryElement).setLon(value);
            }
            // Put attribute in list
            else {
                currentPrimaryElement.addAttribute(qName, value);
            }
        }
    }

    public void handleWay(String qName, String value) {
        if (currentPrimaryElement.getId() == null) {
            // value is ID
            currentPrimaryElement = new Way(value);
        }
        // Just put attributes in list, with a few exceptions.
        else {
            currentPrimaryElement.addAttribute(qName, value);
        }
    }

    public void handleRelation(String qName, String value) {
        if (currentPrimaryElement.getId() == null) {
            // value is ID
            currentPrimaryElement = new Relation(value);
        }
        // Just put attributes in list, with a few exceptions.
        else {
            currentPrimaryElement.addAttribute(qName, value);
        }
    }


    public void handleTag(String qName, String value) {
        // Encountered key.
        if (qName.equals("k")){

            currentTag = new Tag(value, currentTag.getValue(value));
        }
        // Encountered value.
        else {
            currentTag = new Tag(currentTag.getKey(), value);
        }
        // Tag is complete.
        if (currentTag.getKey() != null && currentTag.getValue(currentTag.getKey()) != null){
            currentPrimaryElement.addTag(currentTag);
            currentTag = null;
        }
    }


    public void handleRelationMember(String qName, String value) {
        if (qName.equals("type")) {
            currentRelationMember.setType(value);
        } else if (qName.equals("ref")) {
            currentRelationMember.setRef(value);
        } else if (qName.equals("role")) {
            currentRelationMember.setRole(value);
        } else {
            currentRelationMember.addAttribute(value);
        }
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

    public boolean isRelationMemberNew() {
        return (currentRelationMember != null && currentRelationMember.getType() == null);
    }

    /**
     * Checks if tag is non null and completely constructed.
     * @return True if tag is nun OR true if non-null, its key is non-null, and its value is non-null. False otherwise.
     */
    public boolean isTagComplete(){
        if (currentTag != null){
            return (currentTag.getKey() != null && currentTag.getValue(currentTag.getKey()) != null);
        }
        else {
            return true;
        }


    }


}
