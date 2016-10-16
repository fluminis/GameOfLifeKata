package com.fluminis.gameoflife;

public class GameOfLife {

	private static final byte DEAD_CELL = (byte) 0;
	private static final byte ALIVE_CELL = (byte) 1;

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
				byte cellState = (byte) (line.charAt(x) == '*' ? 1 : 0);
				setCellState(y - 1, x, cellState);
			}
		}
	}

	void setCellState(int row, int column, byte cellState) {
		game[row + 1][column + 1] = cellState;
	}

	byte cellState(int row, int column) {
		return game[row + 1][column + 1];
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

	public GameOfLife nextGeneration() {
		byte[][] newgame = new byte[rowCount + 2][columnCount + 2];
		for (int row = 0; row < rowCount; row++) {
			for (int column = 0; column < columnCount; column++) {
				newgame[row + 1][column + 1] = nextGeneration(row, column);
			}
		}
		return new GameOfLife(newgame, rowCount, columnCount);
	}

	byte nextGeneration(int row, int column) {
		int aliveNeighbours = aliveNeighbours(row, column);
		byte cellState = cellState(row, column);
		if (cellState == ALIVE_CELL && aliveNeighbours == 2) {
			return ALIVE_CELL;
		}
		if (aliveNeighbours == 3) {
			return ALIVE_CELL;
		}
		return DEAD_CELL;
	}
}
