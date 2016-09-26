import java.util.ArrayList;

public class Knight extends Piece
{

	public Knight(boolean side)
	{
		super(side, 'k');
	}

	@Override
	public Coordinate[] move(Board[][] board, Piece[][] piece, boolean blockCheck)
	{
		ArrayList<Coordinate> set = new ArrayList<Coordinate>(0);
		short temp;
		for (int i = -2; i < 4; i += 4)
		{
			for (int ii = -1; ii < 2; ii += 2)
			{
				temp = (short) super.potential(x + i, y + ii, board, piece);
				if (temp == 2 || temp == 1)
					if (!blockCheck || blockCheck && board[x + i][y + ii].getBlockable())
						set.add(new Coordinate(x + i, y + ii));
			}
		}

		for (int i = -2; i < 4; i += 4)
		{
			for (int ii = -1; ii < 2; ii += 2)
			{
				temp = (short) super.potential(x + ii, y + i, board, piece);
				if (temp == 2 || temp == 1)
					if (!blockCheck || blockCheck && board[x + i][y + ii].getBlockable())
						set.add(new Coordinate(x + ii, y + i));
			}
		}
		Coordinate[] a = new Coordinate[set.size()];
		return set.toArray(a);
	}

	@Override
	public void kingDanger(Board[][] board, Piece[][] piece, KingsSquare[][] square)
	{
		for (int i = -2; i < 4; i += 4)
			for (int ii = -1; ii < 2; ii += 2)
			{

				if (x + i >= 0 && x + i < Board.getCOL() && y + ii >= 0 && y + ii < Board.getROW())
				{
					int[] a = board[x + i][y + ii].getPiece();
					if (a[1] >= 0)
					{
						if (piece[a[0]][a[1]].getName() == 'K' && piece[a[0]][a[1]].getSide() != side)
						{
							((King) piece[a[0]][a[1]]).increaseThreatCounter();
							immediateThreat = true;
						}
					}
					KingsSquare.squareCheck(x + i, y + ii, square);
				}
			}

		for (int i = -2; i < 4; i += 4)
			for (int ii = -1; ii < 2; ii += 2)
			{
				if (x + ii >= 0 && x + ii < Board.getCOL() && y + i >= 0 && y + i < Board.getROW())
				{
					int[] a = board[x + ii][y + i].getPiece();
					if (a[1] >= 0)
					{
						if (piece[a[0]][a[1]].getName() == 'K' && piece[a[0]][a[1]].getSide() != side)
						{
							((King) piece[a[0]][a[1]]).increaseThreatCounter();
							immediateThreat = true;
						}
					}
					KingsSquare.squareCheck(x + i, y + ii, square);
				}
			}

	}
}
