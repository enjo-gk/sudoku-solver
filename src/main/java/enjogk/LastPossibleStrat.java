package enjogk;

import java.util.Set;

import enjogk.Square.Unit;

public class LastPossibleStrat implements Strategy {

	@Override
	public boolean execute(Board b) {
		boolean changed = false;
		State state = b.getState();
		for (Square s : b){
			Set<Square> boxPeers = s.getPeers(Unit.BOX);
			Set<Square> rowPeers = s.getPeers(Unit.ROW);
			Set<Square> colPeers = s.getPeers(Unit.COL);
			boolean lastPossible = false;
			for (int pvalue : state.getPValues(s.getX(), s.getY())){
				lastPossible = lastPossible(pvalue, boxPeers, state) ||
						  lastPossible(pvalue, rowPeers, state) ||
						  lastPossible(pvalue, colPeers, state);
				if (lastPossible) {
					changed = changed || s.setV(pvalue);
					state.updateState(s.getX(), s.getY(), pvalue);
					break;
				}
			}
		}
		return changed;
	}
	
	private boolean lastPossible(int pvalue, Set<Square> peers, State state){
		for (Square s : peers){
			Set<Integer> pvalues = state.getPValues(s.getX(), s.getY());
			if (pvalues.contains(pvalue)) return false;
		}
		return true;
	}

}
