package student_player;

import boardgame.Move;
import pentago_twist.PentagoBoardState;

import java.io.Serializable;
import java.util.ArrayList;
//import java.io.IOException;

/* A class to represent board states */

public class State implements Serializable{

    //default serialVersion id
    private static final long serialVersionUID = 1L;

    //attributes
    private PentagoBoardState board;
    private String boardString;
    private int turnNum;
    private ArrayList<Action> possibleActions;
    private int avgMovesTilFin; //put in action instead?

    //S_t // state at time step t (Board string)
    //A_t //action at time step t (array of Moves) //Maybe we want separate class?
    //R_t //reward at time step t (see notebook for calcs)

    //constructor
    public State (PentagoBoardState board){
        this.board = board;
        this.boardString = board.toString();
        this.turnNum = board.getTurnNumber();
        ArrayList<Action> possibleActions = new ArrayList<Action>();
        this.possibleActions = possibleActions;
    }

    //getters
    public PentagoBoardState getBoard(){
        return this.board;
    }

    public ArrayList<Action> getpossibleActions(){
        return this.possibleActions;
    }

    //setters

    //misc
    public void addNewAction(Move m){
        Action a = new Action(m);
        this.possibleActions.add(a);

        //DONT FORGET TO SORT
    }

    //method to UPDATE an action from the results of a game
    public void updateAction(Action a){

    }

    public void printState(){
        System.out.println("---------------------------------");
        System.out.println("STATE");
        System.out.println("---------------------------------");
        this.getBoard().printBoard();
        System.out.println("ACTIONS");
        for (Action a: this.getpossibleActions()){
            a.printAction();
        }
        System.out.println("---------------------------------");

    }
}