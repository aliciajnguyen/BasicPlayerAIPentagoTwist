package student_player;


import pentago_twist.PentagoBoardState;

/* Each node has its own state, implementation for MCST*/

public class MC_NodeState {
    PentagoBoardState board;
    int playerNum;
    int visitCount;
    double winScore;

    //constructor
    MC_NodeState(PentagoBoardState board){
        this. visitCount = 0;
        this.winScore = 0;
        this.board = board;
    }

    //setters
    public void setPlayerNum(int player_num){
        this.playerNum = player_num;
    }
    //getters
    public PentagoBoardState getBoard(){
        return this.board;
    }
    public int getVisitCount(){
        return this.visitCount;
    }
    public double getWinScore(){
        return this.winScore;
    }
    public int getPlayerNum(){
        return this.playerNum;
    }

    public int getOpponent(){
        return 1 - this.playerNum;
    }

    //other methods
    public void incrementVisit(){
        this.visitCount++;
    }

    public void addWinScore(int score){
        this.winScore = winScore + score;
    }
}