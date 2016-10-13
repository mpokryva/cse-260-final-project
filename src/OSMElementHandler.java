/**
 * Created by mpokr on 10/12/2016.
 */
public class OSMElementHandler {

    private OSMElement currentPrimaryElement;
    private Tag currentTag;
    private RelationMember currentRelationMember;


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

    public void handleNode(String value, String qName) {
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

    public void handleRelation(String qName, String value){
        if (currentPrimaryElement.getId() == null){
            // value is ID
            currentPrimaryElement = new Relation(value);
        }
        // Just put attributes in list, with a few exceptions.
        else {
            currentPrimaryElement.addAttribute(qName, value);
        }
    }


    public void handleTag(String qName, String value) {
        if (qName.equals("name")) {
            currentPrimaryElement.setName(value);
        } else {
            currentTag = new Tag(qName, value);
            currentPrimaryElement.addTag(currentTag);
        }
        currentTag = null;

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

    public RelationMember getCurrentRelationMember(){
        return currentRelationMember;
    }


}
