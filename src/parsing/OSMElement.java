package parsing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * An object of this class represents a primary element in an OSM file.
 * A primary element is either a Vertex, Way, or Relation.
 */
public class OSMElement {

    /**
     * The name of this element.
     */
    private String name;
    /**
     * The id of this element.
     */
    private String id;
    /**
     * List of attributes such as uis, version, changeset, etc.
     */
    private HashMap<String, String> attributeMap;
    /**
     * List of tags of this element.
     */
    private List<Tag> tagList;

    private HashMap<String, String> tagMap;

    /**
     * Initializes an OSMElement with the given ID>
     *
     * @param id The ID of this OSMElement.
     */
    public OSMElement(String id) {
        attributeMap = new HashMap<>();
        tagList = new ArrayList<>();
        tagMap = new HashMap<>();
        this.id = id;
        this.name = "";
    }

    /**
     * Default constructor.
     */
    public OSMElement() {

    }

    /**
     * Adds an attribute to this element's list of attributes.
     *
     * @param type  The attributes type (uid, changeset, etc.)
     * @param value The value of this attribute.
     */
    public void addAttribute(String type, String value) {
        attributeMap.put(type, value);
    }

    /**
     * Returns an attribute given its key.
     * @param attrKey The attribute key
     * @return The attribute value.
     */
    public String getAttr(String attrKey){
        return attributeMap.get(attrKey);
    }

    /**
     * Adds a tag to this element's list and HashMap of tags.
     *
     * @param tagToAdd The tag to add.
     */
    public void addTag(Tag tagToAdd) {
        tagList.add(tagToAdd);
        tagMap.put(tagToAdd.getKey(), tagToAdd.getValue(tagToAdd.getKey()));
    }

    /**
     * Checks if this element has the specified tag key & value pair.
     * @param key The tag key.
     * @param value The tag value.
     * @return True, if this element has the specified tag key & value pair. False otherwise.
     */
    public boolean hasTagPair(String key, String value){
        return (tagMap.get(key) != null && tagMap.get(key).equals(value));
    }

    /**
     * Returns the tag value matching the specified key.
     * @param tagKey The tag's key.
     * @return The tag's value.
     */
    public String getTag(String tagKey){
        return tagMap.get(tagKey);
    }

    /**
     * Convenience method for checking if this element has a specified tag, given a tag key.
     * @param key The tag key.
     * @return True if this element has a tag with the specified key.
     */
    public boolean hasTag(String key){
        return tagMap.get(key) != null;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    /**
     * Returns this element's hashcode.
     *
     * @return This element's hashcode.
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }

    /**
     * Only equal if the two elements' id's are equal.
     *
     * @param other The element to compare to.
     * @return True if the two elements' id's are equal.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof OSMElement)) {
            return false;
        }
        return this.getId().equals(((OSMElement) other).getId());
    }


}
