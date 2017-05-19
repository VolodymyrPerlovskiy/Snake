package com.codenjoy.dojo.snake.client;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2016 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */



import com.codenjoy.dojo.client.Direction;
import com.codenjoy.dojo.client.Solver;
import com.codenjoy.dojo.client.WebSocketRunner;
import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.services.RandomDice;
import com.codenjoy.dojo.snake.model.Elements;

/**
 * User: your name
 */
public class YourSolver implements Solver<Board> {

    private static final String USER_NAME = "vperlovsky@gmail.com";

    private Dice dice;
    private Board board;

    public YourSolver(Dice dice) {
        this.dice = dice;
    }

    @Override
    public String get(Board board) {
        this.board = board;
        System.out.println(board.toString());

         // Found snake head
        int snakeHeadX = -1;
        int snakeHeadY = -1;

        char [][] field = board.getField();

        for (int x = 0; x < field.length; x++) {
            for (int y = 0; y < field.length; y++) {

                char ch = field[x][y];

                if (ch == Elements.HEAD_UP.ch() ||
                   ch == Elements.HEAD_DOWN.ch() ||
                   ch == Elements.HEAD_LEFT.ch() ||
                   ch == Elements.HEAD_RIGHT.ch())
                {
                    snakeHeadX = x;
                    snakeHeadY = y;
                    break;
                }
            }
            if (snakeHeadX != -1){
                break;
            }
        }

        // Found Apple
        int appleX = -1;
        int appleY = -1;

        for (int x = 0; x < field.length; x++) {
            for (int y = 0; y < field.length; y++) {

                char ch = field[x][y];

                if (ch == Elements.GOOD_APPLE.ch())

                {
                    appleX = x;
                    appleY = y;
                    break;
                }
            }
            if (appleX != -1){
                break;
            }
        }

        // Found Bad Apple
        int badAppleX = -1;
        int badAppleY = -1;

        for (int x = 0; x < field.length; x++) {
            for (int y = 0; y < field.length; y++) {

                char ch = field[x][y];

                if (ch == Elements.BAD_APPLE.ch())

                {
                    badAppleX = x;
                    badAppleY = y;
                    break;
                }
            }
            if (badAppleX != -1){
                break;
            }
        }

        int dx = snakeHeadX - appleX;
        int dy = snakeHeadY - appleY;

        if (snakeHeadX == badAppleX && snakeHeadY - badAppleY == -1){
            return Direction.RIGHT.toString();
        }

        // dx < 0 RIGHT
        if (dx < 0) {
            return Direction.RIGHT.toString();
        }
        // dx > 0 LEFT
        if (dx > 0){
            return Direction.LEFT.toString();
        }

        // dy < 0 DOWN
        if (dy < 0){
            return Direction.DOWN.toString();
        }

        // dy > 0 UP
        if (dy > 0){
            return Direction.UP.toString();
        }

         return Direction.UP.toString();
    }

    public static void main(String[] args) {
//        WebSocketRunner.runOnServer("192.168.1.1:8080", // to use for local server
        WebSocketRunner.run(WebSocketRunner.Host.REMOTE,  // to use for codenjoy.com server
                USER_NAME,
                new YourSolver(new RandomDice()),
                new Board());
    }

}
