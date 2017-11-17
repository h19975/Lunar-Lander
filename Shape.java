import javax.swing.*;
import javax.vecmath.Point2d;
import javax.vecmath.Vector2d;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.util.Observable;
import java.awt.geom.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

class Shape extends Observable { 
     public ArrayList<Point2D> points;
  
    // add a point to end of shape
	static Random rand;
	public int selected;
	public int oldselected;
	public int oldy;	
	public int getSelected() {
		return selected;
	}

	public void setSelected(int i) {
		selected = i;
	}

	private int random(int min, int max) {
		return rand.nextInt(max-min+1)+min;
	}

	
	public Shape() {
		selected = -1;
		rand = new Random();
		double interval = 700/19;	
		int y = 0;
		for (int i = 0; i < 20; i++) {
			y = random(100, 200);
			addPoint((double)i*interval, (double)y);
		}
		addPoint((double)700,(double)200);
		addPoint((double)0, (double)200);
	}
				
	private void clearPoints() {
		points = new ArrayList<Point2D>();
		pointsChanged = true;
	}
	
    private void addPoint(Point2D p) {
        if (points == null) clearPoints();
        points.add(p);
        pointsChanged = true;
    }    
    // add a point to end of shape
    private void addPoint(double x, double y) {
        addPoint(new Point2D.Double(x, y));  
    }
    public int npoints() {
        return points.size();
    }
    
    
    Boolean pointsChanged = false; // dirty bit
	
	
    int[] xpoints, ypoints;
    int npoints = 0;
    void cachePointsArray() {
        xpoints = new int[points.size()];
        ypoints = new int[points.size()];
        for (int i=0; i < points.size(); i++) {
            xpoints[i] = (int)points.get(i).getX();
            ypoints[i] = (int)points.get(i).getY();
        }
        npoints = points.size();
        pointsChanged = false;
    }
	
	public void inside(int x, int y) {
		for (int i = 0; i < 20; i++) {
			if ((x-xpoints[i])*(x-xpoints[i])+(y-ypoints[i])*(y-ypoints[i]) < 15*15) {
				selected = i; break;
			}
		}
	}

	public void update(int y) {
		if (selected != -1 && y < 200 && y > 0) {
			points.get(selected).setLocation(points.get(selected).getX(), (double)y);
			pointsChanged = true;
		}
	}	

	public Polygon getShape() {
		return new Polygon(xpoints, ypoints, npoints);
	}

    public void draw(Graphics2D g2, boolean circle) {
        if (points == null) return;
        
	cachePointsArray();
	g2.setColor(Color.DARK_GRAY);
        g2.fillPolygon(xpoints, ypoints, npoints);
	if (circle) {
		g2.setColor(Color.GRAY);
		for (int i = 0; i < 20; i++) {
			g2.drawOval(xpoints[i]-15, ypoints[i]-15, 30, 30);
		};
	}
    }
}
