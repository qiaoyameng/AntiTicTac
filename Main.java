import java.util.Arrays;
import java.util.Scanner;


class TicTacToe {

    private static final int BOARD_SIZE = 5;
    private static final int LOSE_LENGTH = 3;

    private static final String EMPTY = " ";

    private static final String PLAYER_X = "X";
    private static final String PLAYER_O = "O";

    private int moveCount = 0;

    private static class Coord {
        int row, col;

        Coord(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    private String[][] initialiseBoard() {
        String[][] board = new String[BOARD_SIZE][BOARD_SIZE];
        for (String[] row : board) {
            Arrays.fill(row, EMPTY);
        }
        return board;
    }

    private void displayBoard(String[][] board) {
        for (String[] row : board) {
            System.out.println(String.join(" | ", row));
            System.out.println("-".repeat(BOARD_SIZE * 4 - 3));
        }
    }

    private boolean isValidCoord(Coord coord) {
        boolean rowIsValid = coord.row >=0 && coord.row < BOARD_SIZE;
        boolean colIsValid = coord.col >=0 && coord.col < BOARD_SIZE;
        return rowIsValid && colIsValid;
    }

    private Coord getPlayerMove() {
        Scanner scanner = new Scanner(System.in);
        Coord move = new Coord(-1, -1);

        while (true) {
            try {
                System.out.printf("Enter the row (0 to %d): ", BOARD_SIZE - 1);
                move.row = Integer.parseInt(scanner.nextLine());
                System.out.printf("Enter the column (0 to %d): ", BOARD_SIZE - 1);
                move.col = Integer.parseInt(scanner.nextLine());

                if(isValidCoord(move)) {
                    break;
                }
                throw new NumberFormatException();
            }
            catch (NumberFormatException _) {
                System.out.println("Invalid row or column. Try again.");
            }
        }
        return move;
    }

    private boolean isCellEmpty(String[][] board, Coord coord) {
        return board[coord.row][coord.col].equals(EMPTY);
    }

    private boolean isPlayerCell(String[][] board, String player, Coord coord) {
        return board[coord.row][coord.col].equals(player);
    }

    private void makeMove(String[][] board, String player, Coord coord) {
        board[coord.row][coord.col] = player;
        moveCount++;
    }

    private boolean checkLoser(String[][] board, String player, Coord lastMove) {
        // Define directions to check: Horizontal, Vertical, Diagonal, Anti-diagonal
        int[][] directions = {{0, 1}, {1, 0}, {1, 1}, {1, -1}};
        Coord coord = new Coord(-1,-1);

        for (int[] dir : directions) {
            int count = 1;  // Count the current cell

            // Count consecutive matches in the forward direction up to LOSE_LENGTH
            for (int i = 1; i < LOSE_LENGTH; i++) {
                coord.row = lastMove.row + dir[0] * i;
                coord.col = lastMove.col + dir[1] * i;
                if (!isValidCoord(coord)) {
                    break;
                }
                if (!isPlayerCell(board, player, coord)) {
                    break;
                }
                count++;
            }

            // Count consecutive matches in the reverse direction up to LOSE_LENGTH
            for (int i = 1; i < LOSE_LENGTH; i++) {
                coord.row = lastMove.row - dir[0] * i;
                coord.col = lastMove.col - dir[1] * i;
                if (!isValidCoord(coord)) {
                    break;
                }
                if (!isPlayerCell(board, player, coord)) {
                    break;
                }
                count++;
            }

            // Player loses only if they have exactly LOSE_LENGTH consecutive marks
            if (count == LOSE_LENGTH) {
                return true;
            }
        }
        return false;
    }

    private boolean isBoardFull() {
        return moveCount == BOARD_SIZE * BOARD_SIZE;
    }

    public void playTicTacToe() {
        String currentPlayer = PLAYER_X;
        String otherPlayer;      // The player who's not playing
        String[][] board = initialiseBoard();

        System.out.println("Anti-Tic-Tac-Toe: Getting EXACTLY 3 in a row makes you LOSE!");

        while (true) {
            otherPlayer = currentPlayer.equals(PLAYER_X)? PLAYER_O : PLAYER_X;      // Determine who's not playing

            displayBoard(board);
            System.out.printf("Player %s's turn.%n", currentPlayer);

            Coord move = getPlayerMove();
            if(!isCellEmpty(board, move)) {
                System.out.println("Position is already taken. Try again.");
                continue;
            }
            makeMove(board, currentPlayer, move);

            if (checkLoser(board, currentPlayer, move)) {
                displayBoard(board);
                System.out.printf("Player %s got 3 in a row and LOSES!%n", currentPlayer);
                System.out.printf("Player %s WINS!%n", otherPlayer);
                break;
            }
            else if (isBoardFull()) {
                displayBoard(board);
                System.out.println("The game ends in a tie!");
                break;
            }

            // Make the current player the other player
            currentPlayer = otherPlayer;
        }
    }
}

public class Main {
    public static void main(String[] args) {
        TicTacToe game = new TicTacToe();
        game.playTicTacToe();
    }
}