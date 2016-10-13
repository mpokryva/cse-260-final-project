import java.util.List;

/**
 * Created by mpokr on 10/12/2016.
 */
public class RelationMember {

    private String type;
    private String ref;
    private String role;
    private List<String> attributes;

    public RelationMember(){

    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void addAttribute(String attribute){
        attributes.add(attribute);
    }
}
