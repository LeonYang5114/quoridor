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
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import yang.leon.quoridor.state.HoldingWallState;
import yang.leon.quoridor.state.InitState;
import yang.leon.quoridor.state.MovingPawnState;
import yang.leon.quoridor.state.WonState;

public class DefaultView extends AbstractView {

    private JPanel pnl_grid;
    private JPanel pnl_func;

    private JButton btn_wall;
    private JLabel lb_numWalls;
    private JButton btn_pawn;

    private transient Map<String, Image> images;

    private boolean isResettingGUI;

    public DefaultView() {
	this(null);
    }

    public DefaultView(IModelAdapter modelAdpt) {
	images = new HashMap<String, Image>();
	loadImages();
	try {
	    modelAdpt.registerView(this);
	} catch (RemoteException e) {
	    e.printStackTrace();
	}
    }

    protected void loadImages() {
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

    public Image getImage(String key) {
	return images.get(key);
    }

    public void resetGUI() {
	if (isResettingGUI)
	    return;
	isResettingGUI = true;
	this.removeAll();
	pnl_grid = new JPanel() {
	    public void paintComponent(Graphics g) {
		super.paintComponent(g);
		try {
		    getModelAdapter().update(g, DefaultView.this);
		} catch (RemoteException e) {
		    e.printStackTrace();
		}
		getViewState().update(g);
	    }
	};
	pnl_func = new JPanel();

	pnl_grid.setPreferredSize(new Dimension(GRID_WIDTH, GRID_HEIGHT));
	pnl_grid.addMouseListener(new MouseAdapter() {

	    public void mousePressed(MouseEvent e) {
		getViewState().mousePressed(e);
	    }
	});
	pnl_grid.addMouseMotionListener(new MouseAdapter() {

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
		setViewState(new HoldingWallState(DefaultView.this));
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
		setViewState(new MovingPawnState(DefaultView.this));
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
	setViewState(new InitState(this));
	isResettingGUI = false;
    }

    public JPanel getGridPanel() {
	return pnl_grid;
    }

    public void update() {
	IModelAdapter modelAdpt = getModelAdapter();
	if (modelAdpt == null)
	    return;
	try {
	    lb_numWalls.setText("X "
		    + modelAdpt.getPlayer(modelAdpt.getCurrPlayerIndex())
			    .getNumWalls());
	} catch (RemoteException e) {
	    e.printStackTrace();
	}
	repaint();
    }

    public void drawBackground(Graphics g) {
	g.drawImage(getImage("background"), 0, 0, null);
    }

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

    public void drawPawn(Graphics g, Point p) {
	g.drawImage(getImage("pawn"), p.x, p.y, null);
    }

    public void win(int currPlayerIndex) {
	setViewState(new WonState(this));
	JOptionPane.showMessageDialog(this, "Congratulations! Player "
		+ currPlayerIndex + " is the winner!");
    }

    public void disableButtons() {
	btn_wall.setEnabled(false);
	btn_pawn.setEnabled(false);
    }
}