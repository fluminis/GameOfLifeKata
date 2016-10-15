package com.fluminis.gameoflife;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class GameOfLifeTest {

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

	@Test
	public void shouldDisplayTheCurrentState() {
		GameOfLife game = new GameOfLife(INITIAL_STATE);
		assertThat(game.display()).isEqualTo(INITIAL_STATE);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIfStateNull() {
		GameOfLife game = new GameOfLife(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIfStateDoesntStartWithGameSize() {
		GameOfLife game = new GameOfLife("........\n" + //
				"....*...\n" + //
				"...**...\n" + //
				"........");
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIfFirstLineHaveMoreThanTwoNumbers() {
		GameOfLife game = new GameOfLife("1 2 3" + //
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
}
