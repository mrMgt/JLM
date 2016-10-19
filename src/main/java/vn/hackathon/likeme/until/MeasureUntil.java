package vn.hackathon.likeme.until;

import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;

/**
 * Created by bangnl on 3/9/16.
 */
public class MeasureUntil {
    static public Distance getDistanceByMet(double radius){
        double km = exchangeKilometToMet(radius);
        return new Distance(km, Metrics.KILOMETERS);
    }

    static public double exchangeKilometToMet(double km){
        if(km == 0){
            return km;
        }
        return km / 1000;
    }

    static public double getDistance(double[] a, double[] b){
        double x = Math.pow(Math.abs(a[0] - b[0]),2);
        double y = Math.pow(Math.abs(a[1] - b[1]),2);
        return  Math.sqrt(x + y);
    }
}
