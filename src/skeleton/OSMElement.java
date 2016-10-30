package skeleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * An object of this class represents a primary element in an OSM file.
 * A primary element is either a Node, Way, or Relation.
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
    private List<Tag> tagList;

    /**
     * Initializes an OSMElement with the given ID>
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
    public OSMElement(){

    }


    /**
     * Adds an attribute to this element's list of attributes.
     * @param type The attributes type (uid, changeset, etc.)
     * @param value The value of this attribute.
     */
    public void addAttribute(String type, String value) {
        attributeList.put(type, value);
    }

    /**
     * Adds a tag to this element's list of tags.
     * @param tagToAdd The tag to add.
     */
    public void addTag(Tag tagToAdd) {
        tagList.add(tagToAdd);
    }



    @Override
    public int hashCode() {
        return id.hashCode();
    }


    /**
     * Only equal if the two element's id's are equal.
     * @param other The element to compare to.
     * @return True if the same element. False otherwise.
     */
    @Override
    public boolean equals(Object other){

    }

}
