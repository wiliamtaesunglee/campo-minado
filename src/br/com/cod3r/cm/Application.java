package br.com.cod3r.cm;

import br.com.cod3r.cm.model.Board;
import br.com.cod3r.cm.vision.ConsoleBoard;

public class Application {
	public static void main(String[] args) {
		Board board = new Board(6, 6, 6);
		new ConsoleBoard(board);
	}
}
