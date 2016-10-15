import javafx.scene.shape.Polyline;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.io.File;
import java.util.ArrayList;
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
        List<Way> wayList = map.getWayList();
        for (Way way : wayList){
            List<Node> nodesInWay = map.findNodesInWay(way);
            double[] previousCoords = new double[2];
            for (Node node : nodesInWay){
                double lonScaleFactor = (PIXELS_PER_DEGREE)*Math.cos(Math.toRadians(node.getLat()));
                double lat = node.getLat();
                double lon = node.getLon();
                double centerLat = (map.getMaxLat()-map.getMinLat())/2 + map.getMinLat();
                double centerLon = (map.getMaxLon() - map.getMinLon())/2 + map.getMinLon()+.2;
                double scaledLat = -1*(lat-centerLat) * (PIXELS_PER_DEGREE) + (this.getHeight()/2);// (centerLat - pointLat) * zoom + screenHeight/2
                double scaledLon = (lon-centerLon) * lonScaleFactor + (this.getWidth()/2);
                //Ellipse2D.Double point = new Ellipse2D.Double(scaledLon, scaledLat, POINT_RADIUS, POINT_RADIUS);
                //g2.fill(point);
                Line2D.Double dot = new Line2D.Double(scaledLon,scaledLat,scaledLon,scaledLat);
                Shape prevLine = new Line2D.Double(previousCoords[0], previousCoords[1], scaledLon, scaledLat);
                if (this.getGraphicsConfiguration().getBounds().contains(dot.getX1(), dot.getY1())){
                    if (previousCoords[0] != 0){
                        g2.draw(prevLine);
                    }
                }
                previousCoords[0] = scaledLon;
                previousCoords[1] = scaledLat;
                g2.draw(dot);
            }


        }
    }






    public static void main(String[] args) throws Exception {
        OSMParser parser = new OSMParser(new File(args[0]));
        parser.parse();

        JFrame mainFrame = new JFrame("Map display");
        mainFrame.setLayout(new BorderLayout());

        MapDisplay mapDisplay = new MapDisplay(parser.getMap());
        mainFrame.add(mapDisplay);
        //mainFrame.add(mapDisplay.getWayNameJList(), BorderLayout.WEST);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setVisible(true);
    }
}
