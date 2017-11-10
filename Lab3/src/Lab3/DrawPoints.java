package Lab3;

import java.awt.*;

public class DrawPoints {
    private Point start;
    private Point end;

    public DrawPoints(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    public void setEnd(Point end) {
        this.end = end;
    }
}
