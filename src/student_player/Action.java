package student_player;

import boardgame.Move;
/* A class to represent board states and actions (moves)*/

public class Action{
    //attributes
    private Move move;
    private int avgMovesTilFin; //put in state instead?
    private int numTimesActTaken;
    //S_t // state at time step t (Board string)
    //A_t //action at time step t (array of Moves) //Maybe we want separate class?
    //R_t //reward at time step t (see notebook for calcs)

    //constructor
    public Action(Move m){
        this.move = m;
        this.numTimesActTaken = 0;
    }

    //getters
    public Move getMove(){
        return this.move;
    }
    public int getNumTimesActTaken(){
        return this.numTimesActTaken;
    }

    //setters

    //misc

    public void printAction(){
        System.out.println("ACTION: " + this.getMove().toPrettyString());
        System.out.println("Num time action taken: " + this.getNumTimesActTaken());
    }


}