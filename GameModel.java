import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.undo.*;
import javax.vecmath.*;
import javax.swing.undo.*;

public class GameModel extends Observable {

	private UndoManager undoM;
	private boolean crashed;	

    public GameModel(int fps, int width, int height, int peaks) {
	undoM = new UndoManager();
        ship = new Ship(60, width/2, 50);
	pad = new LandingPad(330+20, 100+5);
	fuelpad = new FuelPad(500, 100);
	shape = new Shape();
	crashed = false;
        worldBounds = new Rectangle2D.Double(0, 0, width, height);

        // anonymous class to monitor ship updates
        ship.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
		if (shape.getShape().intersects(ship.getShape()) ||
                        !worldBounds.contains(ship.getShape())) 
			{crashed = true; ship.crashed = true;ship.landed = false; }
		else if (pad.getShape().intersects(ship.getShape())) { 
			if (ship.getSpeed() < ship.getSafeLandingSpeed()) {
				crashed = false; ship.crashed = false; ship.landed = true;} 
			else {crashed = true; ship.crashed = true; ship.landed = false;} }
		else if (fuelpad.getShape().intersects(ship.getShape()) && !fuelpad.used) {
			fuelpad.used = true; addFuel(); }
		else {ship.crashed = false; crashed = false; ship.landed = false;}
		updateViews();

            }
        });
    }

	public boolean getCrashed() {
		return crashed;
	}

	public void setCrashed(boolean c) {
		crashed = c;
	}

    // World
    // - - - - - - - - - - -
    public final Rectangle2D getWorldBounds() {
        return worldBounds;
    }

    Rectangle2D.Double worldBounds;


    // Ship
    // - - - - - - - - - - -

    public Ship ship;

    // landingPad
	public LandingPad pad;
	
	//terrain
	public Shape shape;

	//fuel pod
	public FuelPad fuelpad;
    // Observerable
    // - - - - - - - - - - -

    // helper function to do both
	public void undopad(int x, int y) {
		UndoableEdit undoableEdit = new AbstractUndoableEdit() {
                        final int oldx = pad.oldx;
                        final int oldy = pad.oldy;
                        final int newx= x;
                        final int newy = y;

                        public void redo() throws CannotRedoException {
                                super.redo();
                                pad.x = newx;
                                pad.y = newy;
                                updateViews();
                        }

                        public void undo() throws CannotUndoException {
                                super.undo();
                                pad.x = oldx;
                                pad.y = oldy;
                                updateViews();
                        }
                };

                        undoM.addEdit(undoableEdit);
			updateViews();
	}

	public void updatepad(int x, int y) {

			if (x < 700-20 && x > 0+20) {
				this.pad.x = x;
			};
			if (y < 200-5 && y > 0+5) {
				this.pad.y = y;
			};

			updateViews();
	}	

	public void undofuelpad(int x, int y) {
		UndoableEdit undoableEdit = new AbstractUndoableEdit() {
                        final int oldx = fuelpad.oldx;
                        final int oldy = fuelpad.oldy;
                        final int newx= x;
                        final int newy = y;

                        public void redo() throws CannotRedoException {
                                super.redo();
                                fuelpad.x = newx;
                                fuelpad.y = newy;
                                updateViews();
                        }

                        public void undo() throws CannotUndoException {
                                super.undo();
                                fuelpad.x = oldx;
                                fuelpad.y = oldy;
                                updateViews();
                        }
                };

                        undoM.addEdit(undoableEdit);
                        updateViews();
        }
	

	public void updatefuelpad(int x, int y) {

                        if (x < 700-20 && x > 0+20) {
                                this.fuelpad.x = x;
                        };
                        if (y < 200-5 && y > 0+5) {
                                this.fuelpad.y = y;
                        };

                        updateViews();
        }

	public void undoshape(int y, int selected) {
		UndoableEdit undoableEdit = new AbstractUndoableEdit() {
			final int oldy = shape.oldy;
			final int newy = y;
			final int newi = selected;

			public void redo() throws CannotRedoException {
				super.redo();
				shape.selected = -1;
				shape.points.get(newi).setLocation(shape.points.get(newi).getX(), (double)newy);
				updateViews();
			}

			public void undo() throws CannotUndoException {
				super.undo();
				shape.selected = -1;
				shape.points.get(newi).setLocation(shape.points.get(newi).getX(), (double)oldy);
				updateViews();
			}
		};
		undoM.addEdit(undoableEdit);
		updateViews();
	}

	
	/*private void crash() {
		if (shape.getShape().intersects(ship.getShape()) || 
			!worldBounds.contains(ship.getShape())) {crashed = true;}
		System.out.println(crashed);
	}	*/
		
	public void updateshape(int y) {
                if (shape.selected != -1 && y < 200 && y > 0) {
                        shape.points.get(shape.selected).setLocation(shape.points.get(shape.selected).getX(), (double)y);
                        shape.pointsChanged = true;
                }
		updateViews();
        }

	private void addFuel() {
		ship.setFuel((int)ship.getFuel()+20);
	}		
	
	public void updateViews() {
		setChanged();
		notifyObservers();
	}

	public void undo() {
		if (canUndo()) {
			undoM.undo();
			updateViews();
		}
	}

	public void redo() {
		if (canRedo()) {
		undoM.redo();
		updateViews();
		}
	}

	public boolean canUndo() {
		return undoM.canUndo();
	}

	public boolean canRedo() {
		return undoM.canRedo();
	}

}



