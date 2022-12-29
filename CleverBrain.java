import student.tetris.*;
/**
 *  @author Solaiman Ibrahimi
 *  @version (2022.10.31)
 */
public class CleverBrain implements Brain
{
    /**
     * bestMove method will determine
     * the best move possible
     * @return the best move possible
     */
    public Move bestMove(Board board, Piece piece, int heightLimit)
    {
        Move move = new Move(piece);
        double bestScore = 10000.0;
        int bestX = 0;
        int bestY = 0;
        Piece bestPiece = null;
        Piece current = piece;
        
        while (true) {
            int yBound = heightLimit - current.getHeight()+1;
            int xBound = board.getWidth() - current.getWidth()+1;
            for (int x = 0; x<xBound; x++) {
                int y = board.dropHeight();
                if (y<yBound) { 
                    int result = board.place(current, x, y);
                    if (result <= Board.PLACE_ROW_FILLED) {
                        if (result == Board.PLACE_ROW_FILLED) {
                            board.clearRows();
                        }
                        double score = rateBoard(board);
                        if (score<bestScore) {
                                 bestScore = score;
                                 bestX = x;
                                 bestY = y;
                                 bestPiece = current;
                        }
                    }
                    board.undo();
                }
            }
         }
        if (bestPiece == null) {
            return(null);
        }
        Point p = new Point(bestX, bestY);
        Move bestMove = new Move(bestPiece);
        bestMove.setLocation(p);
        move.setScore(bestScore);
        return bestMove;
    } 

    /**
     * rateBoard method will calculate
     * a rating for the quality of a
     * board's current condition
     * @return the rating of the board
     */
    public double rateBoard(Board board)
    {
        int width = board.getWidth();
        int height = board.getHeight();
        int sumHeight = 0;
        int[] colHeights = board.getColumnHeights();
        double avgHeight = ((double)sum(colHeights))/width;
        return (height + (5 * avgHeight));
    }
    
    public int sum(int[] columnHeights)
    {
        int count = 0;
        for (int i = 0; i < columnHeights.length; i++)
        {
            count += columnHeights[i];   
        }
        return count;
    }
}   

