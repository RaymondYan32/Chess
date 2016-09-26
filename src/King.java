import java.util.ArrayList;

public class King extends Piece
{
	private int threatCounter;
	private boolean moved;

	public King(boolean side)
	{
		super(side, 'K');
		threatCounter = 0;
		moved = false;
	}

	@Override
	public int potential(int x, int y, Board[][] board, Piece[][] piece)
	{

		if (x >= 0 && x < Board.getCOL() && y >= 0 && y < Board.getROW() && board[x][y].getType() >= -1)
		{
			int[] a = board[x][y].getPiece();
			if (a[1] >= 0)
			{
				if (piece[a[0]][a[1]].getSide() == getSide())
					return 0;
				else
					return 1;
			}
			return 2;
		}
		return -1;
	}

	@Override
	public Coordinate[] move(Board[][] board, Piece[][] piece, boolean blockCheck)
	{
		ArrayList<Coordinate> set = new ArrayList<Coordinate>();
		short temp;
		for (int i = -1; i < 2; i++)
			for (int ii = -1; ii < 2; ii++)
			{
				if (!(i == 0 && ii == 0))
				{
					temp = (short) potential(x + i, y + ii, board, piece);
					if (temp == 2 || temp == 1)
						set.add(new Coordinate(x + i, y + ii));
				}
			}
		Coordinate[] a = new Coordinate[set.size()];
		return set.toArray(a);
	}

	public void kingDanger(Board[][] board, Piece[][] piece, KingsSquare[][] square)
	{
		for (int i = -1; i < 2; i++)
			for (int ii = -1; ii < 2; ii++)
			{
				if (!(i == 0 && ii == 0))
				{
					KingsSquare.squareCheck(x + i, y + ii, square);
				}
			}
	}

	public void resetThreatCounter()
	{
		threatCounter = 0;
	}

	public void increaseThreatCounter()
	{
		threatCounter++;
	}

	public int getThreatCounter()
	{
		return threatCounter;
	}
}
