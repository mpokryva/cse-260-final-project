package parsing;

import java.awt.*;
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
import sun.misc.ThreadGroupUtils;

/**
 * Sample parser for reading Open Street parsing.Map XML format files.
 * Illustrates the use of SAXParser to parse XML.
 *
 * @author E. Stark
 * @date September 20, 2009
 */
public class OSMParser {

    /**
     * OSM file from which the input is being taken.
     */
    private File file;
    /**
     * Converts parsed XML String to OSMElements
     */
    private OSMElementHandler elementHandler;
    /**
     * Map model that stores the OSMElements
     */
    private Map map;


    /**
     * Initialize an parsing.OSMParser that takes data from a specified file.
     *
     * @param f The file to read.
     * @throws IOException
     */
    public OSMParser(File f) {
        file = f;
        elementHandler = new OSMElementHandler();
        map = new Map();
    }

    /**
     * Parse the OSM file underlying this parsing.OSMParser.
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
     * Returns this parser's associated Map object.
     *
     * @return This parser's associated Map object.
     */
    public Map getMap() {
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
            //System.out.println("startDocument");
        }

        /**
         * Method called by SAX parser when end of document is encountered.
         */
        public void endDocument() {
            //System.out.println("endDocument");
        }

        /**
         * Method called by SAX parser when start tag for XML element is
         * encountered.
         */
        public void startElement(String namespaceURI, String localName,
                                 String qName, Attributes atts) {
            attributes = atts;
            //System.out.println("startElement: " + namespaceURI + ","
                //    + localName + "," + qName);

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
                OSMElement currentPrimaryElement = elementHandler.getCurrentPrimaryElement();
                if (currentPrimaryElement.getClass() == Way.class) {
                    Way currentWay = (Way) currentPrimaryElement;
                    configureWaySettings(currentWay);
                } else if (currentPrimaryElement.getClass() == Relation.class) {
                    Relation currentRelation = (Relation) currentPrimaryElement;
                    if (currentRelation.getTag("border_type") != null || currentRelation.getTag("boundary") != null) {
                        for (RelationMember member : currentRelation.getMemberList()) {
                            if (member.isWay()) {
                                Way wayMember = map.findWayById(member.getRefId());
                                if (wayMember != null) {
                                    wayMember.setColor(Color.GREEN);
                                }
                            }
                        }
                    }
                }
                map.addElement(elementHandler.getCurrentPrimaryElement());
            }

            //System.out.println("endElement: " + namespaceURI + ","
               //     + localName + "," + qName);
        }

        private void configureWaySettings(Way wayToAdd) {
            String highWayType = wayToAdd.getTag("highway");
            if (wayToAdd.hasTagPair("natural", "water")) {
                wayToAdd.setWater(true);
                wayToAdd.setColor(Way.WayColor.WATER.getColor());
            }

            // Check if way is a non-physical boundary and configure accordingly.
            if (wayToAdd.hasTag("boundary") && !(wayToAdd.hasTagPair("natural", "coastline"))) {
                wayToAdd.setBoundary(true);
            }
            if ((wayToAdd.hasTagPair("natural", "coastline"))) {
               wayToAdd.setColor(new Color(0,0,128));

            }

                // Check if way is a building and configure accordingly.
                if (wayToAdd.hasTagPair("building", "yes")) {
                    wayToAdd.setColor(Way.WayColor.BUILDING.getColor());
                    wayToAdd.setWayThickness(Way.Thickness.BUILDING.getThickness());
                    wayToAdd.setWayPriority(Way.Priority.BUILDING.getPriority());
                }
                if (highWayType != null) {
                    switch (highWayType) {
                        case ("motorway"):
                            wayToAdd.setColor(Way.WayColor.MOTORWAY.getColor());
                            wayToAdd.setWayThickness(Way.Thickness.MOTORWAY.getThickness());
                            wayToAdd.setWayPriority(Way.Priority.MOTORWAY.getPriority());
                            break;
                        case ("trunk"):
                            wayToAdd.setColor(Way.WayColor.TRUNK.getColor());
                            wayToAdd.setWayThickness(Way.Thickness.TRUNK.getThickness());
                            wayToAdd.setWayPriority(Way.Priority.TRUNK.getPriority());
                            break;
                        case ("primary"):
                            wayToAdd.setColor(Way.WayColor.PRIMARY.getColor());
                            wayToAdd.setWayThickness(Way.Thickness.PRIMARY.getThickness());
                            wayToAdd.setWayPriority(Way.Priority.PRIMARY.getPriority());
                            break;
                        case ("secondary"):
                            wayToAdd.setColor(Way.WayColor.SECONDARY.getColor());
                            wayToAdd.setWayThickness(Way.Thickness.SECONDARY.getThickness());
                            wayToAdd.setWayPriority(Way.Priority.SECONDARY.getPriority());
                            break;
                        case ("tertiary"):
                            wayToAdd.setColor(Way.WayColor.TERTIARY.getColor());
                            wayToAdd.setWayThickness(Way.Thickness.TERTIARY.getThickness());
                            wayToAdd.setWayPriority(Way.Priority.TERTIARY.getPriority());
                            break;
                        case ("unclassified"):
                            wayToAdd.setColor(Way.WayColor.UNCLASSIFIED.getColor());
                            wayToAdd.setWayThickness(Way.Thickness.UNCLASSIFIED.getThickness());
                            wayToAdd.setWayPriority(Way.Priority.UNCLASSIFIED.getPriority());
                            break;
                        case ("residential"):
                            wayToAdd.setColor(Way.WayColor.RESIDENTIAL.getColor());
                            wayToAdd.setWayThickness(Way.Thickness.RESIDENTIAL.getThickness());
                            wayToAdd.setWayPriority(Way.Priority.RESIDENTIAL.getPriority());
                            break;
                        default:
                            wayToAdd.setColor(Way.WayColor.DEFAULT.getColor());
                            wayToAdd.setWayThickness(Way.Thickness.DEFAULT.getThickness());
                            wayToAdd.setWayPriority(Way.Priority.DEFAULT.getPriority());
                    }
                }

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


