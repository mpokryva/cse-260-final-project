package skeleton;

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
    private String ref;
    /**
     * The role of this relation member.
     */
    private String role;
    /**
     * The attributes of this relation member.
     */
    private List<String> attributes;


    public void setType(String type) {
        this.type = type;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void addAttribute(String attribute) {
        attributes.add(attribute);
    }

    public String getType() {
        return type;
    }
}
