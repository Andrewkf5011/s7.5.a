package graph;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import graph.*;

public class GraphPanel extends JPanel  implements  ActionListener {
    public static final long serialVersionUID = 1L;
    String title;
    Scale  scale;
    double rate;
    int[] xaxis;
    ArrayList<Trace> traces = new ArrayList<Trace>();
    Hashtable<String,String> datasource;

    public GraphPanel(String title, Dimension size, Scale scale) {
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        this.setBorder( BorderFactory.createTitledBorder(title) );
        this.title = title;
        this.scale = scale;

        this.setPreferredSize( size );
        this.xaxis = new int[size.width];
        for(int x=0 ; x<size.width ; x++ )  xaxis[x]=x;
    }

    public void dataSource(Hashtable<String,String> data) {
        this.datasource = data;
    }
    public void addTrace(String name, double init, Color color){
        traces.add(new Trace(name,this.getSize().width,color));
        datasource.put(name,Double.toString(init));
    }
    public void actionPerformed(ActionEvent evt) {
        for(Trace t : traces) {
            t.addPoint( Double.parseDouble(datasource.get(t.name)));
        }
        this.repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(Trace t : traces){
            int y[] = t.getPoints(this.getSize().height, scale);
            g.setColor( t.color );
            g.drawPolyline(xaxis,y, xaxis.length);
        }
    }
}
