import java.util.ArrayList;

public class Pawn extends Piece
{

	public Pawn(boolean side)
	{
		super(side, 'p');
	}

	@Override
	public Coordinate[] move(Board[][] board, Piece[][] piece, boolean checkBlock)
	{
		if (!side)
		{
			if (super.potential(x, y - 1, board, piece) == 2)
			{
				if (!checkBlock || checkBlock && board[x][y - 1].getBlockable())
					board[x][y - 1].setType(2);

				if (y == 6 && super.potential(x, y - 2, board, piece) == 2)
					if (!checkBlock || checkBlock && board[x][y - 2].getBlockable())
						board[x][y - 2].setType(2);
			}
			if (super.potential(x + 1, y - 1, board, piece) == 1)
				if (!checkBlock || checkBlock && board[x + 1][y - 1].getBlockable())
					board[x + 1][y - 1].setType(1);

			if (super.potential(x - 1, y - 1, board, piece) == 1)
				if (!checkBlock || checkBlock && board[x - 1][y - 1].getBlockable())
					board[x - 1][y - 1].setType(1);
		}
		else
		{
			if (super.potential(x, y + 1, board, piece) == 2)
			{
				if (!checkBlock || checkBlock && board[x][y + 1].getBlockable())
					board[x][y + 1].setType(2);
				if (y == 1 && super.potential(x, y + 2, board, piece) == 2)
					if (!checkBlock || checkBlock && board[x][y + 2].getBlockable())
						board[x][y + 2].setType(2);
			}

			if (super.potential(x + 1, y + 1, board, piece) == 1)
				if (!checkBlock || checkBlock && board[x + 1][y + 1].getBlockable())
					board[x + 1][y + 1].setType(1);

			if (super.potential(x - 1, y + 1, board, piece) == 1)
				if (!checkBlock || checkBlock && board[x - 1][y + 1].getBlockable())
					board[x - 1][y + 1].setType(1);
		}
		return new Coordinate[0];

	}

	@Override
	public void kingDanger(Board[][] board, Piece[][] piece, KingsSquare[][] square)
	{
		if (!side)
		{
			for (int i = -1; i < 2; i += 2)
			{

				if (x + i >= 0 && x + i < Board.getCOL() && y - 1 >= 0 && y - 1 < Board.getROW())
				{
					int[] a = board[x + i][y - 1].getPiece();
					if (a[1] >= 0)
					{
						if (piece[a[0]][a[1]].getName() == 'K' && piece[a[0]][a[1]].getSide() != side)
						{
							((King) piece[a[0]][a[1]]).increaseThreatCounter();
							immediateThreat = true;
						}
					}
					KingsSquare.squareCheck(x + i, y - 1, square);
				}
			}
		}
		else
		{
			for (int i = -1; i < 2; i += 2)
			{
				if (x + i >= 0 && x + i < Board.getCOL() && y + 1 >= 0 && y + 1 < Board.getROW())
				{
					int[] a = board[x + i][y - 1].getPiece();
					if (a[1] >= 0)
					{
						if (piece[a[0]][a[1]].getName() == 'K' && piece[a[0]][a[1]].getSide() != side)
						{
							((King) piece[a[0]][a[1]]).increaseThreatCounter();
							immediateThreat = true;
						}
					}
					KingsSquare.squareCheck(x + i, y + 1, square);
				}
			}
		}
	}
}