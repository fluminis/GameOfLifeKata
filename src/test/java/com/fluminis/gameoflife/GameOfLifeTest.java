package com.fluminis.gameoflife;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class GameOfLifeTest {

	private static final byte DEAD_CELL = (byte) 0;
	private static final byte ALIVE_CELL = (byte) 1;

	private final static String INITIAL_STATE = "4 8\n" + //
			"........\n" + //
			"....*...\n" + //
			"...**...\n" + //
			"........";

	private final static String NEXT_STATE = "4 8\n" + //
			"........\n" + //
			"...**...\n" + //
			"...**...\n" + //
			"........";

	private final static String STATE_0 = "3 3\n" + //
			"...\n" + //
			"***\n" + //
			"...";
	private final static String STATE_1 = "3 3\n" + //
			".*.\n" + //
			".*.\n" + //
			".*.";
	private final static String SMALL_INITIAL_STATE = "3 4\n" + //
			"..*.\n" + //
			"..*.\n" + //
			"....\n";

	private final static String EMPTY_STATE = "3 3\n" + //
			"...\n" + //
			"...\n" + //
			"...";

	@Test
	public void shouldDisplayTheCurrentState() {
		GameOfLife game = new GameOfLife(INITIAL_STATE);
		assertThat(game.display()).isEqualTo(INITIAL_STATE);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIfStateNull() {
		new GameOfLife(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIfStateDoesntStartWithGameSize() {
		new GameOfLife("........\n" + //
				"....*...\n" + //
				"...**...\n" + //
				"........");
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIfFirstLineHaveMoreThanTwoNumbers() {
		new GameOfLife("1 2 3" + //
				"........\n" + //
				"....*...\n" + //
				"...**...\n" + //
				"........");
	}

	@Test
	public void shouldExtractSizeOfGameFromState() {
		GameOfLife game = new GameOfLife(INITIAL_STATE);
		assertThat(game.rowCount()).isEqualTo(4);
		assertThat(game.columnCount()).isEqualTo(8);
	}

	@SuppressWarnings("unused")
	private Object[] parametersForShouldCountCellAliveNeighbours() {
		return new Object[][] { //
				{ 0, 0, 0 }, //
				{ 0, 1, 2 }, //
				{ 1, 1, 2 }, //
				{ 0, 2, 1 } };
	}

	@Test
	@Parameters
	public void shouldCountCellAliveNeighbours(int row, int column, int nbAliveCells) {
		GameOfLife game = new GameOfLife(SMALL_INITIAL_STATE);
		assertThat(game.aliveNeighbours(row, column)).isEqualTo(nbAliveCells);
	}

	@SuppressWarnings("unused")
	private Object[] parametersForShouldCalculateNextGenerationOfCell() {
		return new Object[][] { //
				{ 0, 0, 0 }, //
				{ 1, 3, 1 }, //
				{ 2, 3, 1 } };
	}

	@Test
	@Parameters
	public void shouldCalculateNextGenerationOfCell(int row, int column, int nextState) {
		GameOfLife game = new GameOfLife(INITIAL_STATE);
		assertThat(game.nextGeneration(row, column)).isEqualTo((byte) nextState);
	}

	@Test
	public void shouldCalculateNextGeneration() {
		GameOfLife game = new GameOfLife(INITIAL_STATE);
		GameOfLife next = game.nextGeneration();
		assertThat(next.display()).isEqualTo(NEXT_STATE);
	}

	@Test
	public void shouldCalculateNextGeneration3x3() {
		GameOfLife game = new GameOfLife(STATE_0).nextGeneration();
		assertThat(game.display()).isEqualTo(STATE_1);
		game = game.nextGeneration();
		assertThat(game.display()).isEqualTo(STATE_0);
	}

	@Test
	public void shouldDeadCellWithZeroAliveNeighbourgStayDead() {
		GameOfLife game = new GameOfLife(EMPTY_STATE);
		assertThat(game.nextGeneration(1, 1)).isEqualTo(DEAD_CELL);
	}

	@Test
	public void shouldDeadCellWithOneAliveNeighbourgStayDead() {
		GameOfLife game = new GameOfLife(EMPTY_STATE);
		game.setCellState(0, 0, ALIVE_CELL);
		assertThat(game.nextGeneration(1, 1)).isEqualTo(DEAD_CELL);
	}

	@Test
	public void shouldDeadCellWithTwoAliveNeighbourgStayDead() {
		GameOfLife game = new GameOfLife(EMPTY_STATE);
		game.setCellState(0, 0, ALIVE_CELL);
		game.setCellState(0, 1, ALIVE_CELL);
		assertThat(game.nextGeneration(1, 1)).isEqualTo(DEAD_CELL);
	}

	@Test
	public void shouldDeadCellWithThreeAliveNeighbourgBecomeAlive() {
		GameOfLife game = new GameOfLife(EMPTY_STATE);
		game.setCellState(0, 0, ALIVE_CELL);
		game.setCellState(0, 1, ALIVE_CELL);
		game.setCellState(0, 2, ALIVE_CELL);
		assertThat(game.nextGeneration(1, 1)).isEqualTo(ALIVE_CELL);
	}

	@Test
	public void shouldDeadCellWithFourAliveNeighbourgStayDead() {
		GameOfLife game = new GameOfLife(EMPTY_STATE);
		game.setCellState(0, 0, ALIVE_CELL);
		game.setCellState(0, 1, ALIVE_CELL);
		game.setCellState(0, 2, ALIVE_CELL);
		game.setCellState(1, 0, ALIVE_CELL);
		assertThat(game.nextGeneration(1, 1)).isEqualTo(DEAD_CELL);
	}

	@Test
	public void shouldDeadCellWithFiveAliveNeighbourgStayDead() {
		GameOfLife game = new GameOfLife(EMPTY_STATE);
		game.setCellState(0, 0, ALIVE_CELL);
		game.setCellState(0, 1, ALIVE_CELL);
		game.setCellState(0, 2, ALIVE_CELL);
		game.setCellState(1, 0, ALIVE_CELL);
		game.setCellState(1, 2, ALIVE_CELL);
		assertThat(game.nextGeneration(1, 1)).isEqualTo(DEAD_CELL);
	}

	@Test
	public void shouldDeadCellWithSixAliveNeighbourgStayDead() {
		GameOfLife game = new GameOfLife(EMPTY_STATE);
		game.setCellState(0, 0, ALIVE_CELL);
		game.setCellState(0, 1, ALIVE_CELL);
		game.setCellState(0, 2, ALIVE_CELL);
		game.setCellState(1, 0, ALIVE_CELL);
		game.setCellState(1, 2, ALIVE_CELL);
		game.setCellState(2, 2, ALIVE_CELL);
		assertThat(game.nextGeneration(1, 1)).isEqualTo(DEAD_CELL);
	}

	@Test
	public void shouldDeadCellWithSevenAliveNeighbourgStayDead() {
		GameOfLife game = new GameOfLife(EMPTY_STATE);
		game.setCellState(0, 0, ALIVE_CELL);
		game.setCellState(0, 1, ALIVE_CELL);
		game.setCellState(0, 2, ALIVE_CELL);
		game.setCellState(1, 0, ALIVE_CELL);
		game.setCellState(1, 2, ALIVE_CELL);
		game.setCellState(2, 0, ALIVE_CELL);
		game.setCellState(2, 1, ALIVE_CELL);
		assertThat(game.nextGeneration(1, 1)).isEqualTo(DEAD_CELL);
	}

	@Test
	public void shouldDeadCellWithEightAliveNeighbourgStayDead() {
		GameOfLife game = new GameOfLife(EMPTY_STATE);
		game.setCellState(0, 0, ALIVE_CELL);
		game.setCellState(0, 1, ALIVE_CELL);
		game.setCellState(0, 2, ALIVE_CELL);
		game.setCellState(1, 0, ALIVE_CELL);
		game.setCellState(1, 2, ALIVE_CELL);
		game.setCellState(2, 0, ALIVE_CELL);
		game.setCellState(2, 1, ALIVE_CELL);
		game.setCellState(2, 2, ALIVE_CELL);
		assertThat(game.nextGeneration(1, 1)).isEqualTo(DEAD_CELL);
	}

	@Test
	public void shouldAliveCellWithZeroAliveNeighbourgDies() {
		GameOfLife game = new GameOfLife(EMPTY_STATE);
		game.setCellState(1, 1, ALIVE_CELL);
		assertThat(game.nextGeneration(1, 1)).isEqualTo(DEAD_CELL);
	}

	@Test
	public void shouldAliveCellWithOneAliveNeighbourgDies() {
		GameOfLife game = new GameOfLife(EMPTY_STATE);
		game.setCellState(1, 1, ALIVE_CELL);
		game.setCellState(0, 0, ALIVE_CELL);
		assertThat(game.nextGeneration(1, 1)).isEqualTo(DEAD_CELL);
	}

	@Test
	public void shouldAliveCellWithTwoAliveNeighbourgsStayAlive() {
		GameOfLife game = new GameOfLife(EMPTY_STATE);
		game.setCellState(1, 1, ALIVE_CELL);
		game.setCellState(0, 0, ALIVE_CELL);
		game.setCellState(0, 1, ALIVE_CELL);
		assertThat(game.nextGeneration(1, 1)).isEqualTo(ALIVE_CELL);
	}

	@Test
	public void shouldAliveCellWithThreeAliveNeighbourgsStayAlive() {
		GameOfLife game = new GameOfLife(EMPTY_STATE);
		game.setCellState(1, 1, ALIVE_CELL);
		game.setCellState(0, 0, ALIVE_CELL);
		game.setCellState(0, 1, ALIVE_CELL);
		game.setCellState(0, 2, ALIVE_CELL);
		assertThat(game.nextGeneration(1, 1)).isEqualTo(ALIVE_CELL);
	}

	@Test
	public void shouldAliveCellWithFourAliveNeighbourgsDies() {
		GameOfLife game = new GameOfLife(EMPTY_STATE);
		game.setCellState(1, 1, ALIVE_CELL);
		game.setCellState(0, 0, ALIVE_CELL);
		game.setCellState(0, 1, ALIVE_CELL);
		game.setCellState(0, 2, ALIVE_CELL);
		game.setCellState(1, 0, ALIVE_CELL);
		assertThat(game.nextGeneration(1, 1)).isEqualTo(DEAD_CELL);
	}

	@Test
	public void shouldAliveCellWithFiveAliveNeighbourgsDies() {
		GameOfLife game = new GameOfLife(EMPTY_STATE);
		game.setCellState(1, 1, ALIVE_CELL);
		game.setCellState(0, 0, ALIVE_CELL);
		game.setCellState(0, 1, ALIVE_CELL);
		game.setCellState(0, 2, ALIVE_CELL);
		game.setCellState(1, 0, ALIVE_CELL);
		game.setCellState(1, 2, ALIVE_CELL);
		assertThat(game.nextGeneration(1, 1)).isEqualTo(DEAD_CELL);
	}

	@Test
	public void shouldAliveCellWithSixAliveNeighbourgsDies() {
		GameOfLife game = new GameOfLife(EMPTY_STATE);
		game.setCellState(1, 1, ALIVE_CELL);
		game.setCellState(0, 0, ALIVE_CELL);
		game.setCellState(0, 1, ALIVE_CELL);
		game.setCellState(0, 2, ALIVE_CELL);
		game.setCellState(1, 0, ALIVE_CELL);
		game.setCellState(1, 2, ALIVE_CELL);
		game.setCellState(2, 0, ALIVE_CELL);
		assertThat(game.nextGeneration(1, 1)).isEqualTo(DEAD_CELL);
	}

	@Test
	public void shouldAliveCellWithSevenAliveNeighbourgsDies() {
		GameOfLife game = new GameOfLife(EMPTY_STATE);
		game.setCellState(1, 1, ALIVE_CELL);
		game.setCellState(0, 0, ALIVE_CELL);
		game.setCellState(0, 1, ALIVE_CELL);
		game.setCellState(0, 2, ALIVE_CELL);
		game.setCellState(1, 0, ALIVE_CELL);
		game.setCellState(1, 2, ALIVE_CELL);
		game.setCellState(2, 0, ALIVE_CELL);
		game.setCellState(2, 1, ALIVE_CELL);
		assertThat(game.nextGeneration(1, 1)).isEqualTo(DEAD_CELL);
	}

	@Test
	public void shouldAliveCellWithEightAliveNeighbourgsDies() {
		GameOfLife game = new GameOfLife(EMPTY_STATE);
		game.setCellState(1, 1, ALIVE_CELL);
		game.setCellState(0, 0, ALIVE_CELL);
		game.setCellState(0, 1, ALIVE_CELL);
		game.setCellState(0, 2, ALIVE_CELL);
		game.setCellState(1, 0, ALIVE_CELL);
		game.setCellState(1, 2, ALIVE_CELL);
		game.setCellState(2, 0, ALIVE_CELL);
		game.setCellState(2, 1, ALIVE_CELL);
		assertThat(game.nextGeneration(1, 1)).isEqualTo(DEAD_CELL);
	}
}
