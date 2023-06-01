import java.util.Scanner;
//Alexis Aldhair Garcia Sandez

public class PalitosAlpha {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PalitosGame game = new PalitosGame(13);
        MinimaxWithAlphaBetaPruning ai = new MinimaxWithAlphaBetaPruning();

        System.out.println("¡Bienvenido al juego de los palitos!");

        while (!game.isGameOver()) {
            System.out.println("Quedan " + game.getPalitos() + " palitos.");
            System.out.print("Tu turno. Ingresa cuántos palitos quieres tomar (1-3): ");
            int playerMove = scanner.nextInt();

            if (game.isValidMove(playerMove)) {
                game.makeMove(playerMove);
                if (game.isGameOver()) {
                	System.out.println("¡La IA gana! Mejor suerte la próxima vez.");
                    break;
                } else {
                    int aiMove = ai.findBestMove(game);
                    System.out.println("La IA toma " + aiMove + " palito(s).");
                    game.makeMove(aiMove);
                    if (game.isGameOver()) {
                    	System.out.println("¡Felicidades! ¡Ganaste!");
                        
                        break;
                    }
                }
            } else {
                System.out.println("Movimiento inválido. Intenta de nuevo.");
            }
        }
    }
}


class PalitosGame {
    private int palitos;

    public PalitosGame(int initialPalitos) {
        palitos = initialPalitos;
    }

    public boolean isValidMove(int palitosToTake) {
        return palitosToTake >= 1 && palitosToTake <= 3 && palitosToTake <= palitos;
    }

    public void makeMove(int palitosToTake) {
        palitos -= palitosToTake;
    }

    public boolean isGameOver() {
        return palitos == 0;
    }

    public int getPalitos() {
        return palitos;
    }
}

class MinimaxWithAlphaBetaPruning {
    public static int minimax(PalitosGame state, int depth, int alpha, int beta, boolean isMaximizingPlayer) {
        if (state.isGameOver()) {
            if (isMaximizingPlayer) {
                return -1; // El jugador maximizador perdió
            } else {
                return 1; // El jugador minimizador perdió
            }
        }

        if (isMaximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;
            for (int i = 1; i <= 3; i++) {
                if (state.isValidMove(i)) {
                    state.makeMove(i);
                    int eval = minimax(state, depth - 1, alpha, beta, false);
                    state.makeMove(-i);

                    maxEval = Math.max(maxEval, eval);
                    alpha = Math.max(alpha, eval);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int i = 1; i <= 3; i++) {
                if (state.isValidMove(i)) {
                    state.makeMove(i);
                    int eval = minimax(state, depth - 1, alpha, beta, true);
                    state.makeMove(-i);

                    minEval = Math.min(minEval, eval);
                    beta = Math.min(beta, eval);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            return minEval;
        }
    }

    public static int findBestMove(PalitosGame state) {
        int bestEval = Integer.MIN_VALUE;
        int bestMove = -1;

        for (int i = 1; i <= 3; i++) {
            if (state.isValidMove(i)) {
                state.makeMove(i);
                int eval = minimax(state, 5, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
                state.makeMove(-i);

                if (eval > bestEval) {
                    bestEval = eval;
                    bestMove = i;
                }
            }
        }

        return bestMove;
    }
}

