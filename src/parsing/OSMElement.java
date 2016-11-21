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
    private HashMap<String, String> attributeList;
    /**
     * List of tags of this element.
     */
    private List<Tag> tagList;

    /**
     * Initializes an OSMElement with the given ID>
     *
     * @param id The ID of this OSMElement.
     */
    public OSMElement(String id) {
        attributeList = new HashMap<>();
        tagList = new ArrayList<>();
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
    void addAttribute(String type, String value) {
        attributeList.put(type, value);
    }

    /**
     * Adds a tag to this element's list of tags.
     *
     * @param tagToAdd The tag to add.
     */
    void addTag(Tag tagToAdd) {
        tagList.add(tagToAdd);
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
