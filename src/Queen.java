import java.util.ArrayList;

public class Queen extends Piece
{

	public Queen(boolean side)
	{
		super(side, 'Q');
	}

	@Override
	public Coordinate[] move(Board[][] board, Piece[][] piece, boolean blockCheck)
	{
		ArrayList<Coordinate> set = new ArrayList<Coordinate>();
		set.addAll (super.moveAssist(x, y, 1, 0, board, piece, blockCheck));
		set.addAll (super.moveAssist(x, y, -1, 0, board, piece, blockCheck));
		set.addAll (super.moveAssist(x, y, 0, 1, board, piece, blockCheck));
		set.addAll (super.moveAssist(x, y, 0, -1, board, piece, blockCheck));

		set.addAll (super.moveAssist(x, y, 1, 1, board, piece, blockCheck));
		set.addAll (super.moveAssist(x, y, -1, -1, board, piece, blockCheck));
		set.addAll (super.moveAssist(x, y, 1, -1, board, piece, blockCheck));
		set.addAll (super.moveAssist(x, y, -1, 1, board, piece, blockCheck));
		Coordinate[] a = new Coordinate[set.size()];
		return set.toArray(a);
	}

	@Override
	public void kingDanger(Board[][] board, Piece[][] piece, KingsSquare[][] square)
	{
		super.kingDangerAssist(x, y, 1, 0, board, piece, square);
		super.kingDangerAssist(x, y, -1, 0, board, piece, square);
		super.kingDangerAssist(x, y, 0, 1, board, piece, square);
		super.kingDangerAssist(x, y, 0, -1, board, piece, square);

		super.kingDangerAssist(x, y, 1, 1, board, piece, square);
		super.kingDangerAssist(x, y, -1, -1, board, piece, square);
		super.kingDangerAssist(x, y, 1, -1, board, piece, square);
		super.kingDangerAssist(x, y, -1, 1, board, piece, square);
	}

}
