//A class to keep track of the threats to the king

public class KingsSquare extends Coordinate
{
	private boolean center, threatDanger, withinBoard;

	public KingsSquare(int x, int y, boolean withinBoard)
	{
		super(x, y);
		center = false;
		threatDanger = false;
		this.withinBoard = withinBoard;
	}

	// generates the square around the King
	public static KingsSquare[][] generateSquare(int x, int y)
	{
		KingsSquare[][] square = new KingsSquare[3][3];
		for (int i = 0; i < 3; i++)
			for (int ii = 0; ii < 3; ii++)
			{
				if (x + i - 1 >= 0 && x + i - 1 < Board.getCOL() && y + ii - 1 >= 0 && y + ii - 1 < Board.getROW())
					square[i][ii] = new KingsSquare(x + i - 1, y + ii - 1, true);
				else
					square[i][ii] = new KingsSquare(-1, -1, false);
			}

		square[1][1].center = true;
		return square;
	}

	public static void calculateThreat(KingsSquare[][] square, Board[][] board, Piece[][] piece, boolean side)
	{
		int i;
		if (side)
			i = 1;
		else
			i = 0;

		for (int ii = 0; ii < piece[0].length; ii++)
			if (piece[i][ii].getExist())
				piece[i][ii].kingDanger(board, piece, square);
	}

	// checks if a given x,y on the board is within the kingsSquare
	public static void squareCheck(int x, int y, KingsSquare[][] square)
	{
		for (int i = 0; i < square.length; i++)
			for (int ii = 0; ii < square[0].length; ii++)
			{
				if (square[i][ii].getWithinBoard())
					if (x == square[i][ii].getX() && y == square[i][ii].getY() && !square[i][ii].center)
						square[i][ii].threatDanger = true;
			}
	}

	public static void reset(KingsSquare[][] square)
	{
		for (int i = 0; i < square.length; i++)
			for (int ii = 0; ii < square[0].length; ii++)
				square[i][ii].threatDanger = false;
	}

	public void setThreatDanger(boolean dangerous)
	{
		threatDanger = dangerous;
	}

	public boolean getThreatDanger()
	{
		return threatDanger;
	}

	public boolean getWithinBoard()
	{
		return withinBoard;
	}
}
