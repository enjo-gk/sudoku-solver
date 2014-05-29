package enjogk;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class State {
	
	private Board board;
	private List<Set<Integer>> pValues;
	private int n;
	
	public State(Board b){
		board = b;
		n = b.getXBox()*b.getYBox();
		pValues = new ArrayList<Set<Integer>>();
		for (int y = 0; y < n; y++){
			for (int x = 0; x < n; x++){
				Set<Integer> pvset = new HashSet<Integer>();
				int currentValue = b.getSquare(x, y).getV();
				if (currentValue != 0){
					pvset.add(currentValue);
				}
				else {
					for (int i = 1; i <= n; i ++){
						pvset.add(i);
					}
					Set<Square> peers = new HashSet<Square>();
					Square square = b.getSquare(x, y);
					peers.addAll(square.getUnit(Square.Unit.ROW));
					peers.addAll(square.getUnit(Square.Unit.COL));
					peers.addAll(square.getUnit(Square.Unit.BOX));
					for (Square s : peers){
						pvset.remove(s.getV());
					}
				}
				pValues.add(pvset);
			}
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + n;
		result = prime * result + ((pValues == null) ? 0 : pValues.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		State other = (State) obj;
		if (n != other.n)
			return false;
		if (pValues == null) {
			if (other.pValues != null)
				return false;
		} else if (!pValues.equals(other.pValues))
			return false;
		return true;
	}

	public Set<Integer> getPValues(int x, int y){
		return new HashSet<Integer>(pValues.get(y*n+x));
	}
	
	public boolean removePValues(int x, int y, Set<Integer> values){
		return pValues.get(y*n+x).removeAll(values);
	}
	
	public void updateState(int x, int y, int v){
		Set<Square> peers = new HashSet<Square>();
		Square square = board.getSquare(x, y);
		peers.addAll(square.getUnit(Square.Unit.ROW));
		peers.addAll(square.getUnit(Square.Unit.COL));
		peers.addAll(square.getUnit(Square.Unit.BOX));
		peers.remove(square);
		for (Square s : peers){
			pValues.get(s.getY()*n+s.getX()).remove(v);
		}
	}
	
}
