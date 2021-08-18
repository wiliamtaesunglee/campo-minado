package br.com.cod3r.cm.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Board {
	private int lines;
	private int columns;
	private int bombs;
	private final List<Field> fields = new ArrayList<>();
	public Board(int lines, int columns, int bombs) {
		this.lines = lines;
		this.columns = columns;
		this.bombs = bombs;
		generateFields();
		associateNeighbors();
		sortBombs();
	}
	
	private void generateFields() {
		for (int l = 0; l < lines; l++) {
			for (int c = 0; c < columns; c++) {
				fields.add(new Field(l, c));
			}
		}
	}
	
	private void associateNeighbors() {
		for (Field c1: fields) {
			for (Field c2: fields) {
				c1.addNeighbor(c2);
			}
		}
	}
	
	private void sortBombs() {
		long plantedBombs = 0;
		Predicate<Field> bombPlanted = c -> c.isBombPlanted();
		
		do {
			plantedBombs = fields.stream().filter(bombPlanted).count();
			int randomValue = (int) (Math.random() * fields.size());
			fields.get(randomValue).performMine();
		} while (plantedBombs < bombs);
	}
	
	public boolean goalAchieved() {
		return fields.stream().allMatch(c -> c.goalAchieved());
	}
	
	public void reset() {
		fields.stream().forEach(c -> c.reset());
		sortBombs();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		int i = 0;
		for (int l = 0; l < lines; l ++) {
			for (int c = 0; c < columns; c ++) {
				sb.append(" ");
				sb.append(fields.get(i));
				sb.append(" ");
				i++;
			}
			sb.append("\n");
		}
		
		return sb.toString();
	}
}
