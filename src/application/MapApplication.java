package application;

import com.starkeffect.highway.GPSDevice;
import drawing.MapFrame;
import navigation.ShortestPathGenerator;
import navigation.Vertex;
import org.xml.sax.SAXException;
import parsing.OSMParser;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedList;

/**
 * Created by mpokr on 11/23/2016.
 */
public class MapApplication {

    public static void main(String[] args) {
        try {
            GPSDevice gpsDevice = new GPSDevice(args[0]);
            OSMParser parser = new OSMParser(new File(args[0]));
            parser.parse();
            MapFrame mapFrame = new MapFrame(parser.getMap());
            gpsDevice.addGPSListener(mapFrame);
        } catch (Exception e) {
            JFrame errorFrame = new JFrame();
            errorFrame.setSize(100, 100);
            int answer = JOptionPane.showOptionDialog(errorFrame, null, "File not found", JOptionPane.CANCEL_OPTION, JOptionPane.ERROR_MESSAGE,
                    null, new String[]{"Quit"}, null);
            if(answer > -1){
                System.exit(0);
            }
        }
    }

    public static void launch(File file) throws Exception {
        String fileName = file.getParentFile().getName() + "/" + file.getName();
        GPSDevice gpsDevice = new GPSDevice(fileName);
        OSMParser parser = new OSMParser(file);
        parser.parse();
        MapFrame mapFrame = new MapFrame(parser.getMap());
        gpsDevice.addGPSListener(mapFrame);
    }
}
