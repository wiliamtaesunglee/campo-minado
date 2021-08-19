package br.com.cod3r.cm.model;

import java.util.ArrayList;
import java.util.List;

import br.com.cod3r.cm.exception.ExplosionException;


public class Field {
	private final int line;
	private final int column;
	private boolean open = false;
	private boolean hasBomb = false;
	private boolean marked = false;
	private List<Field> neighbors = new ArrayList<>();
	Field(int line, int column) {
		this.line = line;
		this.column = column;
	}
	boolean addNeighbor(Field neighbor) {
		boolean lineDiff = line != neighbor.line;
		boolean columnDiff = column != neighbor.column;
		boolean diagonal = lineDiff && columnDiff;
		int deltaLine = Math.abs(line - neighbor.line);
		int deltaColumn = Math.abs(column - neighbor.column);
		int deltaGeneral = deltaColumn + deltaLine;
		boolean isNeighbor = (deltaGeneral == 1 && !diagonal) || (deltaGeneral == 2 && diagonal);
		if(isNeighbor) {
			neighbors.add(neighbor);
			return true;
		}
		return false;
	}
	
	void toogleMarkation() {
		if(!open) {
			marked = !marked;
		}
	}
	
	boolean performOpening() {
		boolean isAvailableToOpen = !open && ! marked;
		if (isAvailableToOpen && hasBomb) {
			open = true;
			throw new ExplosionException();
		}
		if (isAvailableToOpen && safeNeighbor()) {
			neighbors.forEach(v -> v.performOpening());
		}
		if (isAvailableToOpen) {
			open = true;
			return true;
		}
		return false;
	}
	
	boolean safeNeighbor() {
		return neighbors.stream().noneMatch(v -> v.hasBomb);
	}
	
	void performMine() {
		hasBomb = true;
	}
	
	public boolean isBombPlanted() {
		return hasBomb;
	}
	
	public boolean isMarked() {
		return marked;
	}

	void setOpened(boolean open) {
		this.open = open;				
	}
	
	public boolean isOpened() {
		return open;
	}
	
	public boolean isClosed() {
		return !isOpened();
	}
	
	public int getLine() {
		return line;
	}
	
	public int getColumn() {
		return column;
	}
	
	boolean goalAchieved() {
		boolean unraveled = !hasBomb && open;
		boolean isProtected = hasBomb && marked;
		return unraveled || isProtected;
	}
	
	long bombsOnNeighbor() {
		return neighbors.stream().filter(v -> v.hasBomb).count();
	}
	
	void reset() {
		open = false;
		hasBomb = false;
		marked = false;
	}
	
	public String toString() {
		if(marked) {
			return "x";
		} else if(open && hasBomb) {
			return "*";
		} else if(open && bombsOnNeighbor() > 0) {
			return Long.toString(bombsOnNeighbor());
		} else if(open) {
			return " ";
		} else {
			return "?";
		}
	}
}
