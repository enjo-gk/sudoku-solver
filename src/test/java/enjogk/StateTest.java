package enjogk;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import enjogk.Board;
import enjogk.State;


public class StateTest {
	
	private Board board;

	@Before
	public void setUp() throws Exception {
		String s = "123456789"
				+ "456789123"
				+ "789123456"
				+ "2..56789."
				+ "56789.2.4"
				+ "8..234567"
				+ "345678912"
				+ "678912345"
				+ "912345678";
		board = new Board(s);
	}

	@Test
	public void testConstructor13() {
		State state = board.getState();
		Set<Integer> expected = new HashSet<Integer>();
		expected.add(3);
		assertEquals(expected, state.getPValues(1, 3));
	}
	
	@Test
	public void testConstructor32() {
		State state = board.getState();
		Set<Integer> expected = new HashSet<Integer>();
		expected.add(4);
		expected.add(1);
		assertEquals(expected, state.getPValues(2, 3));
	}

}
