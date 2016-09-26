import java.awt.Color;

// a square in a chess board
public class Board
{
	final static private int WIDTH = 70;
	final static private int LENGTH = 70;
	final static private int COL = 8;
	final static private int ROW = 8;
	private static boolean tempCounter;
	private boolean isWhite, blockable;
	private int pieceSide, piece, col, row, type;

	public Board(int col, int row, boolean white)
	{
		this.col = col;
		this.row = row;
		isWhite = tempCounter;
		type = -1;
		piece = -1;
	}
	
	public Board(int col, int row, boolean white, int piece)
	{
		
	}

	public static Board[][] generateBoard()
	{
		Board[][] board = new Board[COL][ROW];
		tempCounter = true;
		for (int col = 0; col < COL; col++)

		{
			for (int row = 0; row < ROW; row++)
			{

				board[col][row] = new Board(col, row, tempCounter);
				tempCounter = !tempCounter;
			}
			tempCounter = !tempCounter;
		}
		return board;
	}
	
	public void setBlockable(boolean blockable)
	{
		this.blockable = blockable;
	}
	
	public boolean getBlockable()
	{
		return blockable;
	}

	public boolean isWhite()
	{
		return isWhite;
	}
	
	public int getType()
	{
		return type;
	}
	
	public void setType(int type)
	{
		this.type = type;
	}

	public void setPiece(int pieceSide, int piece)
	{
		this.pieceSide = pieceSide;
		this.piece = piece;
	}

	public int[] getPiece()
	{
		int[] a = new int[2];
		a[0] = pieceSide; 
		a[1] = piece;
		return a;
	}

	public static int getWIDTH()
	{
		return WIDTH;
	}

	public static int getLENGTH()
	{
		return LENGTH;
	}

	public static int getCOL()
	{
		return COL;
	}

	public static int getROW()
	{
		return ROW;
	}
	
	public static void resetBoard(Board[][] board)
	{
		for (int col = 0; col < board.length; col++)
			for (int row = 0; row < board[0].length; row++)
			{
				board[col][row].type = -1;
			}
	}
}
