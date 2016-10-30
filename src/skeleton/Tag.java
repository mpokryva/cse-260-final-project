package skeleton;

/**
 * An object of this class represents a tag in an OSM file.
 */
public class Tag {
    /**
     * The tag's key.
     */
    private String key;
    /**
     * The tag's value.
     */
    private String value;

    /**
     * Initializes a tag with a key and a value.
     * @param key The tag's key.
     * @param value The tag's value.
     */
    public Tag(String key, String value) {
        this.key = key;
        this.value = value;
    }


    /**
     * Returns the value of this tag, if the parameter is the same as this tag's key.
     *
     * @param key The key supplied by the user.
     * @return Value of this tag, if (content of) 'key' is the same as 'this.key'.
     * Otherwise, null is returned.
     */
    public String getValue(String key) {

    }

}
