package Lab3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Vector;


public class PaintCanvas extends JFrame {
    public void run() {
        JFrame frame = new JFrame("Draw");
        frame.setSize(300, 300);
        frame.setLocation(300, 300);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        Vector<DrawPoints> pointVec = new Vector();

        JPanel p = new JPanel() {
            Point pointStart = null;
            Point pointEnd   = null;
            {
                addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                        pointStart = e.getPoint();
                    }

                    public void mouseReleased(MouseEvent e) {
                        repaint();
                    }
                });
                addMouseMotionListener(new MouseMotionAdapter() {
                    public void mouseMoved(MouseEvent e) {
                        pointEnd = e.getPoint();
                    }

                    public void mouseDragged(MouseEvent e) {
                        pointEnd = e.getPoint();
                    }
                });
            }
            public void paint(Graphics g) {
                super.paint(g);
                if (pointStart != null) {
                    pointVec.add(new DrawPoints(pointStart, pointEnd));

                    g.setColor(Color.RED);

                    for(DrawPoints drawPoints : pointVec){
                        g.drawLine(drawPoints.getStart().x, drawPoints.getStart().y,
                                drawPoints.getEnd().x, drawPoints.getEnd().y);
                    }
                }
            }
        };
        frame.add(p);
        frame.setVisible(true);
    }
}