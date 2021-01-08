import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import game.gmk.GomokuAction;
import game.gmk.GomokuGame;
import game.gmk.GomokuPlayer;


public class Agent extends GomokuPlayer{

    protected ArrayList<GomokuAction> actions = new ArrayList<GomokuAction>();

    /**
     * Creates a greedy player, sets the specified parameters to the super class.
     * @param color player's color
     * @param board game board
     * @param random random number generator
     * @see GomokuPlayer#GomokuPlayer(int, int[][], Random)
     */
    public Agent(int color, int[][] board, Random random) {
        super(color, board, random);
        // store possible actions
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                actions.add(new GomokuAction(i, j));
            }
        }
    }


    @Override
    public GomokuAction getAction(GomokuAction prevAction, long[] remainingTimes) {

        if (prevAction == null) {
            int i = board.length / 2;
            int j = board[i].length / 2;
            while (board[i][j] != GomokuGame.EMPTY) {
                i = random.nextInt(board.length);
                j = random.nextInt(board[i].length);
            }
            board[i][j] = color;
            return new GomokuAction(i, j);
        }

        // store enemy's step
        board[prevAction.i][prevAction.j] = 1 - color;

        // find best steps and choose a random one
        Collections.shuffle(actions, random);
        GomokuAction action = null;
        int score = -1;
        for (GomokuAction a : actions) {
            if (board[a.i][a.j] == GomokuGame.EMPTY) {
                int s = score(a.i, a.j, color) + score(a.i, a.j, 1 - color);
                if (score < s) {
                    score = s;
                    action = a;
                }
            }
        }

        // store and do best step
        board[action.i][action.j] = color;
        return action;
    }


    protected int score(int i, int j, int c) {
        int result = 0;
        int szam = 0;

        //right up
        szam = countDirection(i, j, 1, -1, c);
        if (szam == 4) {
            result += 100000;
        } else if (szam == 3) {
            if (board[(i + board.length - 1) % board.length][(j + board[i].length + 1) % board[i].length] != GomokuGame.WALL) {
                if(board[(i + board.length - 1) % board.length][(j + board[i].length + 1) % board[i].length] != (1-c)) {
                    result += 15000;
                }
            }
        } else if (szam == 2) {
            if (board[(i + board.length - 1) % board.length][(j + board[i].length + 1) % board[i].length] != GomokuGame.WALL && board[(i + board.length - 2) % board.length][(j + board[i].length + 2) % board[i].length] != GomokuGame.WALL) {
                if (board[(i + board.length - 1) % board.length][(j + board[i].length + 1) % board[i].length] != (1-c) && board[(i + board.length - 2) % board.length][(j + board[i].length + 2) % board[i].length] != (1-c)) {
                    result += 1000;
                }
            }
        } else if (szam == 1) {
            result += 50;
        }

        // right
        szam = countDirection(i, j, 1, 0, c);;
        if (szam == 4) {
            result += 100000;
        } else if (szam == 3) {
            if (board[(i + board.length - 1) % board.length][(j + board[i].length) % board[i].length] != GomokuGame.WALL) {
                if(board[(i + board.length - 1) % board.length][(j + board[i].length) % board[i].length] != (1-c)) {
                    result += 15000;
                }
            }
        } else if (szam == 2) {
            if (board[(i + board.length - 1) % board.length][(j + board[i].length) % board[i].length] != GomokuGame.WALL && board[(i + board.length - 2) % board.length][(j + board[i].length) % board[i].length] != GomokuGame.WALL) {
                if (board[(i + board.length - 1) % board.length][(j + board[i].length) % board[i].length] != (1-c) && board[(i + board.length - 2) % board.length][(j + board[i].length) % board[i].length] != (1-c)) {
                    result += 1000;
                }
            }
        } else if (szam == 1) {
            result += 50;
        }

        //right down
        szam =  countDirection(i, j, 1, 1, c);
        if (szam == 4) {
            result += 100000;
        } else if (szam == 3) {
            if (board[(i + board.length - 1) % board.length][(j + board[i].length - 1) % board[i].length] != GomokuGame.WALL) {
                if(board[(i + board.length - 1) % board.length][(j + board[i].length - 1) % board[i].length] != (1-c)) {
                    result += 15000;
                }
            }
        } else if (szam == 2) {
            if (board[(i + board.length - 1) % board.length][(j + board[i].length - 1) % board[i].length] != GomokuGame.WALL && board[(i + board.length - 2) % board.length][(j + board[i].length - 2) % board[i].length] != GomokuGame.WALL) {
                if (board[(i + board.length - 1) % board.length][(j + board[i].length - 1) % board[i].length] != (1-c) && board[(i + board.length - 2) % board.length][(j + board[i].length - 2) % board[i].length] != (1-c)) {
                    result += 1000;
                }
            }
        } else if (szam == 1) {
            result += 50;
        }

        //down
        szam = countDirection(i, j, 0, 1, c);
        if (szam == 4) {
            result += 100000;
        } else if (szam == 3) {
            if (board[(i + board.length) % board.length][(j + board[i].length - 1) % board[i].length] != GomokuGame.WALL) {
                if(board[(i + board.length) % board.length][(j + board[i].length - 1) % board[i].length] != (1-c)) {
                    result += 15000;
                }
            }
        } else if (szam == 2) {
            if (board[(i + board.length) % board.length][(j + board[i].length - 1) % board[i].length] != GomokuGame.WALL && board[(i + board.length) % board.length][(j + board[i].length - 2) % board[i].length] != GomokuGame.WALL) {
                if (board[(i + board.length) % board.length][(j + board[i].length - 1) % board[i].length] != (1-c) && board[(i + board.length) % board.length][(j + board[i].length - 2) % board[i].length] != (1-c)) {
                    result += 1000;
                }
            }
        } else if (szam == 1) {
            result += 50;
        }

        //left down
        szam = countDirection(i, j, -1, 1, c);
        if (szam == 4) {
            result += 100000;
        } else if (szam == 3) {
            if (board[(i + board.length + 1) % board.length][(j + board[i].length - 1) % board[i].length] != GomokuGame.WALL) {
                if(board[(i + board.length + 1) % board.length][(j + board[i].length - 1) % board[i].length] != (1-c)) {
                    result += 15000;
                }
            }
        } else if (szam == 2) {
            if (board[(i + board.length + 1) % board.length][(j + board[i].length - 1) % board[i].length] != GomokuGame.WALL && board[(i + board.length + 2) % board.length][(j + board[i].length - 2) % board[i].length] != GomokuGame.WALL) {
                if (board[(i + board.length + 1) % board.length][(j + board[i].length - 1) % board[i].length] != (1-c) && board[(i + board.length + 2) % board.length][(j + board[i].length - 2) % board[i].length] != (1-c)) {
                    result += 1000;
                }
            }
        } else if (szam == 1) {
            result += 50;
        }


        //left
        szam = countDirection(i, j, -1, 0, c);
        if (szam == 4) {
            result += 100000;
        } else if (szam == 3) {
            if (board[(i + board.length + 1) % board.length][(j + board[i].length) % board[i].length] != GomokuGame.WALL) {
                if(board[(i + board.length + 1) % board.length][(j + board[i].length) % board[i].length] != (1-c)) {
                    result += 15000;
                }
            }
        } else if (szam == 2) {
            if (board[(i + board.length + 1) % board.length][(j + board[i].length) % board[i].length] != GomokuGame.WALL && board[(i + board.length + 2) % board.length][(j + board[i].length) % board[i].length] != GomokuGame.WALL) {
                if (board[(i + board.length + 1) % board.length][(j + board[i].length) % board[i].length] != (1-c) && board[(i + board.length + 2) % board.length][(j + board[i].length) % board[i].length] != (1-c)) {
                    result += 1000;
                }
            }
        } else if (szam == 1) {
            result += 50;
        }

        //left up
        szam = countDirection(i, j, -1, -1, c);
        if (szam == 4) {
            result += 100000;
        } else if (szam == 3) {
            if (board[(i + board.length + 1) % board.length][(j + board[i].length + 1) % board[i].length] != GomokuGame.WALL) {
                if(board[(i + board.length + 1) % board.length][(j + board[i].length + 1) % board[i].length] != (1-c)) {
                    result += 15000;
                }
            }
        } else if (szam == 2) {
            if (board[(i + board.length + 1) % board.length][(j + board[i].length + 1) % board[i].length] != GomokuGame.WALL && board[(i + board.length + 2) % board.length][(j + board[i].length + 2) % board[i].length] != GomokuGame.WALL) {
                if (board[(i + board.length + 1) % board.length][(j + board[i].length + 1) % board[i].length] != (1-c) && board[(i + board.length + 2) % board.length][(j + board[i].length + 2) % board[i].length] != (1-c)) {
                    result += 1000;
                }
            }
        } else if (szam == 1) {
            result += 50;
        }

        // up
        szam = countDirection(i, j, 0, -1, c);
        if (szam == 4) {
            result += 100000;
        } else if (szam == 3) {
            if (board[(i + board.length) % board.length][(j + board[i].length + 1) % board[i].length] != GomokuGame.WALL) {
                if(board[(i + board.length) % board.length][(j + board[i].length + 1) % board[i].length] != (1-c)) {
                    result += 15000;
                }
            }
        } else if (szam == 2) {
            if (board[(i + board.length) % board.length][(j + board[i].length + 1) % board[i].length] != GomokuGame.WALL && board[(i + board.length) % board.length][(j + board[i].length + 2) % board[i].length] != GomokuGame.WALL) {
                if (board[(i + board.length) % board.length][(j + board[i].length + 1) % board[i].length] != (1-c) && board[(i + board.length) % board.length][(j + board[i].length + 2) % board[i].length] != (1-c)) {
                    result += 1000;
                }
            }
        } else if (szam == 1) {
            result += 50;
        }
        return result;
    }

    /**
     * Counts the number of consecutive cells from the specified start point at
     * the specified direction belongs to the specified color.
     * @param i row of start position
     * @param j column of start position
     * @param di row direction
     * @param dj column direction
     * @param c color
     * @return number of consecutive cells
     */
    protected int countDirection(int i, int j, int di, int dj, int c) {
        int ni = (i + board.length + di) % board.length;
        int nj = (j + board[ni].length + dj) % board[ni].length;
        if (board[ni][nj] != c) {
            return 0;
        }
        return 1 + countDirection(ni, nj, di, dj, c);
    }
}
