package student_player;

import boardgame.Move;

import pentago_twist.PentagoPlayer;
import pentago_twist.PentagoBoardState;

/** A player file submitted by a student. */
public class StudentPlayer extends PentagoPlayer {

    /**
     * You must modify this constructor to return your student number. This is
     * important, because this is what the code that runs the competition uses to
     * associate you with your agent. The constructor should do nothing else.
     */
    public StudentPlayer() {
        super("260424285"); 
    }

    public Move chooseMove(PentagoBoardState boardState) {     
        //clone the board and get neccessary info
        PentagoBoardState boardClone = (PentagoBoardState)boardState.clone();
        int turnPlayer = boardClone.getTurnPlayer();
        Move myMove;
        int turnNum = boardClone.getTurnNumber();
        //int mode = 0; //0 for learning mode, 1 for read mode

        //if its the first or second move we use a heuristic-informed strategy
        if (turnNum == 0 || turnNum == 1){
            myMove = MyTools.getInitMove(boardClone, turnPlayer);
        }
        else{
            MonteCarloTreeSearch mcts = new MonteCarloTreeSearch();
            myMove = mcts.findNextMove(boardClone, turnPlayer);
        }

        //save turn to be written to file
        //MyTools.saveTurn(boardClone, myMove);

        // Return your move to be processed by the server.
        return myMove;
    }
}