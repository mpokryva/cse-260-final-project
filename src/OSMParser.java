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

    private OSMElement currentPrimaryElement;

    /**
     * Initialize an OSMParser that takes data from a specified file.
     *
     * @param s The file to read.
     * @throws IOException
     */
    public OSMParser(File f) {
        file = f;
    }

    /**
     * Parse the OSM file underlying this OSMParser.
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


            switch (qName) {
                case "node":
                    currentPrimaryElement = new Node();
                    break;
                case "way":
                    currentPrimaryElement = new Way();
                    break;
                case "relation":
                    currentPrimaryElement = new Relation();
                    break;
                default:
                    break;
            }

            if (atts.getLength() > 0)
                showAttrs(atts);


        }

        /**
         * Method called by SAX parser when end tag for XML element is
         * encountered.  This can occur even if there is no explicit end
         * tag present in the document.
         */
        public void endElement(String namespaceURI, String localName,
                               String qName) throws SAXParseException {

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
         */
        private void showAttrs(Attributes atts) {
            for (int i = 0; i < atts.getLength(); i++) {
                String qName = atts.getQName(i);
                String type = atts.getType(i);
                String value = atts.getValue(i);
                //Takes care of nodes
                if (currentPrimaryElement instanceof Node) {
                    // Checks if first time seeing node
                    if (currentPrimaryElement.getId() == null) {
                        // qName is ID
                        currentPrimaryElement = new Node(qName);
                    }
                    // Just put attributes in list, with a few exceptions.
                    else {
                        if (qName.equals("lat")) {
                            ((Node) currentPrimaryElement).setLat(value);
                        } else if (qName.equals("lon")) {
                            ((Node) currentPrimaryElement).setLon(value);
                        }
                        // Put attribute in list
                        else {
                            currentPrimaryElement.addAttribute(qName, value);
                        }
                    }

                }
                System.out.println("\t" + qName + "=" + value
                        + "[" + type + "]");
            }
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


