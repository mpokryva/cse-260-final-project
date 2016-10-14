import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.io.File;
import java.util.List;
import java.util.StringJoiner;

/**
 * Created by mpokr on 10/13/2016.
 */
public class MapDisplay extends JPanel {
    private Map map;
    private final double NODE_Y_OFFSET;
    private final double NODE_X_OFFSET;
    private final double POINT_RADIUS = 2;
    private static final double PIXELS_PER_DEGREE = 5000;
    private JList wayNameList;
    private String selectedWay;


    public MapDisplay(Map map){
        this.map = map;
        NODE_X_OFFSET = map.getMinLat();
        NODE_Y_OFFSET = map.getMinLon();
        initJList();
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

    private JList getWayNameJList(){
        return wayNameList;
    }

    private void drawWayByName(String wayName, Graphics2D g2){
        Way matchingWay = map.findWayByName(wayName);
        if (matchingWay != null){
            List<Node> nodesInWay = map.findNodesInWay(matchingWay);
            for (Node node : nodesInWay){
                double lonScaleFactor = (PIXELS_PER_DEGREE)*Math.cos(node.getLat()-NODE_X_OFFSET);
                double latScaleFactor = (PIXELS_PER_DEGREE)*(1/Math.cos(node.getLat()- NODE_X_OFFSET));
                double scaledLat = ((node.getLat() - NODE_X_OFFSET)*latScaleFactor);
                double scaledLon = ((node.getLon() - NODE_Y_OFFSET)*lonScaleFactor);
                //g2.draw(new Line2D.Double(scaledLat+300, scaledLon+250,scaledLat+300, scaledLon+250));
                Ellipse2D.Double point = new Ellipse2D.Double(scaledLat+600, scaledLon-3000, POINT_RADIUS, POINT_RADIUS);
                g2.fill(point);
            }
            repaint();
        }
        else {
            System.out.println(wayName+ " not found.");
        }
    }

    private void addWayListListener(){
        wayNameList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedWay = (String)wayNameList.getSelectedValue();
                repaint();
            }
        });
    }

    private void initJList(){
        wayNameList = new JList(map.getWayNames());
        addWayListListener();
        JScrollPane listScroller = new JScrollPane(wayNameList);
        listScroller.setPreferredSize(new Dimension(250, 80));
    }


    @Override
    protected void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        super.paintComponent(g2);
        /**
        Way matchingWay = map.findWayByName(selectedWay);
        if (matchingWay != null){
            List<Node> nodesInWay = map.findNodesInWay(matchingWay);
            for (Node node : nodesInWay){
                double lonScaleFactor = (PIXELS_PER_DEGREE)*Math.cos(node.getLat()-NODE_X_OFFSET);
                double latScaleFactor = (PIXELS_PER_DEGREE)*(1/Math.cos(node.getLat()- NODE_X_OFFSET));
                double scaledLat = ((node.getLat() - NODE_X_OFFSET)*latScaleFactor);
                double scaledLon = ((node.getLon() - NODE_Y_OFFSET)*lonScaleFactor);
                //g2.draw(new Line2D.Double(scaledLat+300, scaledLon+250,scaledLat+300, scaledLon+250));
                double latTranslate = PIXELS_PER_DEGREE*.08;
                double lonTranslate = PIXELS_PER_DEGREE*.65;
                Ellipse2D.Double point = new Ellipse2D.Double((scaledLat-latTranslate), scaledLon-lonTranslate, POINT_RADIUS, POINT_RADIUS);
                g2.fill(point);
            }
            repaint();
        }
        else {
            System.out.println(selectedWay + " not found.");
        }
         **/
        List<Way> wayList = map.getWayList();
        for (Way way : wayList){
            List<Node> nodesInWay = map.findNodesInWay(way);
            //
            for (Node node : nodesInWay){
                double lonScaleFactor = (PIXELS_PER_DEGREE)*Math.cos(node.getLat()-NODE_X_OFFSET);
                double latScaleFactor = (PIXELS_PER_DEGREE);//*(1/Math.cos(node.getLat()- NODE_X_OFFSET));
                double scaledLat = ((node.getLat() - NODE_X_OFFSET)*latScaleFactor);
                double scaledLon = ((node.getLon() - NODE_Y_OFFSET)*lonScaleFactor);
                //g2.draw(new Line2D.Double(scaledLat+300, scaledLon+250,scaledLat+300, scaledLon+250));
                Ellipse2D.Double point = new Ellipse2D.Double(scaledLat+1300, scaledLon-1500, POINT_RADIUS, POINT_RADIUS);
                g2.fill(point);
            }
        }
    }






    public static void main(String[] args) throws Exception {
        OSMParser parser = new OSMParser(new File(args[0]));
        parser.parse();

        JFrame mainFrame = new JFrame("Map display");
        mainFrame.setLayout(new BorderLayout());

        MapDisplay mapDisplay = new MapDisplay(parser.getMap());
        mainFrame.add(mapDisplay, BorderLayout.CENTER);
        mainFrame.add(mapDisplay.getWayNameJList(), BorderLayout.WEST);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setVisible(true);
    }
}
