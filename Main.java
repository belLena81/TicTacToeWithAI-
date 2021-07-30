package ua.beloshapka;
import java.util.*;

public class Main {
    private static char[][] board = {{' ', ' ', ' '}, {' ', ' ', ' '}, {' ', ' ', ' '}};
    private static Random random = new Random();
    private static Scanner scanner = new Scanner(System.in);
    private static int deep = 0;
    private static void viewBoard() {
        System.out.println("---------");
        for (int i = 0; i < board.length; i++) {
            System.out.print("|");
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(" "+board[i][j]);
            }
            System.out.println(" |");
        }
        System.out.println("---------");
    }

    private static int[] getCoordinates() {
        int[] point = new int[2];
        boolean hasInput = true;
        do {
            try {
                System.out.print("Enter the coordinates: ");
                point[0] = scanner.nextInt();
                point[1] = scanner.nextInt();
                scanner.nextLine();
                hasInput = true;
            } catch (Exception e) {
                hasInput = false;
                System.out.println("You should enter numbers!");
                scanner.nextLine();
            }
        } while (!hasInput);
        return point;
    }

    private static boolean hasPoint(int[] point){
        if (point[0] <= 0 || point[0] > 3 || point[1] <= 0 || point[1] > 3) {
            System.out.println("Coordinates should be from 1 to 3!");
            return false;
        } else if (board[point[0] - 1][point[1] - 1] == ' ') {
            return true;
        } else {
            System.out.println("This cell is occupied! Choose another one!");
            return false;
        }
    }

    private static boolean isWin(char symbol) {
        boolean win = (board[0][0] == symbol) && (board[0][1] == symbol) && (board[0][2] == symbol);
        win |= (board[1][0] == symbol) && (board[1][1] == symbol) && (board[1][2] == symbol);
        win |= (board[2][0] == symbol) && (board[2][1] == symbol) && (board[2][2] == symbol);
        win |= (board[0][0] == symbol) && (board[1][1] == symbol) && (board[2][2] == symbol);
        win |= (board[2][0] == symbol) && (board[1][1] == symbol) && (board[0][2] == symbol);
        win |= (board[0][0] == symbol) && (board[1][0] == symbol) && (board[2][0] == symbol);
        win |= (board[0][1] == symbol) && (board[1][1] == symbol) && (board[2][1] == symbol);
        win |= (board[0][2] == symbol) && (board[1][2] == symbol) && (board[2][2] == symbol);
        return win;
    }

    private static boolean isWin(char symbol, char[] board) {
        boolean win = (board[0] == symbol) && (board[1] == symbol) && (board[2] == symbol);
        win |= (board[3] == symbol) && (board[4] == symbol) && (board[5] == symbol);
        win |= (board[6] == symbol) && (board[7] == symbol) && (board[8] == symbol);
        win |= (board[0] == symbol) && (board[3] == symbol) && (board[6] == symbol);
        win |= (board[1] == symbol) && (board[4] == symbol) && (board[7] == symbol);
        win |= (board[2] == symbol) && (board[5] == symbol) && (board[8] == symbol);
        win |= (board[0] == symbol) && (board[4] == symbol) && (board[8] == symbol);
        win |= (board[2] == symbol) && (board[4] == symbol) && (board[6] == symbol);
        return win;
    }

    private static boolean isFull() {
        boolean full = true;
        for (char[] chars : board) {
            for (int j = 0; j < chars.length; j++) {
                if (chars[j] == ' ') {
                    full = false;
                }
            }
        }
        return full;
    }

    private static String setPoint(int[] point) {
        char symbol = getSymbol();
        board[point[0] - 1][point[1] - 1] = symbol;
        if (isWin(symbol)) {
            return symbol + " wins";
        } else if (isFull()) {
            return "Draw";
        } else {
            return "Game not finished";
        }
    }

    private static char getSymbol() {
        int xCount = 0;
        int oCount = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 'X' || board[i][j] == 'x') {
                    xCount++;
                }
                if (board[i][j] == 'O' || board[i][j] == 'o') {
                    oCount++;
                }
            }
        }
        return xCount  == oCount ? 'X' : 'O';
    }

    private static boolean isEnd() {
        return isFull() || isWin('X') || isWin('O');
    }

    private static String oMoveEasy() {
        int[] point = new int[2];
        String answer = "";
        boolean move = false;
        do {
            point[0] = random.nextInt(3) + 1;
            point[1] = random.nextInt(3) + 1;
            if (board[point[0] - 1][point[1] - 1] == ' ') {
                answer = setPoint(point);
                move = true;
            }
        } while (!move);
        return answer;
    }

    private static boolean isCanWin(int[] point) {
        boolean canWin = false;
        canWin = board[point[0] % 3][point[1] - 1] == board[(point[0] + 1) % 3][point[1] - 1]
                && board[point[0] % 3][point[1] - 1] != ' ';
        canWin |= board[point[0] - 1][point[1] % 3] == board[point[0] - 1][(point[1] + 1) % 3]
                && board[point[0] - 1][point[1] % 3] != ' ';
        canWin |= point[0] == point[1]
                && board[point[0] % 3][point[1] % 3] == board[(point[0] + 1) % 3][(point[1] + 1) % 3]
                && board[point[0] % 3][point[1] % 3] != ' ';
        canWin |= point[0] + point[1] == 4
                && board[(point[0]) % 3][2 - point[0] % 3] == board[(point[0] + 1) % 3][2 - (point[0] + 1) % 3]
                && board[(point[0]) % 3][2 - point[0] % 3] != ' ';
        canWin &= board[point[0] - 1][point[1] - 1] == ' ';
        return canWin;
    }

    private static String oMoveMedium() {
        int[] point = new int[2];
        String answer = "";
        boolean move = false;
        int i = 0;
        int j = 0;
        boolean canWin = false;
        do{
            point[0] = i + 1;
            point[1] = j + 1;
            canWin = isCanWin(point);
            if (canWin) {
                answer = setPoint(point);
                move = true;
            } else {
                j++;
                if (j == 3) {
                    i++;
                    j = 0;
                }
            }
        } while (!canWin && i < 3 );
        if (!move) {
            do {
                point[0] = random.nextInt(3) + 1;
                point[1] = random.nextInt(3) + 1;
                if (board[point[0] - 1][point[1] - 1] == ' ') {
                    answer = setPoint(point);
                    move = true;
                }
            } while (!move);
        }
        return answer;
    }

    private static String oMoveHard() {
        int[] point = new int[2];
        char[] curBoard = new char[9];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                curBoard[i * 3 + j] = board[i][j];
            }
        }
        HashMap<Integer, Integer> bestMove = new HashMap<>();
        maxScore(getSymbol(),curBoard, bestMove);
        point[0] = bestMove.get(0) / 3 + 1;
        point[1] = bestMove.get(0) % 3 + 1;
        return setPoint(point);
    }

    private static int[] getAllPossibleMoves(char[] moveBoard) {
        ArrayList <Integer> moves = new ArrayList<>();
        for (int i = 0; i < moveBoard.length; i++) {
            if (moveBoard[i] == ' ') {
                moves.add(i);
            }
        }
        return moves.stream().mapToInt(i -> i).toArray();
    }

    private static char anotherSymbol(char symbol) {
        return symbol == 'X' ? 'O' : 'X';
    }

    private static int maxScore(char symbol, char[] moveBoard, HashMap<Integer, Integer> bestMove) {
        int[] possibleMoves = getAllPossibleMoves(moveBoard);
        int max = -10000;
        deep++;
        if (possibleMoves.length == 0) {
            max = 0;
        } else {
            int score = 0;
            for (int i = 0; i < possibleMoves.length; i++) {
                moveBoard[possibleMoves[i]] = symbol;
                if (isWin(symbol, moveBoard)) {
                    score = 10;
                } else if (isWin(anotherSymbol(symbol), moveBoard)) {
                    score = -10;
                } else {
                    score = minScore(anotherSymbol(symbol),moveBoard, bestMove) - 1;
                }

                if (score > max) {
                    max = score;
                    bestMove.put(deep - 1, possibleMoves[i]);
                }
                moveBoard[possibleMoves[i]] = ' ';
            }
        }
        deep--;
        return max;
    }

    private static int minScore(char symbol, char[] moveBoard, HashMap<Integer, Integer> bestMove) {
        int[] possibleMoves = getAllPossibleMoves(moveBoard);
        deep++;
        int min = 100000;
        if (possibleMoves.length == 0) {
            min = 0;
        } else {
            int score = 0;
            for (int i = 0; i < possibleMoves.length; i++) {
                moveBoard[possibleMoves[i]] = symbol;
                if (isWin(symbol, moveBoard)) {
                    score = -10;
                } else if (isWin(anotherSymbol(symbol), moveBoard)) {
                    score = 10;
                } else {
                    score = maxScore(anotherSymbol(symbol), moveBoard, bestMove) + 1;
                }
                if (score < min) {
                    min = score;
                    bestMove.put(deep - 1, possibleMoves[i]);
                }
                moveBoard[possibleMoves[i]] = ' ';
            }
        }
        deep--;
        return min;
    }

    private static void newGame(String[] players) {
        boardInit();
        viewBoard();
        int[] point = new int[2];
        String answer = "";
        do {
            if (players[0].equals("user")) {
                boolean move = false;
                do {
                    point = getCoordinates();
                    if (hasPoint(point)) {
                        answer = setPoint(point);
                        viewBoard();
                        move = true;
                    }
                } while (!move);
            } else {
                System.out.println("Making move level \"" + players[0] + "\"");
                if (players[0].equals("easy")) {
                    answer = oMoveEasy();
                } else if (players[0].equals("medium")) {
                    answer = oMoveMedium();
                } else {
                    answer = oMoveHard();
                }
                viewBoard();
            }
            if (!isEnd()) {
                if (players[1].equals("user")) {
                    boolean move = false;
                    do {
                        point = getCoordinates();
                        if (hasPoint(point)) {
                            answer = setPoint(point);
                            viewBoard();
                            move = true;
                        }
                    } while (!move);
                } else {
                    System.out.println("Making move level \"" + players[1] + "\"");
                    if (players[1].equals("easy")) {
                        answer = oMoveEasy();
                    } else if (players[1].equals("medium")) {
                        answer = oMoveMedium();
                    } else {
                        answer = oMoveHard();
                    }
                    viewBoard();
                }
            }
        } while (!isEnd());
        System.out.println(answer);
    }

    private static void boardInit() {
        for (int i = 0; i < board.length; i++) {
            Arrays.fill(board[i], ' ');
        }
    }

    private static boolean isValid(String player) {
        return player.equals("user") || player.equals("easy") || player.equals("medium") || player.equals("hard");
    }

    public static void main(String[] args) {
        String choose = "";
        do {
            boolean validPlayers = false;
            do {
                System.out.print("Input command: ");
                choose = scanner.nextLine();
                if (choose.startsWith("start")) {
                    choose = choose.substring(5);
                    choose = choose.trim();
                    String[] players = choose.split("\\s+");
                    if (players.length != 2) {
                        System.out.println("Bad parameters!");
                    } else if (isValid(players[0]) && isValid(players[1])) {
                        newGame(players);
                        validPlayers = true;
                    } else {
                        System.out.println("Bad parameters!");
                    }
                }  else if (!choose.equals("exit")) {
                    System.out.println("Bad command!");
                }
            } while (!validPlayers && !choose.equals("exit"));
        } while (!choose.equals("exit"));
    }
}
