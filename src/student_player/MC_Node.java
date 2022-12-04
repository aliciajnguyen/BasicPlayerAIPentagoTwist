package student_player;

import boardgame.Move;
import pentago_twist.PentagoBoardState;

import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
import java.util.Comparator;

/* Node implementation for MCST: Uses MC_NodeState */
public class MC_Node {

    private MC_NodeState state;
    private MC_Node parent;
    private ArrayList<MC_Node> children;
    private Move move; //the move that got us to this board state from the parent
    private double curUCTValue;

    //constructor, needs to create a state
    MC_Node(PentagoBoardState boardState){
        MC_NodeState state = new MC_NodeState(boardState);
        this.state = state;
        ArrayList<MC_Node> children = new ArrayList<MC_Node>();
        this.children = children;
        this.curUCTValue = 0;
    }

    //setters
    public void setState(MC_NodeState state){
        this.state = state;
    }
    public void setParent(MC_Node parent){
        this.parent = parent;
    }
    public void setMove(Move move){
        this.move = move;
    }
    public void setUCTValue(double value){
        this.curUCTValue = value;
    }


    //getters
    public MC_NodeState getState(){
        return this.state;
    }
    public MC_Node getParent(){
        return this.parent;
    }
    public ArrayList<MC_Node> getChildren(){
        return this.children;
    }
    public Move getMove(){
        return this.move;
    }
    public double getUCTValue(){
        return this.curUCTValue;
    }


    //misc methodss

    //add a child to this node's array of children
    public void addChild(MC_Node child){
        this.children.add(child);
    }
    
    //method to get a random child from this node's child list
    public MC_Node getRandomChildNode(){
        Random rand = new Random();

        ArrayList<MC_Node> children = this.getChildren();
        return children.get(rand.nextInt(children.size()));
    }

    //a method that takes this node, and returns the child node with the max score
    //REV assuming the 'best move' is the one with the highest denominator??
    public MC_Node getBestChildNode(){
        MC_Node bestChild = Collections.max(this.getChildren(), 
            Comparator.comparing(c -> c.getState().getVisitCount()));        
        return bestChild;

    }

    //method to print the node
    public void print(){
        System.out.println("---------------------------------------------------------------");

        try{
            System.out.println("Node | Move that got us here: " + this.move.toPrettyString());

            System.out.println("New Board:");
            this.getState().getBoard().printBoard();
            System.out.println("Number of children: " + this.children.size());
            System.out.println("Win Count: " + this.getState().getWinScore());
            System.out.println("Visit Count: " + this.getState().getVisitCount());
            System.out.println("Player number: " + this.getState().getPlayerNum());
            System.out.println("Cur UCT Value: " + this.getUCTValue());
        }
        catch(Exception e){
            System.out.println("Error printing node:");
            System.out.println(e.toString());
            System.out.println("Board:");
            this.getState().getBoard().printBoard();
        }

        System.out.println("---------------------------------------------------------------");        
    }
}