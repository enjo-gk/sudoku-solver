package enjogk;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;


//TODO: Check against manually created board and state
public class LastPossibleSetStratTest {

	@Test
	public void testRow() {
		Board b = new Board("..34" +
							"...." + 
							"...." +
							"43..");
		List<Strategy> strats = new LinkedList<>();
		strats.add(new LastPossibleSetStrat());
		Solver s = new Solver(strats);
		s.solve(b);
		assertEquals(false, s.solved(b));
	}
	
	@Test
	public void testCol() {
		Board b = new Board("4..." +
							"3..." + 
							"...3" +
							"...4");
		List<Strategy> strats = new LinkedList<>();
		strats.add(new LastPossibleSetStrat());
		Solver s = new Solver(strats);
		s.solve(b);
		assertEquals(false, s.solved(b));
	}
	
	@Test
	public void testBoxRow() {
		Board b = new Board("4..." +
							"3..." + 
							"...." +
							"....");
		List<Strategy> strats = new LinkedList<>();
		strats.add(new LastPossibleSetStrat());
		Solver s = new Solver(strats);
		s.solve(b);
		assertEquals(false, s.solved(b));
	}
	
	@Test
	public void testBoxCol() {
		Board b = new Board("43.." +
							"...." + 
							"...." +
							"....");
		List<Strategy> strats = new LinkedList<>();
		strats.add(new LastPossibleSetStrat());
		Solver s = new Solver(strats);
		s.solve(b);
		assertEquals(false, s.solved(b));
	}
	
	@Test
	public void testSudoku() {
		String s = "8...9...2"
				+ "7......9."
				+ "..92....5"
				+ "64.8....."
				+ "2.51.69.7"
				+ ".....5.86"
				+ "4....27.."
				+ ".6......1"
				+ "9...6...4";
		Board board = new Board(s);
		List<Strategy> strats = new LinkedList<>();
		strats.add(new LastPossibleSquareStrat());
		strats.add(new LastPossibleSetStrat());
		Solver solver = new Solver(strats);
		solver.solve(board);
		assertEquals(false, solver.solved(board));
	}

}
