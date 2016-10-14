import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OSMElement {

    private String name;
    private String id;
    // List of attributes such as uis, version, changeset, etc.
    private HashMap<String, String> attributeList;
    private List<Tag> tagList;

    public OSMElement(String id) {
        attributeList = new HashMap<>();
        tagList = new ArrayList<>();
        this.id = id;
    }

    public OSMElement() {

    }


    public void addAttribute(String key, String value) {
        attributeList.put(key, value);
    }

    public void addTag(Tag tagToAdd) {
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

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    /*
    Probably NOT correct.
     */
    @Override
    public boolean equals(Object other){
        if (this == other){
            return true;
        }
        if (!(other instanceof OSMElement)){
            return false;
        }
        OSMElement osmOther = (OSMElement) other;
        return this.hashCode() == osmOther.hashCode();
    }

}
