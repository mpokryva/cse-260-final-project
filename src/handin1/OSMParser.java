package handin1;

import java.io.*;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Sample parser for reading Open Street handin1.Map XML format files.
 * Illustrates the use of SAXParser to parse XML.
 *
 * @author E. Stark
 * @date September 20, 2009
 */
class OSMParser {

    /**
     * OSM file from which the input is being taken.
     */
    private File file;
    private OSMElementHandler elementHandler;
    private Map map;


    /**
     * Initialize an handin1.OSMParser that takes data from a specified file.
     *
     * @param s The file to read.
     * @throws IOException
     */
    public OSMParser(File f) {
        file = f;
        elementHandler = new OSMElementHandler();
        map = new Map();
    }

    /**
     * Parse the OSM file underlying this handin1.OSMParser.
     */
    public void parse()
            throws IOException, ParserConfigurationException, SAXException {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setValidating(false);
        SAXParser saxParser = spf.newSAXParser();
        XMLReader xmlReader = saxParser.getXMLReader();
        OSMHandler handler = new OSMHandler();
        xmlReader.setContentHandler(handler);
        InputStream stream = null;
        try {
            stream = new FileInputStream(file);
            InputSource source = new InputSource(stream);
            xmlReader.parse(source);
        } catch (IOException x) {
            throw x;
        } finally {
            if (stream != null)
                stream.close();
        }
    }

    public Map getMap(){
        return map;
    }


    /**
     * Handler class used by the SAX XML parser.
     * The methods of this class are called back by the parser when
     * XML elements are encountered.
     */
    class OSMHandler extends DefaultHandler {

        /**
         * Current character data.
         */
        private String cdata;

        /**
         * Attributes of the current element.
         */
        private Attributes attributes;

        /**
         * Get the most recently encountered CDATA.
         */
        public String getCdata() {
            return cdata;
        }

        /**
         * Get the attributes of the most recently encountered XML element.
         */
        public Attributes getAttributes() {
            return attributes;
        }

        /**
         * Method called by SAX parser when start of document is encountered.
         */
        public void startDocument() {
            System.out.println("startDocument");
        }

        /**
         * Method called by SAX parser when end of document is encountered.
         */
        public void endDocument() {
            System.out.println("endDocument");
        }

        /**
         * Method called by SAX parser when start tag for XML element is
         * encountered.
         */
        public void startElement(String namespaceURI, String localName,
                                 String qName, Attributes atts) {
            attributes = atts;
            System.out.println("startElement: " + namespaceURI + ","
                    + localName + "," + qName);

            // Element is primary (node, way, or relation).
            if (qName.equals("node") || qName.equals("way") || qName.equals("relation")) {
                // map.addElement(elementHandler.getCurrentPrimaryElement());
                elementHandler.choosePrimaryElement(qName);
            }

            // Element is secondary (nd, tag, etc).



            if (atts.getLength() > 0)
                showAttrs(qName, atts);


        }

        /**
         * Method called by SAX parser when end tag for XML element is
         * encountered.  This can occur even if there is no explicit end
         * tag present in the document.
         */
        public void endElement(String namespaceURI, String localName,
                               String qName) throws SAXParseException {
            // Element is primary (node, way, or relation).
            if (qName.equals("node") || qName.equals("way") || qName.equals("relation")) {
                map.addElement(elementHandler.getCurrentPrimaryElement());
            }

            System.out.println("endElement: " + namespaceURI + ","
                    + localName + "," + qName);
        }

        /**
         * Method called by SAX parser when character data is encountered.
         */
        public void characters(char[] ch, int start, int length)
                throws SAXParseException {
            // OSM files apparently do not have interesting CDATA.
            //System.out.println("cdata(" + length + "): '"
            //                 + new String(ch, start, length) + "'");
            cdata = (new String(ch, start, length)).trim();
        }

        /**
         * Auxiliary method to display the most recently encountered
         * attributes.
         *
         * @param qName Type of element (node, way, relation, tag, etc.)
         * @param atts  Attributes of the element (ID, user ID, etc.)
         */
        private void showAttrs(String qName, Attributes atts) {
            // If non-null and incomplete
            if (qName.equals("tag")) {
                elementHandler.handleTag(attributes);
            }
            // This is a new relation member.
            if (qName.equals("member")) {
                elementHandler.handleRelationMember(attributes);
            }
            // Assumes that "ref" is the only thing in a "nd" element.
            if (qName.equals("nd")) {
                ((Way) elementHandler.getCurrentPrimaryElement()).addNodeRef(atts.getValue(0));
            }
            //Takes care of nodeList
            if (elementHandler.getCurrentPrimaryElement() instanceof Node) {
                elementHandler.handleNode(atts);
            } else if (elementHandler.getCurrentPrimaryElement() instanceof Way) {
                elementHandler.handleWay(atts);
            } else if (elementHandler.getCurrentPrimaryElement() instanceof Relation) {
                elementHandler.handleRelation(atts);
            }


            //System.out.println("\t" + qName + "=" + value
              //      + "[" + type + "]");

        }
    }


    /**
     * Test driver.  Takes filenames to be parsed as command-line arguments.
     */
    public static void main(String[] args) throws Exception {
        for (int i = 0; i < args.length; i++) {
            OSMParser prsr = new OSMParser(new File(args[i]));
            prsr.parse();
        }
    }


}


