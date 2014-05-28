package enjo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import enjo.Board;
import enjo.Solver;
import enjo.Strategy;


public class SolverTest {

	@Test
	public void testSolverNoStratsSimple() {
		String s = "376...2.4"
				+ ".2.6375.."
				+ "9..28.76."
				+ ".1.863.9."
				+ "24...5.36"
				+ ".83..91.7"
				+ "4.859.6.."
				+ "1.73...25"
				+ "..2.16.48";
		Board board = new Board(s);
		List<Strategy> usedStrats = new ArrayList<Strategy>();
		Solver solver = new Solver(usedStrats);
		solver.solve(board);
		assertTrue(solver.solved(board));
	}
	
	@Test
	//Easiest Sudoku @ SudokuWiki.org
	public void testSolverNoStratsNormal() {
		String s = "...1.5..."
				+ "14....67."
				+ ".8...24.."
				+ ".63.7..1."
				+ "9.......3"
				+ ".1..9.52."
				+ "..72...8."
				+ ".26....35"
				+ "...4.9...";
		Board board = new Board(s);
		List<Strategy> usedStrats = new ArrayList<Strategy>();
		Solver solver = new Solver(usedStrats);
		solver.solve(board);
		assertTrue(solver.solved(board));
	}
	
	@Test
	// This one is to hard to be solved without strats
	public void testSolverNoStratsHarder() {
		String s = ".2..3...."
				+ "1....6..3"
				+ "..67.21.."
				+ ".63...7.."
				+ "9..5.8..2"
				+ "..4...96."
				+ "..54.98.."
				+ "8..6....4"
				+ "....7..3.";
		Board board = new Board(s);
		List<Strategy> usedStrats = new ArrayList<Strategy>();
		Solver solver = new Solver(usedStrats);
		solver.solve(board);
		assertFalse(solver.solved(board));
	}

}
