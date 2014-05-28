package enjogk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Board implements Iterable<Square> {
	
	public static enum Direction {
		UP, LEFT, DOWN, RIGHT
	}

	private State state;
	private Square[][] squares;
	private int xBox, yBox;
	
	
	public Board(int xBox, int yBox){
		this.xBox = xBox;
		this.yBox = yBox;
		int n = xBox*yBox;
		squares = new Square[n][n];
		for (int x = 0; x < n; x++){
			for (int y = 0; y < n; y++){
				squares[x][y] = new Square(0, x, y);
			}
		}
		initRowUnits();
		initColUnits();
		initBoxUnits();
	}
	
	public Board(String s){
		calculateXBoxYBox((int) Math.sqrt(s.length()));
		int n = xBox*yBox;
		squares = new Square[n][n];
		for (int x = 0; x < n; x++){
			for (int y = 0; y < n; y++){
				squares[x][y] = new Square(0, x, y);
			}
		}
		initRowUnits();
		initColUnits();
		initBoxUnits();
		parseGameState(s);
	}
	
	private void initColUnits(){
		List<Set<Square>> colUnits = new ArrayList<Set<Square>>();
		for (int x = 0; x < xBox*yBox; x++){
			Set<Square> colUnit = new HashSet<Square>();
			for (int y = 0; y < xBox*yBox; y++){
				colUnit.add(squares[x][y]);
			}
			colUnits.add(colUnit);
		}
		for (int x = 0; x < xBox*yBox; x++){
			for (int y = 0; y < xBox*yBox; y++){
				squares[x][y].setColUnit(colUnits.get(x));
			}
		}
	}
	
	private void initRowUnits(){
		List<Set<Square>> rowUnits = new ArrayList<Set<Square>>();
		for (int y = 0; y < xBox*yBox; y++){
			Set<Square> rowUnit = new HashSet<Square>();
			for (int x = 0; x < xBox*yBox; x++){
				rowUnit.add(squares[x][y]);
			}
			rowUnits.add(rowUnit);
		}
		for (int y = 0; y < xBox*yBox; y++){
			for (int x = 0; x < xBox*yBox; x++){
				squares[x][y].setRowUnit(rowUnits.get(y));
			}
		}
	}
	
	private void initBoxUnits(){
		Map<Integer, Set<Square>> boxUnits = new HashMap<>();
		for (int x = 0; x < xBox*yBox; x++){
			for (int y = 0; y < xBox*yBox; y++){
				int xp = x/xBox;
				int yp = y/yBox;
				int key = yp * yBox + xp;
				if (boxUnits.get(key) == null){
					boxUnits.put(key, new HashSet<Square>());
				}
				boxUnits.get(key).add(squares[x][y]);
			}
		}
		for (int x = 0; x < xBox*yBox; x++){
			for (int y = 0; y < xBox*yBox; y++){
				int xp = x/xBox;
				int yp = y/yBox;
				int key = yp * yBox + xp;
				squares[x][y].setBoxUnit(boxUnits.get(key));
			}
		}	
	}
	
	private void parseGameState(String s){
		int n = (int) Math.sqrt(s.length());
		calculateXBoxYBox(n);
		for (int i = 0; i < s.length(); i++){
			char c = s.charAt(i);
			if (c == '.'){
				squares[i%n][i/n].setV(0);
			}
			else if (Character.isDigit(c)){
				squares[i%n][i/n].setV(Character.getNumericValue(c));
			}
			else throw new IllegalArgumentException("Invalid game state representation!");
		}
	}
	
	private void calculateXBoxYBox(int n) {
		double sqrt = Math.sqrt(n);
		if (Math.round(sqrt) == sqrt){
			xBox = (int) sqrt;
			yBox = (int) sqrt;
			return;
		}
		for (int i = (int) Math.floor(sqrt); i > 0;  i--){
			if (n % i == 0){
				xBox = n/i;
				yBox = i;
				return;
			}
		}
	}

	public Square getSquare(int x, int y){
		return squares[x][y];
	}
	
	public Square getNeighbour(Square s, Direction d){
		int y = s.getY();
		int x = s.getX();
		switch (d) {
		case UP:
			if (y > 0) return squares[x][y-1];
			break;
		case DOWN:
			if (y < xBox*yBox-1) return squares[x][y+1];
			break;
		case LEFT:
			if (x > 0) return squares[x-1][y];
			break;
		case RIGHT:
			if (x < xBox*yBox-1) return squares[x+1][y];
			break;
		}
		return null;
	}
	
	@Override
	public String toString(){
		StringBuilder osb = new StringBuilder();
		for (int y = 0; y < xBox*yBox; y++){
			if (osb.length() > 0){
				osb.append("\n");
			}
			StringBuilder isb = new StringBuilder();
			for (int x = 0; x < xBox*yBox; x++){
				if (isb.length() > 0){
					isb.append(", ");
				}
				isb.append(squares[x][y]);
			}
			osb.append(isb.toString());
		}
		return osb.toString();
	}
	
	public State getState(){
		if (state == null)
			state = new State(this);
		return state;
	}
	
	public int getXBox(){
		return xBox;
	}
	
	public int getYBox(){
		return yBox;
	} 

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public Iterator<Square> iterator() {
		return new Iterator<Square>() {
			
			int x = 0;
			int y = 0;

			@Override
			public boolean hasNext() {
				int n = xBox * yBox;
				return x <= n-1 && y <= n-1;
			}

			@Override
			public Square next() {
				int n = xBox * yBox;
				Square s = squares[x][y];
				y = ((x + 1) / n == 1)? y + 1 : y;
				x = (x + 1) % n;
				return s;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException("You mustn't"
						+ "remove a square from a sudoku!");
			}
		};
	}

}
