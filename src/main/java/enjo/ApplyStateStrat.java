package enjo;

import java.util.Iterator;
import java.util.Set;


public class ApplyStateStrat implements Strategy {

	@Override
	public boolean execute(Board b) {
		State s = b.getState();
		boolean changed = false;
		int n = b.getXBox()*b.getYBox();
		for (int x = 0; x < n; x++){
			for (int y = 0; y < n; y++){
				Set<Integer> pv = s.getPValues(x, y);
				if (pv.size() == 0) throw new IllegalStateException("The sudoku isn't solvable!");
				if (pv.size() != 1) continue;
				Iterator<Integer> it = pv.iterator();
				int value = it.next();
				s.updateState(x, y, value);
				changed = changed || b.getSquare(x, y).setV(value);
			}
		}
		return changed;
	}
	
	

}
