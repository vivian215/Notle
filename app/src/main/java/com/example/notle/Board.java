package com.example.notle;

public class Board {
    private GuessBox[][] board;
    private int currGuess;
    private int currBox;

    //board constructor
    public Board (GuessBox[][] board) {
        this.board = board;
        currGuess = 0;
        currBox = 0;
    }

    //getters and setters
    public GuessBox[][] getBoard() {
        return board;
    }

    public int getCurrGuess() {
        return currGuess;
    }

    //adds a note to the guess box when a piano key is clicked
    public void addKey (String letter) {
        if (currBox < 5) {
            board[currGuess][currBox].setLetter(letter);
            currBox++;
        }
    }

    //checks the guess and changes box colors after enter is clicked
    public boolean check (String[] answer) {
        boolean won = true;
        if (currBox < 5) {
            return false;
        }
        if (currGuess < 5 && currBox == 5) {
            for (int j = 0; j < 5; j++) {
                GuessBox box = board[currGuess][j];
                String letter = box.getLetter();
                box.setTextColor(Constants.WHITE);
                boolean yellow = false;
                if (answer[j].equals(letter)) {
                    box.setBgColor(Constants.GREEN);
                } else {
                    won = false;
                    for (int i = 0; i < answer.length; i++) {
                        if (letter.equals(answer[i])) {
                            box.setBgColor(Constants.YELLOW);
                            yellow = true;
                        }
                    }
                    if (!yellow) {
                        box.setBgColor(Constants.GRAY);
                    }
                }
            }
        }
        currGuess++;
        currBox = 0;

        return won;
    }

    //deletes one letter if delete is clicked
    public void delete () {
        if (currBox > 0) {
            currBox--;
            board[currGuess][currBox].setLetter(" ");
        }
    }


}
