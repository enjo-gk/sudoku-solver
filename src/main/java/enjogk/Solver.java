package enjogk;

import java.util.List;


public class Solver {
	
	List<Strategy> strats;
	
	public Solver(List<Strategy> strats){
		strats.add(0, new ApplyStateStrat());
		this.strats = strats;
	}
	
	public void solve(Board b){
		boolean fixpoint;
		do {
			fixpoint = true;
			for (Strategy strat : strats){
				 if (strat.execute(b)){
					 fixpoint = false;
					 break;
				 };
			}
		} while (!fixpoint);
	}
	
	public boolean solved(Board b){
		State s = b.getState();
		int n = b.getXBox()*b.getYBox();
		for (int x = 0; x < n; x++){
			for (int y = 0; y < n; y++){
				if (s.getPValues(x, y).size() == 0)
					throw new IllegalStateException("Unsolvable Sudoku!");
				if (s.getPValues(x, y).size() != 1)
					return false;
			}
		}
		return true;
	}

}
