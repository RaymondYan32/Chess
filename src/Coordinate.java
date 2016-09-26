//The column and row at a chess board
public class Coordinate
{

	protected int x, y;

	// a placeholder constructor
	public Coordinate()
	{

	}

	public Coordinate(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public static boolean ComparePosition(Coordinate a, Coordinate b)
	{
		if (a.getX() == b.getX() && a.getY() == b.getY())
			return true;
		else
			return false;
	}

	public static boolean aLine(Coordinate a, Coordinate b, Coordinate c)
	{
		if (( ((double)(b.y - a.y)) / (b.x - a.x)) == ( ((double)(c.y - a.y)) / (c.x - a.x)))
			return true;
		else
			return false;
	}

}
