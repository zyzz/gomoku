package gomoku3;

import java.awt.image.RescaleOp;
import java.util.Arrays;
import java.util.Random;

import javax.lang.model.util.ElementScanner6;
import javax.swing.JLabel;

public class Gomoku {
	public static int SIZE = 15;
	public static int WIN_COMBINATION = 5;
	public static char NO_WINNER = 0;
	public static char O = ' '; // EMPTY
	public static char H = 'X';// HUMAN
	public static char C = 'O';// COMPUTER
	public static char[][] gameTable = new char[SIZE][SIZE];
	// public static char[][]

	public static void makeHumanTurn(int i, int j, JLabel cells[][]) {
		gameTable[i][j] = H;
		cells[i][j].setText(String.valueOf(gameTable[i][j]));

	}

	public static void init() {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				gameTable[i][j] = O;
			}
		}
	}

	public static void makeRandomComputerTurn(JLabel cells[][]) {

		int freeCell[] = new int[SIZE*SIZE];
		int count = 0;
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (gameTable[i][j] == O) {
					freeCell[count++] = i * SIZE + j + 1;
				}
			}
		}
		int index = new Random().nextInt(count);
		int i = (index - 1) / SIZE;
		int j = index - i * SIZE - 1;
		if (isCellFree(i, j)) {
			gameTable[i][j] = C;
			cells[i][j].setText(String.valueOf(gameTable[i][j]));
		}
	}

	public static boolean makeComputerTurn(JLabel cells[][]) {
		if (tryWinComputer4(cells)) {
			return true;
		} else if (tryWinHuman4(cells)) {
			return true;
		} else if (tryWinComputer3(cells)) {
			return true;
		} else if (tryWinHuman3(cells)) {
			return true;
		} else if (tryWinComputer2(cells)) {
			return true;
		} else if (tryWinComputer1(cells)) {
			return true;

		} else {
			makeRandomComputerTurn(cells);
			return true;
		}

	}

	public static boolean isCellFree(int i, int j) {
		return gameTable[i][j] == O;
	}

	public static boolean tryWinHuman4(JLabel cells[][]) {
		if (Gomoku.setRowComputer4(cells,H)) {
			return true;
		} else if (Gomoku.setCollComputer4(cells,H)) {
			return true;
		} else if (Gomoku.setMainDiagonalComputer4(cells, H)) {
			return true;
		} else if (Gomoku.setNotMainDiagonalComputer4(cells,H)) {
			return true;
		}
		return false;
	}

	public static boolean tryWinComputer4(JLabel cells[][]) {
		if (Gomoku.setRowComputer4(cells, C)) {
			return true;
		} else if (Gomoku.setCollComputer4(cells, C)) {
			return true;
		} else if (Gomoku.setMainDiagonalComputer4(cells, C)) {
			return true;
		} else if (Gomoku.setNotMainDiagonalComputer4(cells, C)) {
			return true;
		}
		return false;
	}

	public static boolean tryWinHuman3(JLabel cells[][]) {
		if (Gomoku.setMainDiagonalHuman3(cells,H)) {
			return true;
		} else if (Gomoku.setNotMainDiagonalHuman3(cells,H)) {
			return true;
		} else if (setCollHuman3(cells,H)) {
			return true;
		} else if (setRowHuman3(cells,H)) {
			return true;
		} else {
			return false;
		}

	}

	public static boolean tryWinComputer3(JLabel cells[][]) {
		if (Gomoku.setRowHuman3(cells,C)) {
			return true;
		} else if (Gomoku.setMainDiagonalHuman3(cells,C)) {
			return true;
		} else if (Gomoku.setCollHuman3(cells,C)) {
			return true;
		} else if (Gomoku.setNotMainDiagonalHuman3(cells,C)) {
			return true;
		} else {
			return false;
		}

	}

	public static boolean tryWinComputer2(JLabel cells[][]) {
		if (Gomoku.setCollComputer2(cells)) {
			return true;
		} else if (Gomoku.setMainDiagonalComputer2(cells)) {
			return true;
		} else if (Gomoku.setRowComputer2(cells)) {
			return true;
		} else if (Gomoku.setNotMainDiagonaComputer2(cells)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean tryWinComputer1(JLabel cells[][]) {
		if (Gomoku.setCollComputer1(cells)) {
			return true;
		} else if (Gomoku.setMainDiagonalComputer1(cells)) {
			return true;
		} else if (Gomoku.setRowComputer1(cells)) {
			return true;
		} else if (Gomoku.setNotMainDiagonaComputer1(cells)) {
			return true;
		} else {
			return false;
		}
	}
	public static char byCollumn() {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE - WIN_COMBINATION + 1; j++) {
				int count = 0;
				if (gameTable[i][j] != O) {
					for (int k = 1; k < WIN_COMBINATION; k++) {
						if (gameTable[i][j] == gameTable[i][j + k]) {
							count++;
							if (count == WIN_COMBINATION-1) {
								return gameTable[i][j];
							}
						}

					}
				}
			}
		}
		return NO_WINNER;
	}
	
	public static char byMainDiagonal(){
		for (int i = 0; i < SIZE - WIN_COMBINATION + 1; i++) {
			for (int j = 0; j < SIZE - WIN_COMBINATION + 1; j++) {
				int count = 0;
				if (gameTable[i][j] != O) {
					for (int k = 1; k < WIN_COMBINATION; k++) {
						if (gameTable[i][j] == gameTable[i + k][j + k]) {
							count++;
							if (count == 4) {
								return gameTable[i][j];
							}
						}

					}
				}
			}

		}
		return NO_WINNER;
	}
public static char byNotMainDiagonal() {
	for (int i = 0; i < SIZE - WIN_COMBINATION + 1; i++) {
		for (int j = 0; j < SIZE; j++) {
			int count = 0;
			if (gameTable[i][j] != O) {
				for (int k = 1; k < WIN_COMBINATION && j - k >= 0; k++) {
					if (gameTable[i][j] == gameTable[i + k][j - k]) {
						count++;
						if (count == 4) {
							return gameTable[i][j];
						}
					}

				}
			}
		}
	}
	return NO_WINNER;
}
public static char byRow() {
	for (int i = 0; i < SIZE - WIN_COMBINATION + 1; i++) {
		for (int j = 0; j < SIZE; j++) {
			int count = 0;
			if (gameTable[i][j] != O) {

				for (int k = 1; k < WIN_COMBINATION; k++) {
					if (gameTable[i][j] == gameTable[i + k][j]) {
						count++;
						if (count == 4) {
							return gameTable[i][j];
						}
					}
				}
			}
		}
	}
	return NO_WINNER;
}

	public static char findWinner() {
		// column
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE - WIN_COMBINATION + 1; j++) {
				int count = 0;
				if (gameTable[i][j] != O) {
					for (int k = 1; k < WIN_COMBINATION; k++) {
						if (gameTable[i][j] == gameTable[i][j + k]) {
							count++;
							if (count == WIN_COMBINATION-1) {
								return gameTable[i][j];
							}
						}

					}
				}
			}
		}
		// mainDiagonal
		for (int i = 0; i < SIZE - WIN_COMBINATION + 1; i++) {
			for (int j = 0; j < SIZE - WIN_COMBINATION + 1; j++) {
				int count = 0;
				if (gameTable[i][j] != O) {
					for (int k = 1; k < WIN_COMBINATION; k++) {
						if (gameTable[i][j] == gameTable[i + k][j + k]) {
							count++;
							if (count == 4) {
								return gameTable[i][j];
							}
						}

					}
				}
			}

		}
		// notMaintDiagonal
		for (int i = 0; i < SIZE - WIN_COMBINATION + 1; i++) {
			for (int j = 0; j < SIZE; j++) {
				int count = 0;
				if (gameTable[i][j] != O) {
					for (int k = 1; k < WIN_COMBINATION && j - k >= 0; k++) {
						if (gameTable[i][j] == gameTable[i + k][j - k]) {
							count++;
							if (count == 4) {
								return gameTable[i][j];
							}
						}

					}
				}
			}
		}
		// row
		for (int i = 0; i < SIZE - WIN_COMBINATION + 1; i++) {
			for (int j = 0; j < SIZE; j++) {
				int count = 0;
				if (gameTable[i][j] != O) {

					for (int k = 1; k < WIN_COMBINATION; k++) {
						if (gameTable[i][j] == gameTable[i + k][j]) {
							count++;
							if (count == 4) {
								return gameTable[i][j];
							}
						}
					}
				}
			}
		}
		return NO_WINNER;
	}

		

	public static boolean setNotMainDiagonaComputer1(JLabel cells[][]) {
		char variants1[] = { C, O, O, O, O };
		char variants2[] = { O, C, O, O, O };
		char variants3[] = { O, O, C, O, O };
		char variants4[] = { O, O, O, C, O };
		char variants5[] = { O, O, O, O, C };

		char[] chArray = new char[5];
		for (int i = 0; i < SIZE - WIN_COMBINATION; i++) {
			for (int j = 4; j < SIZE; j++) {
				for (int k = 0; k < 5; k++) {
					chArray[k] = gameTable[i + k][j - k];
				}
				if (Arrays.equals(variants1, chArray)) {
					gameTable[i + 1][j - 1] = C;
					cells[i + 1][j - 1].setText(String.valueOf(gameTable[i + 1][j - 1]));
					return true;
				} else if (Arrays.equals(variants2, chArray)) {
					gameTable[i + 2][j - 2] = C;
					cells[i + 2][j - 2].setText(String.valueOf(gameTable[i + 2][j - 2]));
					return true;
				} else if (Arrays.equals(variants3, chArray)) {
					gameTable[i + 3][j - 3] = C;
					cells[i + 3][j - 3].setText(String.valueOf(gameTable[i + 3][j - 3]));
					return true;
				} else if (Arrays.equals(variants4, chArray)) {
					gameTable[i + 4][j - 4] = C;
					cells[i + 4][j - 4].setText(String.valueOf(gameTable[i + 4][j - 4]));
					return true;
				} else if (Arrays.equals(variants5, chArray)) {
					gameTable[i + 3][j - 3] = C;
					cells[i + 3][j - 3].setText(String.valueOf(gameTable[i + 3][j - 3]));
					return true;
				}
			}
		}
		return false;
	}

	public static boolean setMainDiagonalComputer1(JLabel cells[][]) {
		char variants1[] = { C, O, O, O, O };
		char variants2[] = { O, C, O, O, O };
		char variants3[] = { O, O, C, O, O };
		char variants4[] = { O, O, O, C, O };
		char variants5[] = { O, O, O, O, C };
		char[] chArray = new char[5];
		for (int i = 0; i < SIZE - WIN_COMBINATION+1; i++) {
			for (int j = 0; j < SIZE - WIN_COMBINATION+1; j++) {
				for (int k = 0; k < 5; k++) {
					chArray[k] = gameTable[i + k][j + k];
				}
				if (Arrays.equals(variants1, chArray)) {
					gameTable[i + 1][j + 1] = C;
					cells[i + 1][j + 1].setText(String.valueOf(gameTable[i + 1][j + 1]));
					return true;
				} else if (Arrays.equals(variants2, chArray)) {
					gameTable[i + 2][j + 2] = C;
					cells[i + 2][j + 2].setText(String.valueOf(gameTable[i + 2][j + 2]));
					return true;
				} else if (Arrays.equals(variants3, chArray)) {
					gameTable[i + 3][j + 3] = C;
					cells[i + 3][j + 3].setText(String.valueOf(gameTable[i + 3][j + 3]));
					return true;
				} else if (Arrays.equals(variants4, chArray)) {
					gameTable[i + 4][j + 4] = C;
					cells[i + 4][j + 4].setText(String.valueOf(gameTable[i + 4][j + 4]));
					return true;
				} else if (Arrays.equals(variants5, chArray)) {
					gameTable[i + 3][j + 3] = C;
					cells[i + 3][j + 3].setText(String.valueOf(gameTable[i + 3][j + 3]));
					return true;
				}
			}
		}
		return false;
	}

	public static boolean setRowComputer1(JLabel cells[][]) {
		char variants1[] = { C, O, O, O, O };
		char variants2[] = { O, C, O, O, O };
		char variants3[] = { O, O, C, O, O };
		char variants4[] = { O, O, O, C, O };
		char variants5[] = { O, O, O, O, C };

		char[] chArray = new char[5];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE - WIN_COMBINATION; j++) {
				for (int k = 0; k < 5; k++) {
					chArray[k] = gameTable[i][j + k];
				}
				if (Arrays.equals(variants1, chArray)) {
					gameTable[i][j + 1] = C;
					cells[i][j + 1].setText(String.valueOf(gameTable[i][j + 1]));
					return true;
				} else if (Arrays.equals(variants2, chArray)) {
					gameTable[i][j + 2] = C;
					cells[i][j + 2].setText(String.valueOf(gameTable[i][j + 2]));
					return true;
				} else if (Arrays.equals(variants3, chArray)) {
					gameTable[i][j + 3] = C;
					cells[i][j + 3].setText(String.valueOf(gameTable[i][j + 3]));
					return true;
				} else if (Arrays.equals(variants4, chArray)) {
					gameTable[i][j + 4] = C;
					cells[i][j + 4].setText(String.valueOf(gameTable[i][j + 4]));
					return true;
				} else if (Arrays.equals(variants5, chArray)) {
					gameTable[i][j + 3] = C;
					cells[i][j + 3].setText(String.valueOf(gameTable[i][j + 3]));
					return true;
				}
			}
		}
		return false;
	}

	public static boolean setCollComputer1(JLabel cells[][]) {
		char variants1[] = { C, O, O, O, O };
		char variants2[] = { O, C, O, O, O };
		char variants3[] = { O, O, C, O, O };
		char variants4[] = { O, O, O, C, O };
		char variants5[] = { O, O, O, O, C };
		char[] chArray = new char[5];
		for (int i = 0; i < SIZE - WIN_COMBINATION; i++) {
			for (int j = 0; j < SIZE; j++) {
				for (int k = 0; k < 5; k++) {
					chArray[k] = gameTable[i + k][j];
				}
				if (Arrays.equals(variants1, chArray)) {
					gameTable[i + 1][j] = C;
					cells[i + 1][j].setText(String.valueOf(gameTable[i + 1][j]));
					return true;
				} else if (Arrays.equals(variants2, chArray)) {
					gameTable[i + 2][j] = C;
					cells[i + 2][j].setText(String.valueOf(gameTable[i + 2][j]));
					return true;
				} else if (Arrays.equals(variants3, chArray)) {
					gameTable[i + 3][j] = C;
					cells[i + 3][j].setText(String.valueOf(gameTable[i + 3][j]));
					return true;
				} else if (Arrays.equals(variants4, chArray)) {
					gameTable[i + 4][j] = C;
					cells[i + 4][j].setText(String.valueOf(gameTable[i + 4][j]));
					return true;
				} else if (Arrays.equals(variants5, chArray)) {
					gameTable[i + 3][j] = C;
					cells[i + 3][j].setText(String.valueOf(gameTable[i + 3][j]));
					return true;
				}
			}
		}
		return false;
	}

	public static boolean setNotMainDiagonaComputer2(JLabel cells[][]) {
		char variants1[] = { C, O, C, O, O };
		char variants2[] = { O, C, O, C, O };
		char variants3[] = { O, O, C, O, C };
		char variants4[] = { O, O, C, O, C };
		char variants5[] = { O, O, O, C, C };
		char variants6[] = { O, O, C, C, O };
		char variants7[] = { O, C, C, O, O };
		char variants8[] = { C, C, O, O, O };
		char variants9[] = { C, O, O, C, O };
		char variants10[] = { O, C, O, O, C };

		char[] chArray = new char[5];
		for (int i = 0; i < SIZE - WIN_COMBINATION; i++) {
			for (int j = 5; j < SIZE; j++) {
				for (int k = 0; k < 5; k++) {
					chArray[k] = gameTable[i + k][j - k];
				}
				if (Arrays.equals(variants1, chArray)) { 
					gameTable[i + 1][j - 1] = C;
					cells[i + 1][j - 1].setText(String.valueOf(gameTable[i + 1][j - 1]));
					return true;
				} else if (Arrays.equals(variants2, chArray)) { 
					gameTable[i + 2][j - 2] = C;
					cells[i + 2][j - 2].setText(String.valueOf(gameTable[i + 2][j - 2]));
					return true;
				} else if (Arrays.equals(variants3, chArray)) { 
					gameTable[i + 3][j - 3] = C;
					cells[i + 3][j - 3].setText(String.valueOf(gameTable[i + 3][j - 3]));
					return true;
				} else if (Arrays.equals(variants4, chArray)) { 
					gameTable[i + 3][j - 3] = C;
					cells[i + 3][j - 3].setText(String.valueOf(gameTable[i + 3][j - 3]));
					return true;
				} else if (Arrays.equals(variants5, chArray)) {
					gameTable[i + 2][j - 2] = C;
					cells[i + 2][j - 2].setText(String.valueOf(gameTable[i + 2][j - 2]));
					return true;
				} else if (Arrays.equals(variants6, chArray)) { 
					gameTable[i + 4][j - 4] = C;
					cells[i + 4][j - 4].setText(String.valueOf(gameTable[i + 4][j - 4]));
					return true;
				} else if (Arrays.equals(variants7, chArray)) { 
					gameTable[i + 3][j - 3] = C;
					cells[i + 3][j - 3].setText(String.valueOf(gameTable[i + 3][j - 3]));
					return true;
				} else if (Arrays.equals(variants8, chArray)) { 
					gameTable[i + 2][j - 2] = C;
					cells[i + 2][j - 2].setText(String.valueOf(gameTable[i + 2][j - 2]));
					return true;
				} else if (Arrays.equals(variants9, chArray)) { 
					gameTable[i + 2][j - 2] = C;
					cells[i + 2][j - 2].setText(String.valueOf(gameTable[i + 2][j - 2]));
					return true;
				} else if (Arrays.equals(variants10, chArray)) {
					gameTable[i + 2][j - 2] = C;
					cells[i + 2][j - 2].setText(String.valueOf(gameTable[i + 2][j - 2]));
					return true;
				}
			}
		}
		return false;
	}

	public static boolean setMainDiagonalComputer2(JLabel cells[][]) {
		char variants1[] = { C, O, C, O, O };
		char variants2[] = { O, C, O, C, O };
		char variants3[] = { O, O, C, O, C };
		char variants4[] = { O, O, C, O, C };
		char variants5[] = { O, O, O, C, C };
		char variants6[] = { O, O, C, C, O };
		char variants7[] = { O, C, C, O, O };
		char variants8[] = { C, C, O, O, O };
		char variants9[] = { C, O, O, C, O };
		char variants10[] = { O, C, O, O, C };

		char[] chArray = new char[5];
		for (int i = 0; i < SIZE - WIN_COMBINATION; i++) {
			for (int j = 0; j < SIZE - WIN_COMBINATION; j++) {
				for (int k = 0; k < 5; k++) {
					chArray[k] = gameTable[i + k][j + k];
				} if (Arrays.equals(variants1, chArray)) { 
					gameTable[i + 1][j + 1] = C;
					cells[i + 1][j + 1].setText(String.valueOf(gameTable[i + 1][j + 1]));
					return true;
				} else if (Arrays.equals(variants2, chArray)) { 
					gameTable[i + 2][j + 2] = C;
					cells[i + 2][j + 2].setText(String.valueOf(gameTable[i + 2][j + 2]));
					return true;
				} else if (Arrays.equals(variants3, chArray)) { 
					gameTable[i + 3][j + 3] = C;
					cells[i + 3][j + 3].setText(String.valueOf(gameTable[i + 3][j + 3]));
					return true;
				} else if (Arrays.equals(variants4, chArray)) { 
					gameTable[i + 3][j + 3] = C;
					cells[i + 3][j + 3].setText(String.valueOf(gameTable[i + 3][j + 3]));
					return true;
				} else if (Arrays.equals(variants5, chArray)) { 
					gameTable[i + 2][j + 2] = C;
					cells[i + 2][j + 2].setText(String.valueOf(gameTable[i + 2][j + 2]));
					return true;
				}  else if (Arrays.equals(variants6, chArray)) { 
					gameTable[i + 1][j + 1] = C;
					cells[i + 1][j + 1].setText(String.valueOf(gameTable[i + 1][j + 1]));
					return true;
				} else if (Arrays.equals(variants7, chArray)) { 
					gameTable[i + 3][j + 3] = C;
					cells[i + 3][j + 3].setText(String.valueOf(gameTable[i + 3][j + 3]));
					return true;
				} else if (Arrays.equals(variants8, chArray)) { 
					gameTable[i + 2][j + 2] = C;
					cells[i + 2][j + 2].setText(String.valueOf(gameTable[i + 2][j + 2]));
					return true;
				}  else if (Arrays.equals(variants9, chArray)) { 
					gameTable[i + 2][j + 2] = C;
					cells[i + 2][j + 2].setText(String.valueOf(gameTable[i + 2][j + 2]));
					return true;
				} else if (Arrays.equals(variants10, chArray)) { 
					gameTable[i + 2][j + 2] = C;
					cells[i + 2][j + 2].setText(String.valueOf(gameTable[i + 2][j + 2]));
					return true;
				} 
			}
		}
		return false;
	}

	public static boolean setRowComputer2(JLabel cells[][]) {
		char variants1[] = { C, O, C, O, O };
		char variants2[] = { O, C, O, C, O };
		char variants3[] = { O, O, C, O, C };
		char variants4[] = { O, O, C, O, C };
		char variants5[] = { O, O, O, C, C };
		char variants6[] = { O, O, C, C, O };
		char variants7[] = { O, C, C, O, O };
		char variants8[] = { C, C, O, O, O };
		char variants9[] = { C, O, O, C, O };
		char variants10[] = { O, C, O, O, C };

		char[] chArray = new char[5];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE - WIN_COMBINATION; j++) {
				for (int k = 0; k < 5; k++) {
					chArray[k] = gameTable[i][j + k];
				} if (Arrays.equals(variants1, chArray)) { 
					gameTable[i][j + 1] = C;
					cells[i][j + 1].setText(String.valueOf(gameTable[i][j + 1]));
					return true;
				} else if (Arrays.equals(variants2, chArray)) { 
					gameTable[i][j + 2] = C;
					cells[i][j + 2].setText(String.valueOf(gameTable[i][j + 2]));
					return true;
				} else if (Arrays.equals(variants3, chArray)) { 
					gameTable[i][j + 3] = C;
					cells[i][j + 3].setText(String.valueOf(gameTable[i][j + 3]));
					return true;
				} else if (Arrays.equals(variants4, chArray)) { 
					gameTable[i][j + 3] = C;
					cells[i][j + 3].setText(String.valueOf(gameTable[i][j + 3]));
					return true;
				} else if (Arrays.equals(variants5, chArray)) { 
					gameTable[i][j + 2] = C;
					cells[i][j + 2].setText(String.valueOf(gameTable[i][j + 2]));
					return true;
				}else if (Arrays.equals(variants6, chArray)) { 
					gameTable[i][j + 1] = C;
					cells[i][j + 1].setText(String.valueOf(gameTable[i][j + 1]));
					return true;
				}  else if (Arrays.equals(variants7, chArray)) { 
					gameTable[i][j + 3] = C;
					cells[i][j + 3].setText(String.valueOf(gameTable[i][j + 3]));
					return true;
				}else if (Arrays.equals(variants8, chArray)) { 
					gameTable[i][j + 2] = C;
					cells[i][j + 2].setText(String.valueOf(gameTable[i][j + 2]));
					return true;
				} else if (Arrays.equals(variants9, chArray)) { 
					gameTable[i][j + 2] = C;
					cells[i][j + 2].setText(String.valueOf(gameTable[i][j + 2]));
					return true;
				} else if (Arrays.equals(variants10, chArray)) { 
					gameTable[i][j + 2] = C;
					cells[i][j + 2].setText(String.valueOf(gameTable[i][j + 2]));
					return true;
				}
			}
		}
		return false;
	}

	public static boolean setCollComputer2(JLabel cells[][]) {
		char variants1[] = { C, O, C, O, O };
		char variants2[] = { O, C, O, C, O };
		char variants3[] = { O, O, C, O, C };
		char variants4[] = { O, O, C, O, C };
		char variants5[] = { O, O, O, C, C };
		char variants6[] = { O, O, C, C, O };
		char variants7[] = { O, C, C, O, O };
		char variants8[] = { C, C, O, O, O };
		char variants9[] = { C, O, O, C, O };
		char variants10[] = { O, C, O, O, C };
		char[] chArray = new char[5];
		for (int i = 0; i < SIZE - WIN_COMBINATION; i++) {
			for (int j = 0; j < SIZE; j++) {
				for (int k = 0; k < 5; k++) {
					chArray[k] = gameTable[i + k][j];
				}if (Arrays.equals(variants1, chArray)) {
					gameTable[i + 1][j] = C;
					cells[i + 1][j].setText(String.valueOf(gameTable[i + 1][j]));
					return true;
				} else if (Arrays.equals(variants2, chArray)) {
					gameTable[i + 2][j] = C;
					cells[i + 2][j].setText(String.valueOf(gameTable[i + 2][j]));
					return true;
				} else if (Arrays.equals(variants3, chArray)) {
					gameTable[i + 3][j] = C;
					cells[i + 3][j].setText(String.valueOf(gameTable[i + 3][j]));
					return true;
				} else if (Arrays.equals(variants4, chArray)) {
					gameTable[i + 3][j] = C;
					cells[i + 3][j].setText(String.valueOf(gameTable[i + 3][j]));
					return true;
				} else if (Arrays.equals(variants5, chArray)) {
					gameTable[i + 2][j] = C;
					cells[i + 2][j].setText(String.valueOf(gameTable[i + 2][j]));
					return true;
				} else if (Arrays.equals(variants6, chArray)) {
					gameTable[i + 1][j] = C;
					cells[i + 1][j].setText(String.valueOf(gameTable[i + 1][j]));
					return true;
				} else if (Arrays.equals(variants7, chArray)) {
					gameTable[i + 3][j] = C;
					cells[i + 3][j].setText(String.valueOf(gameTable[i + 3][j]));
					return true;
				}else if (Arrays.equals(variants8, chArray)) {
					gameTable[i + 2][j] = C;
					cells[i + 2][j].setText(String.valueOf(gameTable[i + 2][j]));
					return true;
				}else if (Arrays.equals(variants9, chArray)) {
					gameTable[i + 2][j] = C;
					cells[i + 2][j].setText(String.valueOf(gameTable[i + 2][j]));
					return true;
				}else if (Arrays.equals(variants10, chArray)) {
					gameTable[i + 2][j] = C;
					cells[i + 2][j].setText(String.valueOf(gameTable[i + 2][j]));
					return true;
				}
			}
		}
		return false;
	}

	public static boolean setNotMainDiagonalComputer4(JLabel cells[][], char figure) {
		char variants1[] = {  figure, figure, figure, figure, O };
		char variants2[] = { figure, figure, figure, O, figure };
		char variants3[] = { figure, figure, O, figure, figure};
		char variants4[] = { figure, O, figure, figure, figure};
		char variants5[] = { O, figure, figure, figure, figure };

		char[] chArray = new char[5];
		for (int i = 0; i < SIZE - WIN_COMBINATION+1; i++) {
			for (int j = 4; j < SIZE; j++) {
				for (int k = 0; k < 5; k++) {
					chArray[k] = gameTable[i + k][j - k];
				} if (Arrays.equals(variants1, chArray)) {
					gameTable[i + 4][j - 4] = C;
					cells[i + 4][j - 4].setText(String.valueOf(gameTable[i + 4][j - 4]));
					return true;
				} else if (Arrays.equals(variants2, chArray)) {
					gameTable[i+3][j-3] = C;
					cells[i+3][j-3].setText(String.valueOf(gameTable[i+3][j-3]));
					return true;
				} else if ((Arrays.equals(variants3, chArray))) {
					gameTable[i + 2][j - 2] = C;
					cells[i + 2][j - 2].setText(String.valueOf(gameTable[i + 2][j - 2]));
					return true;
				} else if ((Arrays.equals(variants4, chArray))) {
					gameTable[i + 1][j -1] = C;
					cells[i + 1][j -1].setText(String.valueOf(gameTable[i + 1][j -1]));
					return true;
				}else if ((Arrays.equals(variants5, chArray))) {
					gameTable[i][j] = C;
					cells[i][j].setText(String.valueOf(gameTable[i][j]));
					return true;
				}
			}
		}
		return false;
	}

	public static boolean setMainDiagonalComputer4(JLabel cells[][], char figure) {
		char variants1[] = {  figure, figure, figure, figure, O };
		char variants2[] = { figure, figure, figure, O, figure };
		char variants3[] = { figure, figure, O, figure, figure};
		char variants4[] = { figure, O, figure, figure, figure};
		char variants5[] = { O, figure, figure, figure, figure };

		char[] chArray = new char[5];
		for (int i = 0; i < SIZE - WIN_COMBINATION+1; i++) {
			for (int j = 0; j < SIZE - WIN_COMBINATION+1; j++) {
				for (int k = 0; k < 5; k++) {
					chArray[k] = gameTable[i + k][j + k];
				}if (Arrays.equals(variants1, chArray)) {
						gameTable[i + 4][j + 4] = C;
						cells[i + 4][j + 4].setText(String.valueOf(gameTable[i + 4][j + 4]));
						return true;
					} else if (Arrays.equals(variants2, chArray)) {
						gameTable[i+3][j+3] = C;
						cells[i+3][j+3].setText(String.valueOf(gameTable[i+3][j+3]));
						return true;
					} else if ((Arrays.equals(variants3, chArray))) {
						gameTable[i + 2][j + 2] = C;
						cells[i + 2][j + 2].setText(String.valueOf(gameTable[i + 2][j + 2]));
						return true;
					} else if ((Arrays.equals(variants4, chArray))) {
						gameTable[i + 1][j +1] = C;
						cells[i + 1][j +1].setText(String.valueOf(gameTable[i + 1][j +1]));
						return true;
					}else if ((Arrays.equals(variants5, chArray))) {
						gameTable[i][j] = C;
						cells[i][j].setText(String.valueOf(gameTable[i][j]));
						return true;
					}
			}
		}
		return false;
	}

	public static boolean setRowComputer4(JLabel cells[][], char figure) {
		char variants1[] = {  figure, figure, figure, figure, O };
		char variants2[] = { figure, figure, figure, O, figure };
		char variants3[] = { figure, figure, O, figure, figure};
		char variants4[] = { figure, O, figure, figure, figure};
		char variants5[] = { O, figure, figure, figure, figure };

		char[] chArray = new char[WIN_COMBINATION];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE - WIN_COMBINATION; j++) {
				for (int k = 0; k < WIN_COMBINATION; k++) {
					chArray[k] = gameTable[i][j + k];
				}if (Arrays.equals(variants1, chArray)) {
					gameTable[i][j + 4] = C;
					cells[i ][j + 4].setText(String.valueOf(gameTable[i ][j + 4]));
					return true;
				} else if (Arrays.equals(variants2, chArray)) {
					gameTable[i][j+3] = C;
					cells[i][j+3].setText(String.valueOf(gameTable[i][j+3]));
					return true;
				} else if ((Arrays.equals(variants3, chArray))) {
					gameTable[i][j + 2] = C;
					cells[i][j + 2].setText(String.valueOf(gameTable[i][j + 2]));
					return true;
				} else if ((Arrays.equals(variants4, chArray))) {
					gameTable[i][j +1] = C;
					cells[i][j +1].setText(String.valueOf(gameTable[i][j +1]));
					return true;
				}else if ((Arrays.equals(variants5, chArray))) {
					gameTable[i][j] = C;
					cells[i][j].setText(String.valueOf(gameTable[i][j]));
					return true;
				}
			}
		}
		return false;
	}

	public static boolean setCollComputer4(JLabel cells[][], char figure) {
		char variants1[] = {  figure, figure, figure, figure, O };
		char variants2[] = { figure, figure, figure, O, figure };
		char variants3[] = { figure, figure, O, figure, figure};
		char variants4[] = { figure, O, figure, figure, figure};
		char variants5[] = { O, figure, figure, figure, figure };

		char[] chArray = new char[5];
		for (int i = 0; i < SIZE - WIN_COMBINATION; i++) {
			for (int j = 0; j < SIZE; j++) {
				for (int k = 0; k < 5; k++) {
					chArray[k] = gameTable[i + k][j];
				}if (Arrays.equals(variants1, chArray)) {
					gameTable[i + 4][j] = C;
					cells[i + 4][j].setText(String.valueOf(gameTable[i + 4][j]));
					return true;
				} else if (Arrays.equals(variants2, chArray)) {
					gameTable[i+3][j] = C;
					cells[i+3][j].setText(String.valueOf(gameTable[i+3][j]));
					return true;
				} else if ((Arrays.equals(variants3, chArray))) {
					gameTable[i + 2][j] = C;
					cells[i + 2][j].setText(String.valueOf(gameTable[i + 2][j]));
					return true;
				} else if ((Arrays.equals(variants4, chArray))) {
					gameTable[i + 1][j] = C;
					cells[i + 1][j].setText(String.valueOf(gameTable[i + 1][j]));
					return true;
				}else if ((Arrays.equals(variants5, chArray))) {
					gameTable[i][j] = C;
					cells[i][j].setText(String.valueOf(gameTable[i][j]));
					return true;
				}
			}
		}
		return false;
	}

	public static boolean handleTrySetResult(int res, int emptyI, int emptyJ, JLabel cell[][]) {
		if ((res > 0) && emptyI != -1 && emptyJ != -1) {
			gameTable[emptyI][emptyJ] = C;
			cell[emptyI][emptyJ].setText(String.valueOf(gameTable[emptyI][emptyJ]));
			return true;
		} else {
			return false;
		}
	}

	public static boolean setNotMainDiagonalHuman3(JLabel cells[][], char figure) {
		char variants1[] = { O, figure, figure, figure, O };
		char variants2[] = { figure, figure, figure, O, O };
		char variants3[] = { O, O, figure, figure, figure };
		char variants4[] = { O, figure, O, figure, figure };
		char variants5[] = { figure, O, figure, figure, O };
		char variants6[] = { O, figure, figure, O, figure };
	
		char[] chArray = new char[5];
		for (int i = 0; i < SIZE - WIN_COMBINATION+1; i++) {
			for (int j = 4; j < SIZE; j++) {
				for (int k = 0; k < 5; k++) {
					chArray[k] = gameTable[i + k][j - k];
				}if (Arrays.equals(variants1, chArray)) {
					gameTable[i + 4][j - 4] = C;
					cells[i + 4][j - 4].setText(String.valueOf(gameTable[i + 4][j - 4]));
					return true;
				} else if (Arrays.equals(variants2, chArray)) {
					gameTable[i + 3][j - 3] = C;
					cells[i + 3][j - 3].setText(String.valueOf(gameTable[i + 3][j - 3]));
					return true;
				} else if (Arrays.equals(variants3, chArray)) {
					gameTable[i + 1][j - 1] = C;
					cells[i + 1][j - 1].setText(String.valueOf(gameTable[i + 1][j - 1]));
					return true;
				} else if (Arrays.equals(variants4, chArray)) {
					gameTable[i + 2][j - 2] = C;
					cells[i + 2][j - 2].setText(String.valueOf(gameTable[i + 2][j - 2]));
					return true;
				} else if (Arrays.equals(variants5, chArray)) {
					gameTable[i + 1][j - 1] = C;
					cells[i + 1][j - 1].setText(String.valueOf(gameTable[i + 1][j - 1]));
					return true;
				} else if (Arrays.equals(variants6, chArray)) {
					gameTable[i + 3][j - 3] = C;
					cells[i + 3][j - 3].setText(String.valueOf(gameTable[i + 3][j - 3]));
					return true;
				}
			}
		}
		return false;
	}

	public static boolean setMainDiagonalHuman3(JLabel cells[][], char figure) {
		char variants1[] = { O, figure, figure, figure, O };
		char variants2[] = { figure, figure, figure, O, O };
		char variants3[] = { O, O, figure, figure, figure };
		char variants4[] = { O, figure, O, figure, figure };
		char variants5[] = { figure, O, figure, figure, O };
		char variants6[] = { O, figure, figure, O, figure };

		char[] chArray = new char[5];
		for (int i = 0; i < SIZE - WIN_COMBINATION+1; i++) {
			for (int j = 0; j < SIZE - WIN_COMBINATION+1; j++) {
				for (int k = 0; k < 5; k++) {
					chArray[k] = gameTable[i + k][j + k];

				}if (Arrays.equals(variants1, chArray)) {
					gameTable[i + 4][j + 4] = C;
					cells[i + 4][j + 4].setText(String.valueOf(gameTable[i + 4][j + 4]));
					return true;
				} else if (Arrays.equals(variants2, chArray)) {
					gameTable[i + 3][j + 3] = C;
					cells[i + 3][j + 3].setText(String.valueOf(gameTable[i + 3][j + 3]));
					return true;
				} else if (Arrays.equals(variants3, chArray)) {
					gameTable[i + 1][j + 1] = C;
					cells[i + 1][j + 1].setText(String.valueOf(gameTable[i + 1][j + 1]));
					return true;
				} else if (Arrays.equals(variants4, chArray)) {
					gameTable[i + 2][j + 2] = C;
					cells[i + 2][j + 2].setText(String.valueOf(gameTable[i + 2][j + 2]));
					return true;
				} else if (Arrays.equals(variants5, chArray)) {
					gameTable[i + 1][j + 1] = C;
					cells[i + 1][j + 1].setText(String.valueOf(gameTable[i + 1][j + 1]));
					return true;
				} else if (Arrays.equals(variants6, chArray)) {
					gameTable[i + 3][j + 3] = C;
					cells[i + 3][j + 3].setText(String.valueOf(gameTable[i + 3][j + 3]));
					return true;
				}
			}
		}
		return false;
	}

	public static boolean setRowHuman3(JLabel cells[][],char figure) {
		char variants1[] = { O, figure, figure, figure, O };
		char variants2[] = { figure, figure, figure, O, O };
		char variants3[] = { O, O, figure, figure, figure };
		char variants4[] = { O, figure, O, figure, figure };
		char variants5[] = { figure, O, figure, figure, O };
		char variants6[] = { O, figure, figure, O, figure };
		char[] chArray = new char[5];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE - WIN_COMBINATION; j++) {
				for (int k = 0; k < 5; k++) {
					chArray[k] = gameTable[i][j + k];
				}if (Arrays.equals(variants1, chArray)) {
					gameTable[i][j + 4] = C;
					cells[i][j + 4].setText(String.valueOf(gameTable[i][j + 4]));
					return true;
				} else if (Arrays.equals(variants2, chArray)) {
					gameTable[i][j + 3] = C;
					cells[i][j + 3].setText(String.valueOf(gameTable[i][j + 3]));
					return true;
				} else if (Arrays.equals(variants3, chArray)) {
					gameTable[i][j + 1] = C;
					cells[i][j + 1].setText(String.valueOf(gameTable[i][j + 1]));
					return true;
				} else if (Arrays.equals(variants4, chArray)) {
					gameTable[i][j + 2] = C;
					cells[i][j + 2].setText(String.valueOf(gameTable[i][j + 2]));
					return true;
				} else if (Arrays.equals(variants5, chArray)) {
					gameTable[i][j + 1] = C;
					cells[i][j + 1].setText(String.valueOf(gameTable[i][j + 1]));
					return true;
				} else if (Arrays.equals(variants6, chArray)) {
					gameTable[i][j + 3] = C;
					cells[i][j + 3].setText(String.valueOf(gameTable[i][j + 3]));
					return true;
				}
			}
		}
		return false;
	}

	public static boolean setCollHuman3(JLabel cells[][], char figure) {
		char variants1[] = { O, figure, figure, figure, O };
		char variants2[] = { figure, figure, figure, O, O };
		char variants3[] = { O, O, figure, figure, figure };
		char variants4[] = { O, figure, O, figure, figure };
		char variants5[] = { figure, O, figure, figure, O };
		char variants6[] = { O, figure, figure, O, figure };
		char[] chArray = new char[5];
		for (int i = 0; i < SIZE - WIN_COMBINATION; i++) {
			for (int j = 0; j < SIZE; j++) {
				for (int k = 0; k < 5; k++) {
					chArray[k] = gameTable[i + k][j];
				}if (Arrays.equals(variants1, chArray)) {
					gameTable[i + 4][j] = C;
					cells[i + 4][j].setText(String.valueOf(gameTable[i + 4][j]));
					return true;
				} else if (Arrays.equals(variants2, chArray)) {
					gameTable[i + 3][j] = C;
					cells[i + 3][j].setText(String.valueOf(gameTable[i + 3][j]));
					return true;
				} else if (Arrays.equals(variants3, chArray)) {
					gameTable[i + 1][j ] = C;
					cells[i + 1][j].setText(String.valueOf(gameTable[i + 1][j]));
					return true;
				} else if (Arrays.equals(variants4, chArray)) {
					gameTable[i + 2][j] = C;
					cells[i + 2][j].setText(String.valueOf(gameTable[i + 2][j]));
					return true;
				} else if (Arrays.equals(variants5, chArray)) {
					gameTable[i + 1][j] = C;
					cells[i + 1][j].setText(String.valueOf(gameTable[i + 1][j]));
					return true;
				} else if (Arrays.equals(variants6, chArray)) {
					gameTable[i + 3][j] = C;
					cells[i + 3][j].setText(String.valueOf(gameTable[i + 3][j]));
					return true;
				}
			}
		}
		return false;
	}


}
