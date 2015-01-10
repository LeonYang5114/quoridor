package yang.leon.quoridor;

import java.awt.BorderLayout;
import java.awt.Container;
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
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import yang.leon.quoridor.state.HoldingWallState;
import yang.leon.quoridor.state.InitState;
import yang.leon.quoridor.state.MovingPawnState;
import yang.leon.quoridor.state.ViewState;
import yang.leon.quoridor.state.WonState;

public class QuoridorView extends JFrame {
    public static final int GRID_WIDTH = 450, GRID_HEIGHT = 450;

    private ModelControlAdapter modelCtrlAdpt;
    private ModelUpdateAdapter modelUpdateAdpt;

    private JPanel pnl_grid;
    private JPanel pnl_player;

    private JButton btn_wall;
    private JButton btn_pawn;

    private ViewState viewState;

    private Map<String, Image> images;

    public QuoridorView(ModelControlAdapter modelControlAdapter,
	    ModelUpdateAdapter modelUpdateAdapter) {
	modelCtrlAdpt = modelControlAdapter;
	modelUpdateAdpt = modelUpdateAdapter;
	images = new HashMap<String, Image>();
	loadImages();
	SwingUtilities.invokeLater(new Runnable() {
	    public void run() {
		initGUI();
	    }
	});
    }

    private void loadImages() {
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

    public void initGUI() {
	pnl_grid = new JPanel() {
	    public void paintComponent(Graphics g) {
		super.paintComponent(g);
		modelUpdateAdpt.update(g);
		viewState.update(g);
	    }
	};
	pnl_player = new JPanel();

	viewState = new InitState(this);

	pnl_grid.setPreferredSize(new Dimension(GRID_WIDTH, GRID_HEIGHT));
	pnl_grid.addMouseListener(new MouseAdapter() {
	    
	    public void mousePressed(MouseEvent e) {
		viewState.mousePressed(e);
	    }
	});
	pnl_grid.addMouseMotionListener(new MouseAdapter() {

	    public void mouseMoved(MouseEvent e) {
		viewState.mouseMoved(e);
	    }
	});

	btn_wall = new JButton("Walls");
	btn_wall.setPreferredSize(new Dimension(90, 60));
	btn_wall.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		if (viewState instanceof HoldingWallState)
		    return;
		setViewState(new HoldingWallState(QuoridorView.this));
	    }
	});

	btn_pawn = new JButton("Pawn");
	btn_pawn.setPreferredSize(new Dimension(90, 60));
	btn_pawn.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		if (viewState instanceof MovingPawnState)
		    return;
		setViewState(new MovingPawnState(QuoridorView.this));
		update();
	    }
	});

	JButton btn_test = new JButton("Test");
	btn_test.setPreferredSize(new Dimension(90, 60));
	btn_test.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		System.out.println(viewState);
	    }

	});

	pnl_player.setPreferredSize(new Dimension(GRID_WIDTH, 100));
	pnl_player.add(btn_wall);
	pnl_player.add(btn_pawn);
	pnl_player.add(btn_test);

	Container contentPane = this.getContentPane();
	contentPane.setLayout(new BorderLayout());
	contentPane.add(pnl_grid, BorderLayout.CENTER);
	contentPane.add(pnl_player, BorderLayout.SOUTH);

	this.setTitle("Quoridor");
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setResizable(false);
	this.pack();
	this.setLocationRelativeTo(null);
	this.setVisible(true);
	update();
    }

    public static Location getSqrLocAtPoint(Point point) {
	int squareHeight = GRID_HEIGHT / QuoridorModel.HEIGHT;
	int squareWidth = GRID_WIDTH / QuoridorModel.WIDTH;
	return new Location(point.y / squareHeight, point.x / squareWidth);
    }

    public static Point getPointFromSqrLoc(Location loc) {
	int squareHeight = GRID_HEIGHT / QuoridorModel.HEIGHT;
	int squareWidth = GRID_WIDTH / QuoridorModel.WIDTH;
	return new Point(loc.getCol() * squareWidth, loc.getRow()
		* squareHeight);
    }

    public static Location getCrsLocAtPoint(Point point) {
	int squareHeight = GRID_HEIGHT / QuoridorModel.HEIGHT;
	int squareWidth = GRID_WIDTH / QuoridorModel.WIDTH;
	int row = (point.y - squareHeight / 2) / squareHeight;
	row = Math.max(Math.min(row, 7), 0);
	int col = (point.x - squareWidth / 2) / squareWidth;
	col = Math.max(Math.min(col, 7), 0);
	return new Location(row, col);
    }

    public static Point getPointFromCrsLoc(Location loc) {
	int squareHeight = GRID_HEIGHT / QuoridorModel.HEIGHT;
	int squareWidth = GRID_WIDTH / QuoridorModel.WIDTH;
	return new Point((loc.getCol() + 1) * squareWidth, (loc.getRow() + 1)
		* squareHeight);
    }

    public JPanel getGridPanel() {
	return pnl_grid;
    }

    public ViewState getViewState() {
	return viewState;
    }

    public void setViewState(ViewState aState) {
	this.viewState = aState;
	update();
    }

    public ModelControlAdapter getModelCtrlAdpt() {
	return modelCtrlAdpt;
    }

    public ModelUpdateAdapter getModelUpdateAdp() {
	return modelUpdateAdpt;
    }

    public void update() {
	repaint();
    }

    public Image getImage(String key) {
	return images.get(key);
    }

    public void drawWall(Graphics g, Point p, int direction, String arg) {
	Image wall = null;
	if (direction == QuoridorModel.HORIZONTAL_WALL) {
	    if (arg != null && arg.equals("light"))
		wall = getImage("wall.horizontal.light");
	    else
		wall = getImage("wall.horizontal");
	} else if (direction == QuoridorModel.VERTICAL_WALL) {
	    if (arg != null && arg.equals("light"))
		wall = getImage("wall.vertical.light");
	    else
		wall = getImage("wall.vertical");
	}
	if (wall != null)
	    g.drawImage(wall, p.x - wall.getWidth(null) / 2,
		    p.y - wall.getHeight(null) / 2, null);
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
