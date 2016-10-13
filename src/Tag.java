import java.io.ObjectInputStream.GetField;

public class Tag {
	private String key;
	private String value;
	
	public Tag(String key, String value){
		this.key = key;
		this.value = value;
	}



	/**
	 * Returns the value of this tag, if the parameter is the same as this tag's key.
	 * @param key The key supplied by the user.
	 * @return Value of this tag, if (content of) 'key' is the same as 'this.key'.
	 * Otherwise, null is returned.
	 */
	public String getValue(String key){
		if (key.equals(this.key)){
			return value;
		}
		else{
			return null;
		}
	}
	
	public String getKey(){
		return key;
	}
}
