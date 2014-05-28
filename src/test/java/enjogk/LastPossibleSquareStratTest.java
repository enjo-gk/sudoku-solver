package enjogk;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public class LastPossibleSquareStratTest {

	@Test
	public void test() {
		Board b = new Board(".24." +
							"...." + 
							"41.." +
							"....");
		List<Strategy> strats = new LinkedList<>();
		strats.add(new LastPossibleSquareStrat());
		Solver s = new Solver(strats);
		s.solve(b);
		assertEquals(false, s.solved(b));
	}
	
	@Test
	public void testIterator(){
		int i = 0;
		Board b = new Board(".24." +
				"...." + 
				"41.." +
				"....");
		for (Square s : b){
			i ++;
		}
		assertEquals(16, i);
	}

}
