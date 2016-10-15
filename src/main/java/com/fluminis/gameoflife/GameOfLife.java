package com.fluminis.gameoflife;

public class GameOfLife {

	private int rowCount;
	private int columnCount;
	private byte[][] game;

	public GameOfLife(String state) {
		if (state == null) {
			throw new IllegalArgumentException("state should not be null");
		}
		String[] lines = extractLines(state);
		extractGameSizes(lines);

		game = new byte[rowCount + 2][columnCount + 2];
		for (int y = 1; y < lines.length; y++) {
			String line = lines[y];
			for (int x = 0; x < line.length(); x++) {
				game[y][x + 1] = (byte) (line.charAt(x) == '*' ? 1 : 0);
			}
		}
	}

	private GameOfLife(byte[][] game, int rowCount, int columnCount) {
		this.game = game;
		this.columnCount = columnCount;
		this.rowCount = rowCount;
	}

	private void extractGameSizes(String[] lines) {
		String[] gameSizes = lines[0].split(" ");
		if (gameSizes.length != 2) {
			throw new IllegalArgumentException("first line should contain two integers");
		}
		rowCount = Integer.parseInt(gameSizes[0], 10);
		columnCount = Integer.parseInt(gameSizes[1], 10);
	}

	private String[] extractLines(String state) {
		String[] lines = state.split("\n");
		if (lines.length == 0) {
			throw new IllegalArgumentException("state should contain lines break");
		}
		return lines;
	}

	public String display() {
		StringBuilder sb = new StringBuilder();
		sb.append(rowCount).append(" ").append(columnCount).append("\n");
		for (int row = 0; row < rowCount; row++) {
			for (int column = 0; column < columnCount; column++) {
				sb.append(game[row + 1][column + 1] == (byte) 1 ? "*" : ".");
			}
			sb.append("\n");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	public int rowCount() {
		return rowCount;
	}

	public int columnCount() {
		return columnCount;
	}

	int aliveNeighbours(int row, int column) {
		int line1 = game[row][column] + game[row][column + 1] + game[row][column + 2];
		row++;
		int line2 = game[row][column] + /* game[y][x+1] + */ game[row][column + 2];
		row++;
		int line3 = game[row][column] + game[row][column + 1] + game[row][column + 2];
		return line1 + line2 + line3;
	}

	byte nextGeneration(int row, int column) {
		byte current = game[row + 1][column + 1];
		int aliveNeighbours = aliveNeighbours(row, column);
		if (current == 1) {
			if (aliveNeighbours < 2 || aliveNeighbours > 3) {
				// 1. fewer than two live neighbours dies
				// 2. more than three live neighbours dies
				return 0;
			}
			// 3. with two or three live neighbours lives on to
			// the next generation.
			return 1;
		} else {
			if (aliveNeighbours == 3) {
				// 4. Any dead cell with exactly three live neighbours becomes a
				// live cell.
				return 1;
			} else {
				return 0;
			}
		}
	}

	public GameOfLife nextGeneration() {
		byte[][] newgame = new byte[rowCount + 2][columnCount + 2];
		for (int row = 0; row < rowCount; row++) {
			for (int column = 0; column < columnCount; column++) {
				newgame[row + 1][column + 1] = nextGeneration(row, column);
			}
		}
		return new GameOfLife(newgame, rowCount, columnCount);
	}
}
