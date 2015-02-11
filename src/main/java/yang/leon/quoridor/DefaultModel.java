package yang.leon.quoridor;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;

public class DefaultModel extends AbstractGameModel {

    /**
     * 
     */
    private static final long serialVersionUID = -3583850882738528510L;

    public static final int WIDTH = 9, HEIGHT = 9;

    public static final int NOT_ON_EDGE = -1, NORTH_EDGE = 0, EAST_EDGE = 1,
	    SOUTH_EDGE = 2, WEST_EDGE = 3;

    public static final int TOTAL_WALLS = 20;

    private transient IViewAdapter viewAdpt;

    private AbstractGameModel updateDelegate;

    private boolean[][] squares;
    private int[][] crossings;

    private Player[] players;

    private int currPlayerIndex;

    public DefaultModel(int numPlayers) {

	squares = new boolean[WIDTH][HEIGHT];
	crossings = new int[WIDTH - 1][HEIGHT - 1];

	players = new Player[numPlayers];
	for (int i = 0; i < numPlayers; i++) {
	    players[i] = new Player(numPlayers, i);
	    Location loc = players[i].getPawnLoc();
	    squares[loc.getRow()][loc.getCol()] = true;
	}

	currPlayerIndex = 0;

	setUpdateDelegate(createUpdateDelegate());
    }

    @Override
    public IViewAdapter getViewAdapter() {
	return viewAdpt;
    }

    @Override
    public void setViewAdapter(IViewAdapter viewAdpt) {
	this.viewAdpt = viewAdpt;
    }

    @Override
    public AbstractGameModel getUpdateDelegate() {
	return updateDelegate;
    }

    @Override
    public void setUpdateDelegate(AbstractGameModel updateDelegate) {
	this.updateDelegate = updateDelegate;
    }

    public ArrayList<Location> getCanMoveLocs(Location loc) {
	ArrayList<Location> visited = new ArrayList<Location>();
	visited.add(loc);
	return getCanMoveLocs(loc, visited);
    }

    private ArrayList<Location> getCanMoveLocs(Location visiting,
	    ArrayList<Location> visited) {
	ArrayList<Location> locs = new ArrayList<Location>();
	ArrayList<Location> adjLocs = getAdjUnblockLocs(visiting);
	Iterator<Location> itr = adjLocs.iterator();
	while (itr.hasNext()) {
	    Location loc = itr.next();
	    if (visited.contains(loc)) {
		itr.remove();
		continue;
	    }
	    if (squares[loc.getRow()][loc.getCol()]) {
		itr.remove();
		visited.add(visiting);
		locs.addAll(getCanMoveLocs(loc, visited));
	    }
	}
	adjLocs.removeAll(locs);
	locs.addAll(adjLocs);
	return locs;

    }

    private ArrayList<Location> getAdjUnblockLocs(Location visiting) {
	int row = visiting.getRow();
	int col = visiting.getCol();
	ArrayList<Location> locs = new ArrayList<Location>();

	// top location
	if (row > 0)
	    if ((col == 0 || crossings[row - 1][col - 1] != HORIZONTAL_WALL)
		    && (col == WIDTH - 1 || crossings[row - 1][col] != HORIZONTAL_WALL))
		locs.add(new Location(row - 1, col));

	// bottom location
	if (row < HEIGHT - 1)
	    if ((col == 0 || crossings[row][col - 1] != HORIZONTAL_WALL)
		    && (col == WIDTH - 1 || crossings[row][col] != HORIZONTAL_WALL))
		locs.add(new Location(row + 1, col));

	// left location
	if (col > 0)
	    if ((row == 0 || crossings[row - 1][col - 1] != VERTICAL_WALL)
		    && (row == HEIGHT - 1 || crossings[row][col - 1] != VERTICAL_WALL))
		locs.add(new Location(row, col - 1));

	// right location
	if (col < WIDTH - 1)
	    if ((row == 0 || crossings[row - 1][col] != VERTICAL_WALL)
		    && (row == HEIGHT - 1 || crossings[row][col] != VERTICAL_WALL))
		locs.add(new Location(row, col + 1));

	return locs;
    }

    public boolean isCanPutWall(Location loc, int direction) {
	int row = loc.getRow();
	int col = loc.getCol();
	if (row < 0 || row > HEIGHT - 2 || col < 0 || col > WIDTH - 2)
	    return false;
	if (crossings[row][col] != NO_WALL)
	    return false;
	if (direction == HORIZONTAL_WALL) {
	    if ((col > 0 && crossings[row][col - 1] == HORIZONTAL_WALL)
		    || (col < WIDTH - 2 && crossings[row][col + 1] == HORIZONTAL_WALL))
		return false;
	} else if (direction == VERTICAL_WALL) {
	    if ((row > 0 && crossings[row - 1][col] == VERTICAL_WALL)
		    || (row < HEIGHT - 2 && crossings[row + 1][col] == VERTICAL_WALL))
		return false;
	} else
	    return false;

	crossings[row][col] = direction;
	for (Player p : players) {
	    if (isCutOff(p)) {
		crossings[row][col] = NO_WALL;
		return false;
	    }
	}
	crossings[row][col] = NO_WALL;
	return true;
    }

    private boolean isCutOff(Player p) {
	ArrayList<Location> visited = new ArrayList<Location>();
	visited.add(p.getPawnLoc());
	return isCutOff(p.getTargetEdge(), p.getPawnLoc(), visited);
    }

    private boolean isCutOff(int targetEdge, Location visiting,
	    ArrayList<Location> visited) {
	if (isOnEdge(visiting, targetEdge))
	    return false;
	ArrayList<Location> adjLocs = getAdjUnblockLocs(visiting);
	adjLocs.removeAll(visited);
	visited.addAll(adjLocs);
	if (adjLocs.isEmpty())
	    return true;
	for (Location loc : adjLocs) {
	    if (!isCutOff(targetEdge, loc, visited))
		return false;
	}
	return true;
    }

    private boolean isOnEdge(Location loc, int edge) {
	if (edge == NORTH_EDGE && loc.getRow() == 0)
	    return true;
	if (edge == EAST_EDGE && loc.getCol() == WIDTH - 1)
	    return true;
	if (edge == SOUTH_EDGE && loc.getRow() == HEIGHT - 1)
	    return true;
	if (edge == WEST_EDGE && loc.getCol() == 0)
	    return true;
	return false;
    }

    public boolean movePawn(Location newLoc) {
	Player currPlayer = players[currPlayerIndex];
	Location oldLoc = currPlayer.getPawnLoc();
	squares[oldLoc.getRow()][oldLoc.getCol()] = false;
	squares[newLoc.getRow()][newLoc.getCol()] = true;
	currPlayer.setPawnLoc(newLoc);
	if (isOnEdge(currPlayer.getPawnLoc(), currPlayer.getTargetEdge())) {
	    setUpdateDelegate(createUpdateDelegate());
	    return true;
	}
	return false;
    }

    public void putWall(Location loc, int direction) {
	crossings[loc.getRow()][loc.getCol()] = direction;
	players[currPlayerIndex].setNumWalls(players[currPlayerIndex]
		.getNumWalls() - 1);
    }

    public Player getPlayer(int playerIndex) {
	return players[playerIndex];
    }

    public int getNumPlayers() {
	return players.length;
    }

    public void nextPlayer() {
	currPlayerIndex++;
	currPlayerIndex %= players.length;
	setUpdateDelegate(createUpdateDelegate());
    }

    public int getCurrPlayerIndex() {
	return currPlayerIndex;
    }

    public void update(Graphics g, AbstractGameView context) {
	getUpdateDelegate().update(g, context);
    }

    private AbstractGameModel createUpdateDelegate() {

	final ArrayList<Point> drawPawnPoints = new ArrayList<Point>();
	for (int r = 0; r < HEIGHT; r++) {
	    for (int c = 0; c < WIDTH; c++) {
		if (squares[r][c]) {
		    Point p = DefaultView
			    .getPointFromSqrLoc(new Location(r, c));
		    drawPawnPoints.add(p);
		}
	    }
	}

	final ArrayList<Object[]> drawWallParas = new ArrayList<Object[]>();
	for (int r = 0; r < HEIGHT - 1; r++) {
	    for (int c = 0; c < WIDTH - 1; c++) {
		if (crossings[r][c] != NO_WALL) {
		    Point p = DefaultView
			    .getPointFromCrsLoc(new Location(r, c));
		    drawWallParas.add(new Object[] { p, crossings[r][c] });
		}
	    }
	}

	Player player = getPlayer(getCurrPlayerIndex());
	String targetEdgeKey = null;
	final Point edgePoint = new Point();
	switch (player.getTargetEdge()) {
	case DefaultModel.NORTH_EDGE:
	    targetEdgeKey = "target.edge.horizontal";
	    edgePoint.setLocation(0, 0);
	    break;
	case DefaultModel.WEST_EDGE:
	    targetEdgeKey = "target.edge.vertical";
	    edgePoint.setLocation(0, 0);
	    break;
	case DefaultModel.SOUTH_EDGE:
	    targetEdgeKey = "target.edge.horizontal";
	    edgePoint.setLocation(0, 404);
	    break;
	case DefaultModel.EAST_EDGE:
	    targetEdgeKey = "target.edge.vertical";
	    edgePoint.setLocation(404, 0);
	    break;
	default:
	    break;
	}
	final String key = targetEdgeKey;

	Location pawnLoc = player.getPawnLoc();
	final Point pawnPoint = DefaultView.getPointFromSqrLoc(pawnLoc);

	return new DelegateModel() {
	    /**
	     * 
	     */
	    private static final long serialVersionUID = -8449782332509624181L;

	    public void update(Graphics g, AbstractGameView context) {
		context.drawBackground(g);

		for (Point p : drawPawnPoints)
		    context.drawPawn(g, p);

		for (Object[] o : drawWallParas) {
		    context.drawWall(g, (Point) o[0], (int) o[1], null);
		}

		g.drawImage(context.getImage(key), edgePoint.x, edgePoint.y,
			null);
		g.drawImage(context.getImage("current.pawn"), pawnPoint.x,
			pawnPoint.y, null);
	    }
	};

    }
}
