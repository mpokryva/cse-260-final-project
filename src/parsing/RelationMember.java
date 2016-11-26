package parsing;

import java.util.List;

/**
 * An object of this class represents a relation membmer in an
 * OSM file.
 */
public class RelationMember {

    /**
     * The type of this relation member (nde, way, relation, etc).
     */
    private String type;
    /**
     * The reference id of this relation member.
     */
    private String refId;
    /**
     * The role of this relation member.
     */
    private String role;
    /**
     * The attributes of this relation member.
     */
    private List<String> attributes;


    /**
     * Default constructor.
     */
    public RelationMember() {

    }

    /**
     * Sets the type of this relation member to the specified type.
     *
     * @param type The relation member's new type.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Sets the reference ID of this relation member to the specified reference ID.
     *
     * @param refId The relation member's new reference ID.
     */
    public void setRefId(String refId) {
        this.refId = refId;
    }

    /**
     * Sets the role of this relation member to the specified role.
     *
     * @param role The relation member's new role.
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Adds an attribute to this relation member.
     *
     * @param attribute The attribute to add.
     */
    public void addAttribute(String attribute) {
        attributes.add(attribute);
    }

    /**
     * Returns the type of this relation member
     *
     * @return This relation member's type.
     */
    public String getType() {
        return type;
    }

    /**
     * Checks if this relation member is of type "way".
     * @return True if of type "way". False otherwise.
     */
    public boolean isWay(){
        return type.equals("way");
    }

    /**
     * Checks if this relation member is of type "node".
     * @return True if of type "node". False otherwise.
     */
    public boolean isNode(){
        return type.equals("node");
    }

    /**
     * Checks if this relation member is of type "relation".
     * @return True if of type "relation". False otherwise.
     */
    public boolean isRelation(){
        return type.equals("relation");
    }

    /**
     * Returns the reference ID of this relation member.
     * @return The reference ID of this relation member.
     */
    public String getRefId() {
        return refId;
    }
}
