package yang.leon.quoridor;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import yang.leon.quoridor.state.InitState;
import yang.leon.quoridor.state.ViewState;

public class QuoridorView extends JFrame {
    public static final int GRID_WIDTH = 450, GRID_HEIGHT = 450;

    private ModelControlAdapter modelCtrlAdpt;
    private ModelUpdateAdapter modelUpdateAdpt;

    private JPanel gridPanel;
    private JPanel playerPanel;

    private ViewState state;

    public QuoridorView(ModelControlAdapter modelControlAdapter,
	    ModelUpdateAdapter modelUpdateAdapter) {
	modelCtrlAdpt = modelControlAdapter;
	modelUpdateAdpt = modelUpdateAdapter;
	SwingUtilities.invokeLater(new Runnable() {
	    public void run() {
		initGUI();
	    }
	});
    }

    public void initGUI() {
	gridPanel = new JPanel();
	playerPanel = new JPanel();

	state = new InitState(0);

	gridPanel.setPreferredSize(new Dimension(GRID_WIDTH, GRID_HEIGHT));
	gridPanel.addMouseListener(new MouseAdapter() {
	    public void mouseClicked(MouseEvent e) {
		state.mouseClicked(e, QuoridorView.this);
	    }
	});
	playerPanel.setPreferredSize(new Dimension(GRID_WIDTH, 100));

	Container contentPane = this.getContentPane();
	contentPane.setLayout(new BorderLayout());
	contentPane.add(gridPanel, BorderLayout.CENTER);
	contentPane.add(playerPanel, BorderLayout.SOUTH);

	this.setTitle("Quoridor");
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setResizable(false);
	this.pack();
	this.setLocationRelativeTo(null);
	this.setVisible(true);
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

    public void setState(ViewState aState) {
	this.state = aState;
    }

    public ModelControlAdapter getModelCtrlAdpt() {
	return modelCtrlAdpt;
    }

    public ModelUpdateAdapter getModelUpdateAdp() {
	return modelUpdateAdpt;
    }

    public void update() {

    }

}
