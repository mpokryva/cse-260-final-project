package skeleton;

import java.util.ArrayList;
import java.util.List;

/**
 * An object of this class represents a relation in an OSM file.
 */
public class Relation extends OSMElement {

    /**
     * A list of this relation's relation members.
     */
    private List<RelationMember> memberList;


    /**
     * Constructs a new handin1.Relation, with the specified ID.
     * @param id ID of this handin1.Relation object.
     */
    public Relation(String id) {
        super(id);
        memberList = new ArrayList<>();
    }

    /**
     * Adds a new handin1.RelationMember to this handin1.Relation.
     * @param member The handin1.RelationMember to add.
     */
    public void addMember(RelationMember member) {
        memberList.add(member);
    }
}
