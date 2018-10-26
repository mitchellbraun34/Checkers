package FIREBALL;


import java.awt.Button;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Checker extends JFrame{

	JFrame frame = new JFrame("Mitchell's Checkers!");
	JPanel JP = new JPanel();
	JButton[][] b = new JButton[8][8];
	public static String n;
	JButton a = new JButton();	
	int toRow = 0;
	int toCol = 0;
	int fromRow = 0;
	int fromCol = 0;
	int BlackCounter = 0;
	int WhiteCounter = 0;
	int BlackKingCounter = 0;
	int WhiteKingCounter = 0;
	
	int turn = 0;
	int Black = 0;
	int White = 1;
	
	String[][] C;
	
	public Point computerMove;
	
	/*
	 * Constructs the board and stuff
	 */
	
	public Checker()
	{
		
		JP.setLayout(new GridLayout(8,8));

		for(int i = 0;i<8;i++)
		{
			for(int j = 0; j<8;j++)
			{
				b[i][j] = new JButton();
				JP.add(b[i][j]);
				if((i+j) % 2 == 0)
					b[i][j].setBackground(Color.white);
				else
					b[i][j].setBackground(Color.lightGray);
				
			}
		}
		/*
		 * Setting the White pieces: 12
		 */
		b[0][1].setText("W");
		b[1][0].setText("W");
		b[2][1].setText("W");
		b[1][2].setText("W");
		b[0][3].setText("W");
		b[2][3].setText("W");
		b[1][4].setText("W");
		b[0][5].setText("W");
		b[2][5].setText("W");
		b[1][6].setText("W");
		b[0][7].setText("W");
		b[2][7].setText("W");
		
		/*
		 * Setting the Black pieces: 12
		 */
		b[7][0].setText("B");
		b[5][0].setText("B");
		b[6][1].setText("B");
		b[5][2].setText("B");
		b[7][2].setText("B");
		b[6][3].setText("B");
		b[7][4].setText("B");
		b[5][4].setText("B");
		b[6][5].setText("B");
		b[5][6].setText("B");
		b[7][6].setText("B");
		b[6][7].setText("B");
		
		
		
		frame.add(JP);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setSize(800, 800);
		
		/*
		 * Setting up another board in the console
		 */
		C = new String[][] 
		{
			{"","W","","W","","W","","W"},
			{"W","","W","","W","","W",""},
			{"","W","","W","","W","","W"},
			{"?","","","","","","",""},
			{"","","","","","","",""},
			{"B","","B","","B","","B",""},
			{"","B","","B","","B","","B"},
			{"B","","B","","B","","B",""},
		};
		
	}
	
	/*
	 *Makes the JFrame work
	 */
	
	public void initiate()
	{
		frame.setVisible(true);
		
		for(int i = 0;i<8;i++)
		{
			for(int j = 0; j<8;j++)
			{
				MouseListener listener = new Click();
				b[i][j].addMouseListener(listener); 
			}
		}
	}
		class Click implements MouseListener
		{
			/*
			 * (non-Javadoc)
			 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
			 * Moving the pieces
			 */
			
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				for(int i = 0;i<8;i++)
				{
					for(int j = 0; j<8;j++)
					{
						if(((e.getModifiers() & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) && e.getSource() == b[i][j] && !((i+j) % 2 == 0))
						{
							if(b[i][j].getText().contains("W"))
							{
								turn = 1;
							}
							else if(b[i][j].getText().contains("B"))
							{
								turn = 0;
							}
							n = b[i][j].getText();
							a = (JButton) e.getSource(); //Stores the button
							fromRow = i;
							fromCol = j;
							
						}
						if (((e.getModifiers() & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK) && (e.getSource() == b[i][j]) && (!(b[i][j].getText().contains("B")) && !(b[i][j].getText().contains("W"))) && !((i+j) % 2 == 0) && !(b[i][j].getText().contains("BK")) &&
								!(b[i][j].getText().contains("WK")))
						{
							
							toRow = i;
							toCol = j;
							int JumpRow = (fromRow + toRow) / 2;
							int JumpCol = (fromCol + toCol) / 2;
							if(fromRow - toRow == 1 || fromRow - toRow == -1 || fromCol - toCol == 1 || fromCol - toCol == -1)
							{
								b[i][j].setText(n);
								a.setText(""); //Erases the past button
								Rules();
								System.out.println(PiecesLeft());
								gameWon();
								BlackCounter = BlackCounter - BlackCounter;
								WhiteCounter = WhiteCounter - WhiteCounter;
								BlackKingCounter = BlackKingCounter - BlackKingCounter;
								WhiteKingCounter = WhiteKingCounter - WhiteKingCounter;
								printBoard();
								try {
									algorithm();
								} catch (InterruptedException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
							else if(fromRow - toRow == 2 || fromRow - toRow == -2)
							{
								if(b[JumpRow][JumpCol].getText().contains("W") ||
									b[JumpRow][JumpCol].getText().contains("B") || 
									b[JumpRow][JumpCol].getText().contains("WK") ||
									b[JumpRow][JumpCol].getText().contains("BK"))
									{
									b[i][j].setText(n);
									a.setText(""); //Erases the past button
									Rules();
									System.out.println(PiecesLeft());
									gameWon();
									BlackCounter = BlackCounter - BlackCounter;
									WhiteCounter = WhiteCounter - WhiteCounter;
									BlackKingCounter = BlackKingCounter - BlackKingCounter;
									WhiteKingCounter = WhiteKingCounter - WhiteKingCounter;
									printBoard();
										try {
											algorithm();
										} catch (InterruptedException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
									}
								else {
									System.out.println("Invalid Location");
								}

							}

							else
							{
								System.out.println("Invalid Location");
								BlackCounter = BlackCounter - BlackCounter;
								WhiteCounter = WhiteCounter - WhiteCounter;
								BlackKingCounter = BlackKingCounter - BlackKingCounter;
								WhiteKingCounter = WhiteKingCounter - WhiteKingCounter;
							}
							
						}
							
					}
				}
			}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}
		}
		
		/*
		 * If a piece goes over another piece then it will be erased
		 * If a piece goes to the end part of the board then it will be kinged
		 */
		public void Rules()
		{
			if(fromRow - toRow == 2 || fromRow - toRow == -2)
			{
				int JumpRow = (fromRow + toRow) / 2;
				int JumpCol = (fromCol + toCol) / 2;
				b[JumpRow][JumpCol].setText("");
			}
			if(toRow == 0 || toRow == 7)
			{
				if(b[toRow][toCol].getText().contains("B"))
					b[toRow][toCol].setText("BK");
				else
					b[toRow][toCol].setText("WK");
			}
			toRow = toRow - toRow;
			toCol = toCol - toCol;
			fromRow = fromRow - fromRow;
			fromCol = fromCol - fromCol;
		}
		
		
		
		/*
		 * Counter for the amount of pieces you have
		 */
		
		public String PiecesLeft()
		{
			
			
			for(int i = 0;i<8;i++)
			{
				for(int j = 0; j<8;j++)
				{
					if(b[i][j].getText().contains("B"))
					BlackCounter++;
					if(b[i][j].getText().contains("W"))
					WhiteCounter++;
					if(b[i][j].getText().contains("BK"))
					BlackKingCounter++;
					if(b[i][j].getText().contains("WK"))
					WhiteKingCounter++;
				}
			}
			return "Amount of Black Pieces left: " + BlackCounter +
				   " || Amount of White Pieces Left: " + WhiteCounter +
				   " || Amount of Black King Pieces Left: " + BlackKingCounter +
				   " || Amount of White King Pieces Left: " + WhiteKingCounter;
		}
		
		/*
		 * You know what this does
		 */
		
		public void gameWon() 
		{
			if((BlackCounter + BlackKingCounter) == 0)
				System.out.println("White has won the game!");
			if((WhiteCounter + WhiteKingCounter) == 0)
				System.out.println("Black has won the game!");		
		}
		
		public void printBoard()
		{
			mirrorGUI();
			for(int i = 0; i<8;i++)
			{
				for(int j = 0; j<8; j++)
				{
					System.out.printf(" %2s ", C[i][j]);
				}
				System.out.println();
			}
			System.out.println(getAvailableCells());
		}
		
		public void mirrorGUI()
		{
			for(int i = 0; i<8;i++)
			{
				for(int j = 0; j<8; j++)
				{
					if(b[i][j].getText().contains("W"))
					{
						C[i][j] = "W";
					}
					else if(b[i][j].getText().contains("B"))
					{
						C[i][j] = "B";
					}
					else if(b[i][j].getText().contains("WK"))
					{
						C[i][j] = "WK";
					}
					else if(b[i][j].getText().contains("BK"))
					{
						C[i][j] = "BK";
					}
					else
					{
						C[i][j] = "";
					}
				}
			}
		}
		
		
			
			/*
			 * gets the points of the available white pieces 
			 */
			
			public List<Point> getAvailableCells()
			{
				List<Point> availableCells = new ArrayList<>();
				for(int i = 0;i<8;i++)
				{
					for(int j = 0; j<8;j++)
					{
						if(C[i][j].contains("W"))
						{
							if(i+1 >= 0 && i+1 < 8 && j+1 >= 0 && j+1 < 8 && C[i+1][j+1].equals(""))
								availableCells.add(new Point(i, j));
							else if(i+1 >= 0 && i+1 < 8 && j-1 >= 0 && j-1 < 8 && C[i+1][j-1].equals(""))
								availableCells.add(new Point(i, j));
							else if((i+1 >= 0 && i+1 < 8 && j+1 >= 0 && j+1 < 8 && C[i+1][j+1].equals("B"))
								&& (i+2 >= 0 && i+2 < 8 && j+2 >= 0 && j+2 < 8 && C[i+2][j+2].equals("")))
								availableCells.add(new Point(i, j));
							else if((i+1 >= 0 && i+1 < 8 && j-1 >= 0 && j-1 < 8 && C[i+1][j-1].equals("B"))
									&& (i+2 >= 0 && i+2 < 8 && j-2 >= 0 && j-2 < 8 && C[i+2][j-2].equals("")))
								availableCells.add(new Point(i, j));
						
						}
					}
				}
				return availableCells;
			}
			
			/*
			 * Creates the algorithm where the AI does stuff
			 */
			public void algorithm() throws InterruptedException 
			{
				Point bad = null;
				Point decent = null;
				Point good = null;
				Point great = null;
				
				List<Point> availableCells = getAvailableCells();
				
				for(int i = 0; i < getAvailableCells().size(); i++)
				{
					Point point = availableCells.get(i);
			
					if (!(point.y == 7) && !(point.y == 6) && C[point.x + 1][point.y + 1].equals("B") && C[point.x + 2][point.y + 2].equals(""))
					{
						good = point;
					} 
					else if ( !(point.y == 1) && !(point.y == 0) && C[point.x + 1][point.y - 1].equals("B") && C[point.x + 2][point.y - 2].equals(""))
					{
						good = point;
					}
					else if (!(point.y == 7) && C[point.x + 1][point.y + 1].equals("")) 
					{
						decent = point;
					} 
					else if (!(point.y == 0) && C[point.x + 1][point.y - 1].equals(""))
					{
						decent = point;
					}
					else if(!(point.y == 7) && !(point.y == 6) && C[point.x + 1][point.y + 1].equals("") && C[point.x + 2][point.y + 2].equals("B"))
					{
						bad = point;
					}
					else if(!(point.y == 1) && !(point.y == 0) && C[point.x + 1][point.y - 1].equals("") && C[point.x + 2][point.y - 2].equals("B"))
					{
						bad = point;
					}
				}
				
		
				if (good != null)
				{
					if ( !(good.y == 6) && !(good.y == 7) && C[good.x + 1][good.y + 1].equals("B")	&& C[good.x + 2][good.y + 2].equals("")) 
					{
						TimeUnit.SECONDS.sleep((long) 0.5);
						b[good.x + 2][good.y + 2].setText("W");
						b[good.x+1][good.y+1].setText("");
						b[good.x][good.y].setText("");
						
					} 
					else if (!(good.y == 1) && !(good.y == 0) && C[good.x + 1][good.y - 1].equals("B") && C[good.x + 2][good.y - 2].equals("")) 
					{
						TimeUnit.SECONDS.sleep((long) 0.5);
						b[good.x + 2][good.y - 2].setText("W");
						b[good.x+1][good.y-1].setText("");
						b[good.x][good.y].setText("");
					}
				} 
				else if (good == null)
				{
					if(decent!=null)
					{
						if ( !(decent.y == 7) && C[decent.x + 1][decent.y + 1].equals("")) 
						{
							TimeUnit.SECONDS.sleep((long) 0.5);
							b[decent.x + 1][decent.y + 1].setText("W");
							b[decent.x][decent.y].setText("");
						
						}
						else if (!(decent.y == 0)	&& C[decent.x + 1][decent.y - 1].equals(""))
						{
							TimeUnit.SECONDS.sleep((long) 0.5);
							b[decent.x + 1][decent.y - 1].setText("W");
							b[decent.x][decent.y].setText("");
						}
					}
					else if(decent == null)
					{
						if(bad!=null)
						{
							if(!(bad.y == 7) && !(bad.y == 6) && C[bad.x + 1][bad.y + 1].equals("") && C[bad.x + 2][bad.y + 2].equals("B"))
							{
								TimeUnit.SECONDS.sleep((long) 0.5);
								b[bad.x + 1][bad.y + 1].setText("W");
								b[bad.x][bad.y].setText("");
							}
							else if(!(bad.y == 1) && !(bad.y == 0) && C[bad.x + 1][bad.y - 1].equals("") && C[bad.x + 2][bad.y - 2].equals("B"))
							{
								TimeUnit.SECONDS.sleep((long) 0.5);
								b[bad.x + 1][bad.y - 1].setText("W");
								b[bad.x][bad.y].setText("");
							}
							
						}
					}

				
			
				}
			}
		
		
		/*
		 *Starts the game 
		 */
		
		public static void main(String[] args) 
		{		
			Checker game = new Checker();
			game.initiate();
		}	
		
}


