package navigation;

import jdk.internal.org.xml.sax.SAXException;
import parsing.Map;
import parsing.OSMParser;
import parsing.Way;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by mpokr on 12/2/2016.
 */
public class Test {

    public static void main(String[] args) throws Exception {
        OSMParser parser = new OSMParser(new File(args[0]));
        parser.parse();
        Map map = parser.getMap();
        Way way =  map.findWayById("118714341");
        System.out.println(way.getName());
        List<Edge> edgeList = map.createGraph(map);
        System.out.println(edgeList.get(0));


    }
}
