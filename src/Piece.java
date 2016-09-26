import java.util.ArrayList;
import java.util.Collection;

public class Piece extends Coordinate
{

	protected boolean side, exist, immediateThreat;
	protected char name;
	protected int blockingPiece, id;

	//
	public Piece(boolean side, char name)
	{
		super();
		this.side = side;
		this.name = name;
		exist = true;
		blockingPiece = -1;
	}

	public char getName()
	{
		return name;
	}

	// a mini method for the whole potential of a piece (-1 = out of bounds, 0 =
	// friendly, 1 = enemy, 2 = movable)
	public int potential(int x, int y, Board[][] board, Piece[][] piece)
	{
		if (x >= 0 && x < Board.getCOL() && y >= 0 && y < Board.getROW())
		{
			if (blockingPiece == -1 || Coordinate.aLine(new Coordinate(this.x, this.y), new Coordinate(x, y),
					new Coordinate(piece[(!side) ? 1 : 0][blockingPiece].x, piece[(!side) ? 1 : 0][blockingPiece].y)))
			{

				int[] a = board[x][y].getPiece();
				if (a[1] >= 0)
				{
					if (piece[a[0]][a[1]].getSide() == getSide())
						return 0;
					else if (piece[a[0]][a[1]].getName() == 'K')
						return -2;
					else
						return 1;
				}
				return 2;
			}
			return -1;
		}
		return -1;
	}

	// returns a set of Coordinates that a piece can move to
	public Coordinate[] move(Board[][] board, Piece[][] piece, boolean blockCheck)
	{
		return new Coordinate[0];
	}

	// recurrsion method to help piece move in a linear fashinon
	protected ArrayList<Coordinate> moveAssist(int x, int y, int xMove, int yMove, Board[][] board, Piece[][] piece,
			boolean blockCheck)
	{
		ArrayList<Coordinate> set = new ArrayList<Coordinate>(0);
		short temp = (short) potential(x + xMove, y + yMove, board, piece);

		if (temp == 2)
		{
			if (!blockCheck || blockCheck && board[x + xMove][y + yMove].getBlockable())
				set.add(new Coordinate(x + xMove, y + yMove));
			set.addAll(moveAssist(x + xMove, y + yMove, xMove, yMove, board, piece, blockCheck));
		}
		else if (temp == 1 && (!blockCheck || blockCheck && board[x + xMove][y + yMove].getBlockable()))
			set.add(new Coordinate(x + xMove, y + yMove));

		return set;
	}

	// runs at the end of turn to check where pieces can go
	public void kingDanger(Board[][] board, Piece[][] piece, KingsSquare[][] square)
	{
	}

	// If the king needs to move, checks if this piece endangers enemy piece
	// (Rook,Bishop, Queen)
	protected void kingDangerAssist(int x, int y, int xMove, int yMove, Board[][] board, Piece[][] piece,
			KingsSquare[][] square)
	{
		short temp = (short) potential(x + xMove, y + yMove, board, piece);
		if (temp == -2)
		{
			immediateThreat = true;
			((King) piece[(!side) ? 1 : 0][4]).increaseThreatCounter();

			// so the king cant move parallel to attack direction to get away
			if (x + xMove >= 0 && x + xMove < Board.getCOL() && y >= 0 && y < Board.getROW())
				square[1 + xMove][1 + yMove].setThreatDanger(true);

			// the path to the piece to the king is blockable by another piece
			blockablePath(x + xMove, y + yMove, xMove, yMove, board);
		}
		else if (temp == 2)
		{
			KingsSquare.squareCheck(x + xMove, y + yMove, square);
			kingDangerAssist(x + xMove, y + yMove, xMove, yMove, board, piece, square);
		}
		else if (temp == 1)
		{
			if (imaginaryCheck(x + xMove, y + yMove, xMove, yMove, board, piece))
			{
				int[] a = board[x + xMove][y + yMove].getPiece();
				piece[a[0]][a[1]].blockingPiece = board[this.x][this.y].getPiece()[1];
			}
		}
	}

	private void blockablePath(int kingX, int kingY, int xMove, int yMove, Board[][] board)
	{
		int temp = Math.max(Math.abs(x - kingX), Math.abs(y - kingY));
		for (int i = 0; i < temp; i++)
		{
			board[x + i * xMove][y + i * yMove].setBlockable(true);
		}

	}

	// runs an imaginary check to see if the enemy king is directly behind a
	// piece & will restrict that piece
	public boolean imaginaryCheck(int x, int y, int xMove, int yMove, Board[][] board, Piece[][] piece)
	{
		short temp = (short) potential(x + xMove, y + yMove, board, piece);
		if (temp == 2)
			return imaginaryCheck(x + xMove, y + yMove, xMove, yMove, board, piece);
		else if (temp == -2)
		{
			return true;
		}

		return false;
	}

	public static void resetDefendingKing(Piece[] piece)
	{
		for (int i = 0; i < piece.length; i++)
			piece[i].blockingPiece = -1;
	}

	public boolean getExist()
	{
		return exist;
	}

	public void setExist(boolean exist)
	{
		this.exist = exist;
	}

	public boolean getSide()
	{
		return side;
	}

	public int getBlockingPiece()
	{
		return blockingPiece;
	}

}
