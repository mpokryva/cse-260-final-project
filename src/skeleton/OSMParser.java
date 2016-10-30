package skeleton;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Sample parser for reading Open Street Map XML format files.
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
    /**
     *   Converts parsed XML String to OSMElements
     */
    private OSMElementHandler elementHandler;
    /**
     * Stores the OSMElements
     */
    private Map map;


    /**
     * Initialize an OSMParser that takes data from a specified file.
     *
     * @param f The file to read.
     * @throws IOException
     */
    public OSMParser(File f) {

    }

    /**
     * Parse the OSM file underlying this OSMParser.
     */
    public void parse()
            throws IOException, ParserConfigurationException, SAXException {

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
         *
         * If element is a primary element, it is added to the map.
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
         * Handles the most recently encountered attributes.
         * Different handling based on the current primary element.
         *
         * @param qName Type of element (node, way, relation, tag, etc.)
         * @param atts  Attributes of the element (ID, user ID, etc.)
         */
        private void showAttrs(String qName, Attributes atts) {
            // If non-null and incomplete
            if (qName.equals("tag")) {

            }
            // This is a new relation member.
            if (qName.equals("member")) {

            }
            // Assumes that "ref" is the only thing in a "nd" element.
            if (qName.equals("nd")) {

            }
            //Takes care of nodeList
            if (elementHandler.getCurrentPrimaryElement() instanceof Node) {

            } else if (elementHandler.getCurrentPrimaryElement() instanceof Way) {

            } else if (elementHandler.getCurrentPrimaryElement() instanceof Relation) {

            }
        }
    }





}


