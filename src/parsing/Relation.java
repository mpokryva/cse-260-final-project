package parsing;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dpokryvailo on 10/11/2016.
 */
public class Relation extends OSMElement {

    private List<RelationMember> memberList;


    public Relation(String id) {
        super(id);
        memberList = new ArrayList<>();
    }

    public void addMember(RelationMember member) {
        memberList.add(member);
    }
}
