package Networking;

import java.awt.Point;

import Objects.Sendable.Move;

public class SplitterTest {

	public static void main(String[] args){
		Object[] test = Splitter.split("Move,f,3,9");
		for(Object item : test){
			System.out.println(Splitter.getClassName(item.getClass().getName()) + " " + item);
		}
		Move move = new Move((Character)test[1], new Point((Integer) test[2], (Integer) test[3]));
		System.out.println(move.parameters());
	}

}
