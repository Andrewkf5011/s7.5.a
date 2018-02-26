package graph;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import graph.*;

public class Trace {
    public String name;
    public Color color;
    double[] data;

    public Trace(String name, int size, Color color){
        this.name = name;
        data = new double[size];
        this.color = color;
    }
    public void addPoint(double d) {
        for(int n=0 ; n<(data.length-1) ; n++) {
            data[n] = data[n+1];
        }
        data[data.length-1] = d;
    }
    public int[] getPoints(int height, Scale scale){
        int[] points = new int[data.length];
        for(int n=0 ; n<data.length ; n++ ){
            points[n] = (int)(Math.map(data[n], scale.from, scale.to, height,0));
        }
        return points;
    }
}
