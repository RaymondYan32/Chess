
/***********************************************
 * Name: Chess
 * Author: Raymond Yan
 * Date: August 21, 2016 - 
 * Description: A simple program of Chess as something to do for my last couple weeks of Summer of 2016
 ************************************************/

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Main extends JFrame implements MouseListener
{

	static JPanel mainPanel = new JPanel();

	// Pieces
	Piece[][] piece = new Piece[2][16];
	Board[][] board = Board.generateBoard();
	int tempCol, tempRow, selectedCol, selectedRow, prevCol, prevRow, threatCounter;
	BufferedImage buffer;
	final Font TEXTFONT = new Font("Times New Roman", Font.PLAIN, 30);
	boolean blackTurn, check, checkBlock, whiteWin, blackWin;
	KingsSquare[][] square;

	public static void main(String[] args)
	{
		Main m = new Main();
		m.start();
	}

	@SuppressWarnings("deprecation")
	public void start()
	{
		mainPanel.setSize(900, 900);
		mainPanel.setBackground(Color.white);
		setSize(900, 900);
		add(mainPanel);
		show(true);
		addMouseListener(this);
		buffer = new BufferedImage(900, 900, BufferedImage.TYPE_INT_RGB);
		generatePieces();
		blackTurn = false;
		check = false;
		square = KingsSquare.generateSquare(piece[booleantoInt(blackTurn)][4].getX(),
				piece[booleantoInt(blackTurn)][4].getY());
		threatCounter = 0;
		checkBlock = false;
		whiteWin = false;
		blackWin = false;

		while (true)
		{
			try
			{
				drawBuffer();
				drawScreen();
				setSize(900, 900);
				Thread.sleep(15);
			} catch (Exception e)
			{

			}
		}
	}

	public int booleantoInt(boolean b)
	{
		return (b) ? 1 : 0;
	}

	// sets the initial position of pieces
	public void generatePieces()
	{

		boolean side;
		for (int i = 0; i < 2; i++)
		{
			if (i == 1)
				side = true;
			else
				side = false;

			piece[i][0] = new Rook(side);
			piece[i][1] = new Knight(side);
			piece[i][2] = new Bishop(side);
			piece[i][3] = new Queen(side);

			piece[i][4] = new King(side);
			piece[i][5] = new Bishop(side);
			piece[i][6] = new Knight(side);
			piece[i][7] = new Rook(side);

			for (int ii = 0; ii < 8; ii++)
			{
				piece[i][ii].setX(ii);
				if (side)
					piece[i][ii].setY(0);
				else
					piece[i][ii].setY(7);
			}

			// Pawns
			for (int ii = 0; ii < 8; ii++)
			{
				piece[i][ii + 8] = new Pawn(side);
				piece[i][ii + 8].setX(ii);
				if (side)
					piece[i][ii + 8].setY(1);
				else
					piece[i][ii + 8].setY(6);
			}
		}

		for (int i = 0; i < piece.length; i++)
		{
			for (int ii = 0; ii < piece[0].length; ii++)
			{
				board[piece[i][ii].getX()][piece[i][ii].getY()].setPiece(i, ii);
			}
		}

	}

	public void drawBuffer()
	{
		Graphics2D b = buffer.createGraphics();

		b.setColor(Color.white);
		b.fillRect(0, 0, 900, 900);

		// makes black and white 8x8 board
		for (int col = 0; col < Board.getCOL(); col++)
			for (int row = 0; row < Board.getROW(); row++)
			{
				if (board[col][row].getType() == 0)
					b.setColor(Color.YELLOW);
				else if (board[col][row].getType() == 1)
					b.setColor(Color.RED);
				else if (board[col][row].getType() == 2)
					b.setColor(Color.BLUE);
				else
				{
					if (board[col][row].isWhite())
						b.setColor(Color.WHITE);
					else
						b.setColor(Color.BLACK);
				}

				b.fillRect(col * Board.getWIDTH() + 100, row * Board.getLENGTH() + 100, Board.getWIDTH(),
						Board.getLENGTH());
			}

		b.setColor(Color.black);
		b.drawRect(100, 100, 560, 560);

		b.setFont(TEXTFONT);
		for (int i = 0; i < piece.length; i++)
		{
			for (int ii = 0; ii < piece[0].length; ii++)
			{
				if (piece[i][ii].getExist())
				{
					if (piece[i][ii].getSide())
						b.setColor(Color.cyan);
					else
						b.setColor(Color.GREEN);
					b.drawString(String.valueOf(piece[i][ii].getName()), piece[i][ii].getX() * 70 + 125,
							piece[i][ii].getY() * 70 + 150);
				}
			}
		}

		if (blackTurn)
			b.drawString("Player 2's turn", 10, 80);
		else
			b.drawString("Player 1's turn", 10, 80);

		if (whiteWin || blackWin)
			b.drawString("checkmate", 10, 150);
		else if (check)
			b.drawString("check", 10, 150);
	}

	public void drawScreen()
	{
		Graphics2D g = (Graphics2D) this.getGraphics();
		g.drawImage(buffer, 0, 0, this);
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e)
	{

	}

	@Override
	public void mouseExited(MouseEvent e)
	{

	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		if (e.getX() > 100 && e.getX() < Board.getCOL() * Board.getWIDTH() + 100 && e.getY() > 100
				&& e.getY() < Board.getROW() * Board.getLENGTH() + 100)
		{
			tempCol = (int) Math.floor((e.getX() - 100) / 70);
			tempRow = (int) Math.floor((e.getY() - 100) / 70);
		}

	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		if (e.getX() > 100 && e.getX() < Board.getCOL() * Board.getWIDTH() + 100 && e.getY() > 100
				&& e.getY() < Board.getROW() * Board.getLENGTH() + 100
				&& tempCol == (int) Math.floor((e.getX() - 100) / 70)
				&& tempRow == (int) Math.floor((e.getY() - 100) / 70))
		{
			prevCol = selectedCol;
			prevRow = selectedRow;
			selectedCol = tempCol;
			selectedRow = tempRow;
		}
		else
		{
			selectedCol = -1;
			selectedRow = -1;
		}

		if (selectedCol >= 0 && selectedRow >= 0)
		{
			int[] a = board[selectedCol][selectedRow].getPiece();
			int[] b = board[prevCol][prevRow].getPiece();

			if (board[selectedCol][selectedRow].getType() > 0
					&& booleantoInt(blackTurn) == board[prevCol][prevRow].getPiece()[0])
			{

				if (board[selectedCol][selectedRow].getPiece()[1] >= 0)
				{
					piece[a[0]][a[1]].setExist(false);
					piece[a[0]][a[1]].setX(-1);
					piece[a[0]][a[1]].setY(-1);
				}

				board[selectedCol][selectedRow].setPiece(b[0], b[1]);
				piece[b[0]][b[1]].setX(selectedCol);
				piece[b[0]][b[1]].setY(selectedRow);
				board[prevCol][prevRow].setPiece(-1, -1);

				blackTurn = !blackTurn;
				check = false;

				square = KingsSquare.generateSquare(piece[booleantoInt(blackTurn)][4].getX(),
						piece[booleantoInt(blackTurn)][4].getY());
				((King) piece[booleantoInt(!blackTurn)][4]).resetThreatCounter();

				Board.resetBoard(board);
				KingsSquare.calculateThreat(square, board, piece, !blackTurn);

				threatCounter = ((King) piece[booleantoInt(blackTurn)][4]).getThreatCounter();

				if (threatCounter == 1)
				{
					checkBlock = true;
					check = true;
				}
				else
					checkBlock = false;

				if (check)
				{
					int counter = 0;
					for (int i = 0; i < square.length; i++)
						for (int ii = 0; ii < square[0].length; ii++)
							if (square[i][ii].getThreatDanger() && !(i == 0 && ii == 0))
								counter++;

					if (counter == 8)
					{
						Coordinate[] temp = new Coordinate[0];

						if (checkBlock)
						{
							for (int i = 0; i < 16; i++)
							{
								temp = piece[booleantoInt(blackTurn)][i].move(board, piece, checkBlock);
								if (temp.length > 0)
									i = 16;
							}
						}

						if (blackTurn && temp.length == 0)
							whiteWin = true;
						else
							blackWin = true;
					}
				}

			}
			else
			{
				Board.resetBoard(board);
				board[selectedCol][selectedRow].setType(0);
				if (a[1] >= 0 && a[0] == booleantoInt(blackTurn))
				{
					if (piece[a[0]][a[1]].getName() == 'K')
					{
						for (int i = 0; i < square.length; i++)
							for (int ii = 0; ii < square[0].length; ii++)
							{
								if (square[i][ii].getThreatDanger())
									board[square[i][ii].getX()][square[i][ii].getY()].setType(-2);
							}

					}

					Coordinate[] set = null;
					if (threatCounter < 2 || piece[a[0]][a[1]].getName() == 'K')
						set = piece[a[0]][a[1]].move(board, piece, checkBlock);

					for (int i = 0; i < set.length; i++)
					{
						if (board[set[i].getX()][set[i].getY()].getPiece()[1] >= 0)
							board[set[i].getX()][set[i].getY()].setType(1);
						else
							board[set[i].getX()][set[i].getY()].setType(2);

					}
				}
			}
		}

	}

}
