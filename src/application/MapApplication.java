package application;

import com.starkeffect.highway.GPSDevice;
import drawing.MapFrame;
import navigation.ShortestPathGenerator;
import navigation.Vertex;
import org.xml.sax.SAXException;
import parsing.OSMParser;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedList;

/**
 * Created by mpokr on 11/23/2016.
 */
public class MapApplication {
    private MapFrame currentFrame;
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        MapApplication mapApplication= new MapApplication();
        GPSDevice gpsDevice = new GPSDevice(args[0]);
        OSMParser parser = new OSMParser(new File(args[0]));
        parser.parse();
        MapFrame mapFrame = new MapFrame(parser.getMap());
        mapFrame.setMapApplication(mapApplication);
        gpsDevice.addGPSListener(mapFrame);
    }

    public static void launch(File file) throws Exception{
        String fileName = file.getParentFile().getName() + "/" + file.getName();
        GPSDevice gpsDevice = new GPSDevice(fileName);
        OSMParser parser = new OSMParser(file);
        parser.parse();
        MapFrame mapFrame = new MapFrame(parser.getMap());
        gpsDevice.addGPSListener(mapFrame);
    }
}
