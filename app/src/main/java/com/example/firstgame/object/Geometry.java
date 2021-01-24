package com.example.firstgame.object;

public class Geometry {
    // distance of two point
    public static double distanceP2P(int x1, int y1, int x2, int y2) {
        int tmp = x1 - x2;
        int tmp2 = y1 - y2;
        return Math.sqrt(tmp*tmp + tmp2*tmp2);
    }

    // base on cosin theorem and angle between two vector
    private static double lenghtOfThirdEdge1(int x1, int y1, int x2, int y2, int x3, int y3) {
        double edge1 = Geometry.distanceP2P(x1, y1, x2, y2);
        double edge2 = Geometry.distanceP2P(x3, y3, x2, y2);

        int tmp = (x3 -x2)*(x1 - x2) + (y3 - y2)*(y1 - y2);
        return Math.sqrt(edge1*edge1 + edge2*edge2 - (double)2*tmp);
    }

    public static double lengthOfThirdEdge(int x_centerBall, int y_centerBall,
                                           int x_centerRect, int y_centerRect,
                                           int x_rest, int y_rest) {
        return lenghtOfThirdEdge1(x_centerBall, y_centerBall, x_centerRect, y_centerRect, x_rest, y_rest);
    }
}
