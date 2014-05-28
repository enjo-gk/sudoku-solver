package enjogk;

import java.util.HashSet;
import java.util.Set;


public class Square {
	
	public static enum Unit {
		ROW, COL, BOX;
	}
	
	private int v;
	private int x, y;
	private Set<Square> rowU;
	private Set<Square> colU;
	private Set<Square> boxU;
	
	public Square(int value, int x, int y){
		v = value;
		this.x = x;
		this.y = y;
	}
	
	public void setRowUnit(Set<Square> rowU){
		this.rowU = rowU;
	}
	
	public void setColUnit(Set<Square> colU){
		this.colU = colU;
	}
	
	public void setBoxUnit(Set<Square> boxU){
		this.boxU = boxU;
	}
	
	public Set<Square> getUnit(Unit u){
		switch(u){
		case ROW:
			return new HashSet<Square>(rowU);
		case COL:
			return new HashSet<Square>(colU);
		case BOX:
			return new HashSet<Square>(boxU);
		default:
			throw new IllegalArgumentException("No valid unit specified!");
		}
	}
	
	public Set<Square> getPeers(Unit u){
		Set<Square> unit = getUnit(u);
		unit.remove(this);
		return unit;
	}
	
	public int getV(){
		return v;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public boolean setV(int value){
		if (value != v && v != 0){
			throw new IllegalStateException(String.format(
					"Trying to override %d with %d!", v,  value));
		}
		boolean changed = v == 0 && value != 0;
		if (changed) v = value;
		return changed;
	}
	
	@Override
	public String toString(){
		return String.format("%d", v);
	}

}
