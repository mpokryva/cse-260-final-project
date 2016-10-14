import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.io.File;
import java.util.List;

/**
 * Created by mpokr on 10/13/2016.
 */
public class MapDisplay extends JPanel {
    private Map map;
    private final double NODE_Y_OFFSET;
    private final double NODE_X_OFFSET;
    private final double POINT_RADIUS = 2;
    private static final double PIXELS_PER_DEGREE = 1300;


    public MapDisplay(Map map){
        this.map = map;
        NODE_X_OFFSET = map.getMinLat();
        NODE_Y_OFFSET = map.getMinLon();
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
        Graphics2D g2 = (Graphics2D)g;
        super.paintComponent(g2);
        List<Way> wayList = map.getWayList();
        for (Way way : wayList){
            List<Node> nodesInWay = map.findNodesInWay(way);
            //
            for (Node node : nodesInWay){
                double lonScaleFactor = (PIXELS_PER_DEGREE*Math.cos(node.getLat()));
                double latScaleFactor = (PIXELS_PER_DEGREE*(1/Math.cos(node.getLat())));
                double scaledLat = ((node.getLat() - NODE_X_OFFSET)*latScaleFactor);
                double scaledLon = ((node.getLon() - NODE_Y_OFFSET)*lonScaleFactor);
                //g2.draw(new Line2D.Double(scaledLat+300, scaledLon+250,scaledLat+300, scaledLon+250));
                Ellipse2D.Double point = new Ellipse2D.Double(scaledLat+300, scaledLon+1000, POINT_RADIUS, POINT_RADIUS);
                g2.fill(point);
            }
        }

    }






    public static void main(String[] args) throws Exception {
        OSMParser parser = new OSMParser(new File(args[0]));
        parser.parse();

        JFrame mainFrame = new JFrame("Map display");
        MapDisplay mapDisplay = new MapDisplay(parser.getMap());
        mainFrame.setContentPane(mapDisplay);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setVisible(true);
    }
}
