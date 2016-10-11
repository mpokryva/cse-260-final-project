import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OSMElement {

	private String name;
	private String id;
	private HashMap<String, String> attributeList;
	private List<Tag> tagList;
	
	public OSMElement(String id){
		attributeList = new HashMap<>();
		tagList = new ArrayList<>();
		this.id = id;
	}

	public OSMElement (){

	}
	
	
	public void addAttribute(String key, String value){
		attributeList.put(key, value);
	}

	public void addTag(String key, String value) {
		Tag tagToAdd = new Tag(key, value);
		tagList.add(tagToAdd);
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

}
