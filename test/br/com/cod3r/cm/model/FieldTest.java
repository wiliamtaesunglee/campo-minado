package br.com.cod3r.cm.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.cod3r.cm.exception.ExplosionException;

public class FieldTest {
	private Field field;
	
	@BeforeEach
	void initiateField() {
		field = new Field(3, 3);
	}
	
	@Test
	void testNeighborDistance1Bottom() {
		Field neighbor = new Field(3, 2);
		boolean result = field.addNeighbor(neighbor);
		assertTrue(result);
	}
	
	@Test
	void testNeighborDistance1Top() {
		Field neighbor = new Field(3, 4);
		boolean result = field.addNeighbor(neighbor);
		assertTrue(result);
	}
	
	@Test
	void testNeighborDistance1Left() {
		Field neighbor = new Field(2, 3);
		boolean result = field.addNeighbor(neighbor);
		assertTrue(result);
	}
	
	@Test
	void testNeighborDistance1Right() {
		Field neighbor = new Field(4, 3);
		boolean result = field.addNeighbor(neighbor);
		assertTrue(result);
	}
	
	@Test
	void testNeighborDistance2Diagonal() {
		Field neighbor = new Field(2, 2);
		boolean result = field.addNeighbor(neighbor);
		assertTrue(result);
	}
	
	@Test
	void testNotNeighborDistance() {
		Field neighbor = new Field(4, 5);
		boolean result = field.addNeighbor(neighbor);
		assertFalse(result);
	}
	
	@Test
	void testDefaultValueAttributeMarked() {
		assertFalse(field.isMarked());
	}
	
	@Test
	void testToogleMarked() {
		field.toogleMarkation();
		assertTrue(field.isMarked());
	}
	
	@Test
	void testToogleMarkedTwice() {
		field.toogleMarkation();
		field.toogleMarkation();
		assertFalse(field.isMarked());
	}
	
	@Test
	void testOpenNotMinedAndNotMarked() {
		assertTrue(field.performOpening());
	}
	
	@Test
	void testOpenNotMinedMarked() {
		field.toogleMarkation();
		field.performMine();
		assertFalse(field.performOpening());
	}
	
	@Test
	void testOpenMinedNotMarked() {
		field.performMine();
		assertThrows(ExplosionException.class, () -> {			
			field.performOpening();
		});
	}
	
	@Test
	void testOpenNeighbors() {
		Field field11 = new Field(1, 1);
		Field field22 = new Field(2, 2);
		field22.addNeighbor(field11);
		field.addNeighbor(field22);
		field.performOpening();
		assertTrue(field22.isOpened() && field11.isOpened());
	}
	
	@Test
	void testOpenNeighborsWithMine() {
		Field field11 = new Field(1, 1);
		Field field12 = new Field(1, 2);
		field12.performMine();
		Field field22 = new Field(2, 2);
		field22.addNeighbor(field11);
		field22.addNeighbor(field12);
		field.addNeighbor(field22);
		field.performOpening();
		assertTrue(field22.isOpened() && field11.isClosed());
	}
}
