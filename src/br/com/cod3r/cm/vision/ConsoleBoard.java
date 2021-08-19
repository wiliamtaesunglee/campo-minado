package br.com.cod3r.cm.vision;

import br.com.cod3r.cm.exception.ExplosionException;
import br.com.cod3r.cm.exception.OutException;
import br.com.cod3r.cm.model.Board;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class ConsoleBoard {
    private Board board;
    private Scanner entryPoint = new Scanner(System.in);
    public ConsoleBoard(Board board) {
        this.board = board;
        executeGame();
    }
    private void executeGame() {
        try {
            boolean continueGame = true;
            while(continueGame) {
                cycleOfGame();
                System.out.println("Start new game? (Y/n) ");
                String response = entryPoint.nextLine();
                if ("n".equalsIgnoreCase(response)) {
                    continueGame = false;
                } else {
                    board.reset();
                }
            }
        } catch (OutException e) {
            System.out.println("Bye!!!");
        } finally {
            System.out.println("Close game");
            entryPoint.close();
        }
    }

    private void cycleOfGame() {
        try {
            boolean hasNotAchievedGoal = !board.goalAchieved();
            while(hasNotAchievedGoal) {
                System.out.println(board);
                String inputtedValue = captureInputValue("Put (x, y): ");
                Iterator<Integer> xy = Arrays.stream(inputtedValue.split(","))
                        .map(e -> Integer.parseInt(e.trim())).iterator();
                inputtedValue = captureInputValue("1 - Open | 2 - Marking");
                if("1".equals(inputtedValue)) {
                    board.openField(xy.next(), xy.next());
                } else {
                    board.toogleMarketion(xy.next(), xy.next());
                }
            }
            System.out.println("You win!");
        } catch (ExplosionException e) {
            System.out.println(board);
            System.out.println("Game Over, you lose!");
        }
    }

    private String captureInputValue(String text) {
        System.out.print(text);
        String inputValue = entryPoint.nextLine();
        if("Out".equalsIgnoreCase(inputValue)) {
            System.out.println("out executed");
            throw  new OutException();
        }
        return inputValue;
    }
}
