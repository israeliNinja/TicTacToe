package TicTacToe;

/**
 * player
 */
public class Player {

    String mark;
    String oponnentMark;
    boolean isHuman;
    
    public Player(String mark, boolean isHuman) {
        this.mark = mark;
        this.isHuman = isHuman;
        if (mark == "X") {
            this.oponnentMark = "O";
        }
        else {
            oponnentMark = "X";
        }
    }

    public void makeTurn(Board board, String mark, int index, boolean isHuman) {
        if (!isHuman) {
            index = bestMoveIndex(board);
        }
        board.buttons[index].setText(mark);
    }

    /**
     * returns the best move index using the minimax method.
     * @return the best move index.
     */
    private int bestMoveIndex(Board board) {
        int bestMoveValue = Integer.MIN_VALUE;
        int bestMove = -1;
        for (int i = 0; i < Board.NUMBER_OF_BUTTONS; i++) {
            if (board.buttons[i].getText().isEmpty()) {
                board.buttons[i].setText(mark);   
                int moveValue = minimax(board, false, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
                board.buttons[i].setText("");
                if (moveValue > bestMoveValue) {
                    bestMoveValue = moveValue;
                    bestMove = i;
                }
            }
        }
        return bestMove;
    }

    /**
     * minimax algorithm using alpha-beta pruning, A recursive function that considers all
     * the possible ways the game can go and returns the value of the traversed path based on the evaluate function.
     * @param isMax is the player is maximizing or minimizig.
     * @param depth the depth of the game. i.e the amount of plays that has been made.
     * @param alpha the maximizing player's best option.
     * @param beta the minimizing player'sbest option.
     * @return the value of the traversed path untill the last move.
     */
    private int minimax(Board board, boolean isMax, int depth, int alpha, int beta) {
        board.isGame = false;     //to distinguish from the actual game
        int score = board.check();
        if (score == 10) {
            return 10 - depth;
        }
        if (score == -10) {
            return -10 + depth;
        }
        if (!board.isAvailable()) {
            return 0;
        }
        //if it's the maximaizing player's turn
        if (isMax) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < Board.NUMBER_OF_BUTTONS; i++) {
                if (board.buttons[i].getText().isEmpty()) {
                    board.buttons[i].setText(mark);
                    int minimaxScore = minimax(board, false, depth + 1, alpha, beta);
                    bestScore = Math.max(bestScore, minimaxScore);
                    board.buttons[i].setText("");
                    alpha = Math.max(alpha, minimaxScore);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            return bestScore;
        }
        //if it's the minimizing player's turn
        else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < Board.NUMBER_OF_BUTTONS; i++) {
                if (board.buttons[i].getText().isEmpty()) {
                    board.buttons[i].setText(oponnentMark);
                    int minimaxScore = minimax(board, true, depth + 1, alpha, beta);
                    bestScore = Math.min(bestScore, minimaxScore);
                    board.buttons[i].setText("");
                    beta = Math.min(beta, minimaxScore);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            return bestScore;
        }
    }
}