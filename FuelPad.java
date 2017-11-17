import javax.swing.*;
import javax.vecmath.Point2d;
import javax.vecmath.Vector2d;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.util.Observable;
import java.awt.geom.*;
import java.awt.*;

class FuelPad extends Observable {
        public int x;
        public int y;
        public int oldx;
        public int oldy;
        boolean selected;
	public boolean used;

    public FuelPad(int x, int y) {
        this.x = x;
        this.y = y;
        selected = false;
	used = false;
    }


        public int getX() {
                return x;
        }

        public int getY() {
                return y;
        }


        public void draw(Graphics2D g2) {
		if (!used) {
                g2.setColor(Color.GRAY);
                g2.fillRect(this.x-5, this.y-5, 10, 10);
		g2.setColor(Color.GREEN);
		g2.fillRect(this.x-4, this.y-1, 8, 2);
		g2.fillRect(this.x-1, this.y-4, 2, 8);
		}
        }

        public void setupold() {
                oldx = x;
                oldy = y;
        }
	
	public void reset() {
		selected = false;
		used = false;
	}

	public boolean inside(int x, int y) {
                return x < this.x+5 && x > this.x-5 && y < this.y+5 && y > this.y-5;
        }

        public Rectangle2D getShape() {
                return new Rectangle2D.Double(x-5,y-5, 10, 10);
        }
}

