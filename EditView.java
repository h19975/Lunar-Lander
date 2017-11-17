import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;
import javax.swing.event.*;
import java.util.Random;

// the editable view of the terrain and landing pad
public class EditView extends JPanel implements Observer { 
	
	private GameModel model;
	private boolean dragged;
	public EditView(GameModel model) {
		super();
		dragged = false;
		this.setSize(700, 200);
		this.model = model;
		this.model.addObserver(this);
		this.registerControllers();
        	setBackground(Color.LIGHT_GRAY);
    	}	

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		this.model.shape.draw(g2, true);
		this.model.fuelpad.draw(g2);
		this.model.pad.draw(g2);
	}

	private void registerControllers() {
		MouseInputListener m = new MyMouseController();
		addMouseListener(m);
		addMouseMotionListener(m);
	}

	
	private class MyMouseController extends MouseInputAdapter {
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {
				model.pad.setupold();
				model.updatepad(e.getX(), e.getY());
			}
		}
			
		public void mousePressed(MouseEvent e) {
			if (model.pad.inside(e.getX(), e.getY())) {
					model.pad.setupold();
                                        model.pad.selected = true;
                        }
			else if (model.fuelpad.inside(e.getX(), e.getY())) {
					model.fuelpad.setupold();
					model.fuelpad.selected = true;
			}
                        else {
                                model.shape.inside(e.getX(), e.getY());
                        }
		}

		public void mouseDragged(MouseEvent e) {
			if (model.pad.selected) {
				model.updatepad(e.getX(), e.getY());
			}
			else if (model.fuelpad.selected) {
				model.updatefuelpad(e.getX(), e.getY());
			}
			else if (model.shape.selected != -1) 
				 { if (dragged== false) {
				dragged = true;
				model.shape.oldy = (int)model.shape.points.get(model.shape.selected).getY();
                                };
				model.updateshape(e.getY());
			}
			else { }
		}

		public void mouseReleased(MouseEvent e) {
			if (model.pad.selected) {
				model.undopad(e.getX(), e.getY());
				model.pad.selected = false;
			}
			else if (model.fuelpad.selected) {
				model.undofuelpad(e.getX(), e.getY());
				model.fuelpad.selected = false;
			}
			else if (dragged) {
				model.undoshape(e.getY(), model.shape.selected);
				model.shape.oldselected = model.shape.selected;
				model.shape.selected = -1;
				dragged = false;
			}
			else { }
		}	
	}

    @Override
    public void update(Observable o, Object arg) {
	repaint();
    }

}
