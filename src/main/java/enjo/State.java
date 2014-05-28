package enjo;

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
	
	public Set<Integer> getPValues(int x, int y){
		return pValues.get(y*n+x);
	}
	
	public void removePValues(int x, int y, Set<Integer> values){
		pValues.get(y*n+x).removeAll(values);
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
