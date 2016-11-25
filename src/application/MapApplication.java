package application;

import com.starkeffect.highway.GPSDevice;
import drawing.MapFrame;
import org.xml.sax.SAXException;
import parsing.OSMParser;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * Created by mpokr on 11/23/2016.
 */
public class MapApplication {

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        GPSDevice gpsDevice = new GPSDevice(args[0]);
        OSMParser parser = new OSMParser(new File(args[0]));
        parser.parse();
        MapFrame mapFrame = new MapFrame(parser.getMap());
        gpsDevice.addGPSListener(mapFrame);
    }
}
