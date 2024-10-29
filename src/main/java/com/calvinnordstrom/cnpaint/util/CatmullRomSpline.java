package com.calvinnordstrom.cnpaint.util;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

public class CatmullRomSpline {
    public List<Point2D> computeSpline(Point2D P0, Point2D P1, Point2D P2, Point2D P3, double alpha, int steps) {
        List<Point2D> result = new ArrayList<>();
        double t0 = 0.0;
        double t1 = getT(t0, P0, P1, alpha);
        double t2 = getT(t1, P1, P2, alpha);
        double t3 = getT(t2, P2, P3, alpha);

        for (int i = 0; i <= steps; i++) {
            double t = t1 + (t2 - t1) * (i / (double) steps);
            Point2D point = interpolate(P0, P1, P2, P3, t0, t1, t2, t3, t);
            result.add(point);
        }

        return result;
    }

    private double getT(double t, Point2D p0, Point2D p1, double alpha) {
        double dx = p1.getX() - p0.getX();
        double dy = p1.getY() - p0.getY();
        return t + Math.pow(dx * dx + dy * dy, alpha * 0.5);
    }

    private Point2D interpolate(Point2D P0, Point2D P1, Point2D P2, Point2D P3,
                                double t0, double t1, double t2, double t3, double t) {
        Point2D A1 = P0.multiply((t1 - t) / (t1 - t0)).add(P1.multiply((t - t0) / (t1 - t0)));
        Point2D A2 = P1.multiply((t2 - t) / (t2 - t1)).add(P2.multiply((t - t1) / (t2 - t1)));
        Point2D A3 = P2.multiply((t3 - t) / (t3 - t2)).add(P3.multiply((t - t2) / (t3 - t2)));

        Point2D B1 = A1.multiply((t2 - t) / (t2 - t0)).add(A2.multiply((t - t0) / (t2 - t0)));
        Point2D B2 = A2.multiply((t3 - t) / (t3 - t1)).add(A3.multiply((t - t1) / (t3 - t1)));

        return B1.multiply((t2 - t) / (t2 - t1)).add(B2.multiply((t - t1) / (t2 - t1)));
    }
}
