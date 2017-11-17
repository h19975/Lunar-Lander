import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class MessageView extends JPanel implements Observer {
	private GameModel model;
    // status messages for game
    JLabel fuel = new JLabel("fuel");
    JLabel speed = new JLabel("speed");
    JLabel message = new JLabel("message");

    public MessageView(GameModel model) {
	this.model = model;
	model.addObserver(this);	
        setBackground(Color.BLACK);

        setLayout(new FlowLayout(FlowLayout.LEFT));

        add(fuel);
        add(speed);
        add(message);
	fuel.setText("Fuel: "+Integer.toString((int)model.ship.getFuel()));
	speed.setText("Speed: "+Double.toString(model.ship.getSpeed()));
	message.setText("");

        for (Component c: this.getComponents()) {
            c.setForeground(Color.WHITE);
            c.setPreferredSize(new Dimension(100, 20));
        }
    }


    @Override
    public void update(Observable o, Object arg) {
	if (model.ship.getFuel() < 10) {
		fuel.setForeground(Color.RED);
	} else {fuel.setForeground(Color.WHITE);}
	fuel.setText("Fuel: "+ Integer.toString((int)model.ship.getFuel()));
	if (model.ship.getSpeed() < model.ship.getSafeLandingSpeed()) {
		speed.setForeground(Color.GREEN); }
	else {speed.setForeground(Color.RED);}
	speed.setText("Speed: "+Double.toString(model.ship.getSpeed()));
	if (model.getCrashed()) {message.setText("CRASHED!");}
	else if (model.ship.landed) {message.setText("LANDED!");}
	else if (model.ship.isPaused()) {message.setText("PAUSED!");}
	else {message.setText("");};
    }
}
