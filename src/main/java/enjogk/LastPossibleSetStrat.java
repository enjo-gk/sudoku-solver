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
		changed = checkChanged(rowUnits, state, Unit.BOX) || changed;
		changed = checkChanged(colUnits, state, Unit.BOX) || changed;
		changed = checkChanged(boxUnits, state, Unit.ROW) || changed;
		changed = checkChanged(boxUnits, state, Unit.COL) || changed;
		return changed;
	}
	
	private boolean checkChanged(Set<Set<Square>> units, State state, Unit dstUnit){
		boolean changed = false;
		// n iterations
		for (Set<Square> unit : units){
			if (unit.size() < 2) break;
			for (Square s1 : unit){
				Set<Square> pairUnit = new HashSet<>(unit);
				pairUnit.remove(s1);
				for (Square s2 : pairUnit) {
					changed = handlePairs(s1, s2, state, dstUnit)
							  || changed;
					if (pairUnit.size() > 1){
						Set<Square> tripleUnit = new HashSet<>(pairUnit);
						tripleUnit.remove(s2);
						for (Square s3 : tripleUnit){
							changed = handleTriple(s1, s2, s3, state, dstUnit)
									  || changed;
						}
					}
				}
			}
		}
		return changed;
	}
	
	private boolean handleTriple(Square s1, Square s2, Square s3, State state, Unit dstUnit){
		boolean changed = false;
		Set<Integer> pvalUnion = state.getPValues(s1.getX(), s1.getY());
		pvalUnion.addAll(state.getPValues(s2.getX(), s2.getY()));
		pvalUnion.addAll(state.getPValues(s3.getX(), s3.getY()));
		if (pvalUnion.size() == 3
			&& s1.getUnit(dstUnit).equals(s2.getUnit(dstUnit))
			&& s2.getUnit(dstUnit).equals(s3.getUnit(dstUnit))){
			Set<Square> peers = s1.getPeers(dstUnit);
			peers.remove(s2);
			peers.remove(s3);
			for (Square s : peers){
				changed = state.removePValues(s.getX(), s.getY(), pvalUnion) || changed;
			}
		}
		return changed;
	}
	
	private boolean handlePairs(Square s1, Square s2, State state, Unit dstUnit){
		boolean changed = false;
		Set<Integer> pvalUnion = state.getPValues(s1.getX(), s1.getY());
		pvalUnion.addAll(state.getPValues(s2.getX(), s2.getY()));
		if (pvalUnion.size() == 2 && s1.getUnit(dstUnit).equals(s2.getUnit(dstUnit))){
			Set<Square> peers = s1.getPeers(dstUnit);
			peers.remove(s2);
			for (Square s : peers){
				changed = state.removePValues(s.getX(), s.getY(), pvalUnion) || changed;
			}
		}
		return changed;
	}
	
	private void filterUnit(Set<Square> unit, State state){
		Iterator<Square> it = unit.iterator();
		while (it.hasNext()){
			Square s = it.next();
			if (state.getPValues(s.getX(), s.getY()).size() >  3){
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

	public String toString(){
		return "LPSet";
	}
	
}
