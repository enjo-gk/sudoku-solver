package enjogk;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import enjogk.Square.Unit;

public class LastPossibleSetStrat implements Strategy {

	@Override
	public boolean execute(Board b) {
		boolean changed = false;
		State state = b.getState();
		Set<Set<Square>> rowUnits = filterUnits(getRowUnits(b), state);
		Set<Set<Square>> colUnits = filterUnits(getColUnits(b), state);
		Set<Set<Square>> boxUnits = filterUnits(getBoxUnits(b), state);
		changed = checkRowsOrCols(rowUnits, state) || changed;
		changed = checkRowsOrCols(colUnits, state) || changed;
		changed = checkBoxs(boxUnits, state) || changed;
		return changed;
	}
	
	private boolean checkRowsOrCols(Set<Set<Square>> units, State state){
		boolean changed = false;
		// n iterations
		for (Set<Square> unit : units){
			if (unit.size() < 2) break;
			// A : Max n iterations
			for (Square s1 : unit) {
				// B : Same as A
				for (Square s2 : unit) {
					if (s1.equals(s2)) continue;
					Set<Integer> pvals1 = state.getPValues(s1.getX(), s1.getY());
					Set<Integer> pvals2 = state.getPValues(s2.getX(), s2.getY());
					if (pvals1.equals(pvals2) && 
							s1.getUnit(Unit.BOX).equals(s2.getUnit(Unit.BOX))){
						// n (9) iterations
						for (Square stateUpdateSquare : s1.getUnit(Unit.BOX)){
							if (stateUpdateSquare.equals(s1)) continue;
							if (stateUpdateSquare.equals(s2)) continue;
							changed = state.removePValues(stateUpdateSquare.getX(),
									stateUpdateSquare.getY(), pvals1) || changed;
						}
					}
				}
			}
		}
		// O(n^4) for the entire sudoku
		return changed;
	}
	
	private boolean checkBoxs(Set<Set<Square>> units, State state){
		boolean changed = false;
		// n iterations
		for (Set<Square> unit : units){
			if (unit.size() < 2) break;
			// A : Max n iterations
			for (Square s1 : unit) {
				// B : Same as A
				for (Square s2 : unit) {
					if (s1.equals(s2)) continue;
					Set<Integer> pvals1 = state.getPValues(s1.getX(), s1.getY());
					Set<Integer> pvals2 = state.getPValues(s2.getX(), s2.getY());
					if (!pvals1.equals(pvals2) ||
						!(s1.getUnit(Unit.ROW).equals(s2.getUnit(Unit.ROW)) ||
						 s1.getUnit(Unit.COL).equals(s2.getUnit(Unit.COL)))) {
						continue;
					}
					// n (9) iterations
					for (Square stateUpdateSquare : s1.getUnit(Unit.BOX)){
						if (stateUpdateSquare.equals(s1)) continue;
						if (stateUpdateSquare.equals(s2)) continue;
						changed = state.removePValues(stateUpdateSquare.getX(),
									stateUpdateSquare.getY(), pvals1) || changed;
					}
				}
			}
		}
		return changed;
	}
	
	private void filterUnit(Set<Square> unit, State state){
		Iterator<Square> it = unit.iterator();
		while (it.hasNext()){
			Square s = it.next();
			if (state.getPValues(s.getX(), s.getY()).size() >  2){
				it.remove();
			}
		}
	}
	
	private Set<Set<Square>> filterUnits(Set<Set<Square>> units, State state){
		for (Set<Square> unit : units){
			filterUnit(unit, state);
		}
		return units;
	}
	
	private Set<Set<Square>> getColUnits(Board b){
		Set<Set<Square>> colUnits = new HashSet<>();
		for (int x = 0; x < b.getXBox()*b.getYBox(); x++){
			colUnits.add(b.getSquare(x, 1).getUnit(Unit.COL));
		}
		return colUnits;
	}
	
	private Set<Set<Square>> getRowUnits(Board b){
		Set<Set<Square>> rowUnits = new HashSet<>();
		for (int y = 0; y < b.getXBox()*b.getYBox(); y++){
			rowUnits.add(b.getSquare(1, y).getUnit(Unit.ROW));
		}
		return rowUnits;
	}
	
	private Set<Set<Square>> getBoxUnits(Board b){
		Set<Set<Square>> boxUnits = new HashSet<>();
		int n = b.getXBox()*b.getYBox();
		for (int x = 0; x < n; x += b.getXBox()){
			for (int y = 0; y < n; y += b.getYBox()){
				boxUnits.add(b.getSquare(x, y).getUnit(Unit.BOX));
			}
		}
		return boxUnits;
	}

	
	
}
