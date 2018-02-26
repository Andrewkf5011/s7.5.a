package graph;

public class Math {

    static public double norm(double value, double low, double high) {
        return (value-low)/(high-low);
    }

    static public double lerp(double low, double high, double fraction) {
        return (1-fraction)*low + fraction*high;
    }

    static public double map(double value, double inlow, double inhigh, double outlow, double outhigh ) {
        return lerp( outlow, outhigh, norm(value, inlow, inhigh) );
    }

}
