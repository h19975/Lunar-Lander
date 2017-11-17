import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;
import javax.swing.event.*;

// the edit toolbar
public class ToolBarView extends JPanel implements Observer {
	
	private	GameModel model;
    private JButton undo = new JButton("Undo");
    private JButton redo = new JButton("Redo");

    public ToolBarView(GameModel model) {
	
	super();
        setLayout(new FlowLayout(FlowLayout.LEFT));
        this.model = model;
	model.addObserver(this);
	this.registerControllers();
        undo.setFocusable(false);
        redo.setFocusable(false);
	undo.setEnabled(false);
	redo.setEnabled(false);
        add(undo);
        add(redo);
    }

	private void registerControllers() {
		this.undo.addActionListener(new undoController());
		this.redo.addActionListener(new redoController());
	}

	private class undoController implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			model.undo();
		}
	}

	private class redoController implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			model.redo();
		}
	}
		

    @Override
    public void update(Observable o, Object arg) {
	if (model.canUndo()) {
		undo.setEnabled(true);
	} else { undo.setEnabled(false); };
	if (model.canRedo()) {
		redo.setEnabled(true);
	} else {redo.setEnabled(false);};
    }
}
