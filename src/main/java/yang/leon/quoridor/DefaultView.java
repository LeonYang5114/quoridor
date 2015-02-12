package yang.leon.quoridor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import yang.leon.quoridor.state.HoldingWallState;
import yang.leon.quoridor.state.AbstractViewState;
import yang.leon.quoridor.state.MovingPawnState;
import yang.leon.quoridor.state.WaitingState;
import yang.leon.quoridor.state.WonState;

public class DefaultView extends AbstractGameView {

    /**
     * 
     */
    private static final long serialVersionUID = 6783231460747770839L;

    private IModelAdapter modelAdpt;

    /**
     * Grid panel. Any <code>MouseEvent</code> is passed to and processed by the
     * current view state.
     */
    private JPanel pnl_grid;

    /**
     * Function panel, contains the buttons.
     */
    private JPanel pnl_func;

    /**
     * Button to switch to the {@link yang.leon.quoridor.state.HoldingWallState
     * HoldingWallState}.
     */
    private JButton btn_wall;

    /**
     * Label that shows the number of walls left of the current player.
     */
    private JLabel lb_numWalls;

    /**
     * Button to switch to the {@link yang.leon.quoridor.state.MovingPawnState
     * MovingPawnState}. Same effect as clicking the pawn of the current player
     * at {@link yang.leon.quoridor.state.InitialState InitialState}.
     */
    private JButton btn_pawn;

    /**
     * Stores the image resources of the game.
     */
    private transient Map<String, Image> images;

    /**
     * Indicates whether the GUI is being reset to avoid unwanted outcome of
     * multiple attempts calling {@link #resetGUI() resetGUI} at the same time.
     */
    private boolean isResettingGUI;

    private AbstractViewState viewState;

    private AbstractGameModel updateDelegate;

    /**
     * Constructs a <code>DefaultView</code> with no game controller. Image
     * resources are loaded.
     */
    public DefaultView() {
	loadImages();
    }

    /**
     * Constructs a <code>DefaultView</code> with the given game controller.
     * Image resources are loaded.
     * 
     * @param controller
     *            the game controller to be used
     */
    public DefaultView(AbstractGameController controller) {
	loadImages();
	controller.registerView(this);
    }

    @Override
    public void setModelAdapter(IModelAdapter modelAdpt) {
	this.modelAdpt = modelAdpt;
	if (modelAdpt != null) {
	    setUpdateDelegate(modelAdpt.getUpdateDelegate());
	    resetGUI();
	}
    }

    @Override
    public IModelAdapter getModelAdapter() {
	return modelAdpt;
    }

    /**
     * Loads the image resources into a <code>Map</code> using a properties
     * file.
     */
    void loadImages() {
	images = new HashMap<String, Image>();
	Properties prop = new Properties();
	String propertiesFileName = "images/images.properties";
	InputStream is = getClass().getClassLoader().getResourceAsStream(
		propertiesFileName);
	if (is != null) {
	    try {
		prop.load(is);
	    } catch (IOException ext) {
		ext.printStackTrace();
		System.exit(1);
	    }
	}
	Enumeration<Object> keys = prop.keys();
	BufferedImage img = null;
	try {
	    while (keys.hasMoreElements()) {
		Object nextKey = keys.nextElement();
		Object nextValue = prop.get(nextKey);
		is = getClass().getClassLoader().getResourceAsStream(
			(String) nextValue);
		img = ImageIO.read(is);
		images.put((String) nextKey, img);
	    }
	    is.close();
	} catch (IOException ext) {
	    ext.printStackTrace();
	    System.exit(1);
	}
    }

    @Override
    public Image getImage(String key) {
	return images.get(key);
    }

    @Override
    public AbstractViewState getViewState() {
	return viewState;
    }

    @Override
    public void setViewState(String stateName) {
	String name = "yang.leon.quoridor.state." + stateName;
	try {
	    this.viewState = (AbstractViewState) Class.forName(name)
		    .getConstructor(AbstractGameView.class).newInstance(this);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    /**
     * Initializes the grid panel, whose <code>paintComponent</code> method is
     * overridden so that it repaints itself using the update delegate and the
     * view state.
     * 
     * @return
     */
    JPanel initGridPanel() {
	return new JPanel() {
	    /**
	     * 
	     */
	    private static final long serialVersionUID = -1994204987640877393L;

	    @Override
	    public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (getUpdateDelegate() == null)
		    return;
		getUpdateDelegate().update(g, DefaultView.this);
		getViewState().update(g);
	    }
	};
    }

    @Override
    public void resetGUI() {
	if (isResettingGUI)
	    return;
	isResettingGUI = true;
	this.removeAll();
	pnl_grid = initGridPanel();
	pnl_func = new JPanel();

	pnl_grid.setPreferredSize(new Dimension(GRID_WIDTH, GRID_HEIGHT));
	pnl_grid.addMouseListener(new MouseAdapter() {

	    @Override
	    public void mousePressed(MouseEvent e) {
		getViewState().mousePressed(e);
	    }
	});
	pnl_grid.addMouseMotionListener(new MouseAdapter() {

	    @Override
	    public void mouseMoved(MouseEvent e) {
		getViewState().mouseMoved(e);
	    }
	});

	btn_wall = new JButton("Walls");
	btn_wall.setPreferredSize(new Dimension(90, 60));
	btn_wall.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		if (getViewState() instanceof HoldingWallState)
		    return;
		setViewState("HoldingWallState");
	    }
	});

	lb_numWalls = new JLabel();

	btn_pawn = new JButton("Pawn");
	btn_pawn.setPreferredSize(new Dimension(90, 60));
	btn_pawn.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		if (getViewState() instanceof MovingPawnState)
		    return;
		setViewState("MovingPawnState");
		update();
	    }
	});

	JButton btn_test = new JButton("Test");
	btn_test.setPreferredSize(new Dimension(90, 60));
	btn_test.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		System.out.println(getViewState());
	    }

	});

	pnl_func.setPreferredSize(new Dimension(GRID_WIDTH, 100));
	pnl_func.add(btn_wall);
	pnl_func.add(lb_numWalls);
	pnl_func.add(btn_pawn);
	pnl_func.add(btn_test);

	setLayout(new BorderLayout());
	add(pnl_grid, BorderLayout.CENTER);
	add(pnl_func, BorderLayout.SOUTH);
	setPreferredSize(getPreferredSize());
	setViewState("WaitingState");
	isResettingGUI = false;
    }

    @Override
    public JPanel getGridPanel() {
	return pnl_grid;
    }

    /**
     * Gets the current update delegate which contains the necessary for drawing
     * the game state.
     * 
     * @return the current update delegate
     */
    public AbstractGameModel getUpdateDelegate() {
	return updateDelegate;
    }

    /**
     * Sets the update delegate as the given delegate.
     * 
     * @param delegate the update delegate to be used
     */
    public void setUpdateDelegate(AbstractGameModel delegate) {
	updateDelegate = delegate;
    }

    @Override
    public void update() {
	IModelAdapter modelAdpt = getModelAdapter();
	if (modelAdpt == null || modelAdpt.getUpdateDelegate() == null)
	    return;
	setUpdateDelegate(modelAdpt.getUpdateDelegate());
	lb_numWalls.setText("X "
		+ modelAdpt.getPlayer(modelAdpt.getCurrPlayerIndex())
			.getNumWalls());
	repaint();
    }

    @Override
    public void drawBackground(Graphics g) {
	g.drawImage(getImage("background"), 0, 0, null);
    }

    @Override
    public void drawWall(Graphics g, Point p, int direction, String arg) {
	Image wall = null;
	if (direction == DefaultModel.HORIZONTAL_WALL) {
	    if (arg != null && arg.equals("light"))
		wall = getImage("wall.horizontal.light");
	    else
		wall = getImage("wall.horizontal");
	} else if (direction == DefaultModel.VERTICAL_WALL) {
	    if (arg != null && arg.equals("light"))
		wall = getImage("wall.vertical.light");
	    else
		wall = getImage("wall.vertical");
	}
	if (wall != null)
	    g.drawImage(wall, p.x - wall.getWidth(null) / 2,
		    p.y - wall.getHeight(null) / 2, null);
    }

    @Override
    public void drawPawn(Graphics g, Point p) {
	g.drawImage(getImage("pawn"), p.x, p.y, null);
    }

    @Override
    public void win(int winnerIndex) {
	setViewState("WonState");
	JOptionPane.showMessageDialog(this, "Congratulations! Player "
		+ winnerIndex + " is the winner!");
    }

    @Override
    public void disableButtons() {
	btn_wall.setEnabled(false);
	btn_pawn.setEnabled(false);
    }

    @Override
    public void enableButtons() {
	btn_wall.setEnabled(true);
	btn_pawn.setEnabled(true);
    }
}
