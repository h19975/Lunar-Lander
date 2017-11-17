import javax.swing.*;
import javax.vecmath.Point2d;
import javax.vecmath.Vector2d;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.util.Observable;
import java.awt.geom.*;
import java.awt.*;

class LandingPad extends Observable {
	public int x;
	public int y;
	public int oldx;
	public int oldy;
	boolean	selected;
    
    public LandingPad(int x, int y) {
	this.x = x;
	this.y = y;
	selected = false;
    }

	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}


	public void draw(Graphics2D g2) {
		g2.setColor(Color.RED);
		g2.fillRect(this.x-20, this.y-5, 40, 10);
	}
	
	public boolean inside(int x, int y) {
		return x < this.x+20 && x > this.x-20 && y < this.y+5 && y > this.y-5;
	}
	
	public void setupold() {
		oldx = x;
		oldy = y;
	}
		
	public Rectangle2D getShape() {
		return new Rectangle2D.Double(x-20,y-5, 40, 10);
	}
}

