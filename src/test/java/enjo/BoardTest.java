package enjo;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import enjo.Board;
import enjo.Square;

public class BoardTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRowUnit() {
		String s = "123456789"
				+ "456789123"
				+ "789123456"
				+ "2.4567891"
				+ "56789.2.4"
				+ "8.1234567"
				+ "345678912"
				+ "678912345"
				+ "912345678";
		Board board = new Board(s);
		Square s14 = board.getSquare(1, 4);
		assertEquals(s14.getV(), 6);
		Set<Integer> expected = new HashSet<Integer>(Arrays.asList(0,5,6,7,8,9,2,4));
		Set<Integer> result = new HashSet<Integer>();
		for (Square square : s14.getUnit(Square.Unit.ROW)){
			result.add(square.getV());
		}
		assertEquals(expected, result);
	}
	
	@Test
	public void testColUnit() {
		String s = "123456789"
				+ "456789123"
				+ "789123456"
				+ "2.4567891"
				+ "56789.2.4"
				+ "8.1234567"
				+ "345678912"
				+ "678912345"
				+ "912345678";
		Board board = new Board(s);
		Square s14 = board.getSquare(1, 4);
		assertEquals(s14.getV(), 6);
		Set<Integer> expected = new HashSet<Integer>(Arrays.asList(0,5,6,7,8,1,2,4));
		Set<Integer> result = new HashSet<Integer>();
		for (Square square : s14.getUnit(Square.Unit.COL)){
			result.add(square.getV());
		}
		assertEquals(expected, result);
	}
	
	@Test
	public void testBoxUnit() {
		String s = "123456789"
				+ "456789123"
				+ "789123456"
				+ "2..567891"
				+ "56789.2.4"
				+ "8.1234567"
				+ "345678912"
				+ "678912345"
				+ "912345678";
		Board board = new Board(s);
		Square s14 = board.getSquare(1, 4);
		assertEquals(s14.getV(), 6);
		Set<Integer> expected = new HashSet<Integer>(Arrays.asList(0,2,5,6,7,8,1));
		Set<Integer> result = new HashSet<Integer>();
		for (Square square : s14.getUnit(Square.Unit.BOX)){
			result.add(square.getV());
		}
		assertEquals(expected, result);
	}
	
	@Test
	public void testBoxUnit2() {
		String s = "123456789"
				+ "456789123"
				+ "789123456"
				+ "2..567891"
				+ "56789.2.4"
				+ "8.1234567"
				+ "345678912"
				+ "678912345"
				+ "912345678";
		Board board = new Board(s);
		Square s47 = board.getSquare(7, 4);
		assertEquals(0, s47.getV());
		Set<Integer> expected = new HashSet<Integer>(Arrays.asList(0,1,2,4,5,6,7,8,9));
		Set<Integer> result = new HashSet<Integer>();
		for (Square square : s47.getUnit(Square.Unit.BOX)){
			result.add(square.getV());
		}
		assertEquals(expected, result);
	}
	
	

}
