import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;
import java.awt.geom.*;

// the actual game view
public class PlayView extends JPanel implements Observer {
	
	private	GameModel model; 
    
	public PlayView(GameModel model) {
		super();
		setFocusable(true);
		this.model = model;
		model.addObserver(this);
		this.registerControllers();
		this.setBackground(Color.LIGHT_GRAY);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		AffineTransform M = g2.getTransform();
		g2.translate(-350*2, -50*2+100);
                g2.scale(3,3);
		g2.translate(350-(model.ship.getPosition().x), 50-(model.ship.getPosition().y));	
		model.shape.draw(g2, false);
		model.fuelpad.draw(g2);
		model.pad.draw(g2);
		g2.setColor(Color.BLUE);
		model.ship.draw(g2);
		g2.setTransform(M);
	}

	private void registerControllers() {
		this.addKeyListener(new keyController());
	}
	
	private class keyController extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
				case KeyEvent.VK_W:
					if (!model.ship.isPaused()) {	
						model.ship.thrustUp(); }
					break;
				case KeyEvent.VK_A:
					if (!model.ship.isPaused()) {
					model.ship.thrustLeft();} 
					break;
				case KeyEvent.VK_S:
					if (!model.ship.isPaused()) {
					model.ship.thrustDown();}
					break;
				case KeyEvent.VK_D:
					if (!model.ship.isPaused()) {
					model.ship.thrustRight(); }
					break;
				case KeyEvent.VK_SPACE:
					if (model.getCrashed()) {
						model.ship.reset(model.ship.startPosition);
						model.setCrashed(false);
						model.fuelpad.reset();
					}
					else if (model.ship.landed) {
						model.ship.reset(model.ship.startPosition);
						model.fuelpad.reset();
					}
					else {
						model.ship.setPaused(!model.ship.isPaused());
					}
					break;
			}
		}
	}

    @Override
    public void update(Observable o, Object arg) {
		repaint();
    }
}
