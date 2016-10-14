import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;

/**
 * Created by mpokr on 10/13/2016.
 */
public class MapDisplay extends JPanel {
    private Map map;
    private OSMParser parser;


    public MapDisplay(Map map){
        this.map = map;
        parser = new OSMParser(new File("usb.osm"));
    }

    /**
     * Queries the ways of the Map object, and draws roads in the panel.
     */
    private void drawRoads(){

        List<Way> wayList = map.getWayList();
        for (Way way : wayList){
            List<Node> nodesInWay = map.findNodesInWay(way);
            //
            for (Node node : nodesInWay){

            }
        }
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        List<Way> wayList = map.getWayList();
        for (Way way : wayList){
            List<Node> nodesInWay = map.findNodesInWay(way);
            //
            for (Node node : nodesInWay){
                int nodeX = (int)node.getLat();
                int nodeY = (int)node.getLon();
                g.drawLine(nodeX, nodeY, nodeX, nodeY);
            }
        }
    }






    public static void main(String[] args) throws Exception {
        OSMParser parser = new OSMParser(new File(args[0]));
        parser.parse();

        JFrame mainFrame = new JFrame("Map display");
        MapDisplay mapDisplay = new MapDisplay(parser.getMap());
        mainFrame.setContentPane(mapDisplay);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }
}
