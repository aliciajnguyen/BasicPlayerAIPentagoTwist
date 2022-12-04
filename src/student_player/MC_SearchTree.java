package student_player;

import pentago_twist.PentagoBoardState;

import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;

/* Monte Carlo Search Tree representation 
*
*A search tree comprised on of MC_Nodes which encapsulate MC_NodeStates
*
*/

public class MC_SearchTree {
    private MC_Node root;
    private MC_Node curNode; //necessary?

    //constructor
    MC_SearchTree(PentagoBoardState boardState){
        MC_Node root = new MC_Node(boardState);
        this.root = root;
    }
    
    //setters
    public void setRoot(MC_Node root){
        this.root = root;
    }
    public void setCurrent(MC_Node cur_Node){
        this.curNode = cur_Node;
    }
    //getters
    public MC_Node getRootNode(){
        return this.root;
    }
    public MC_Node getCur_Node(){
        return this.curNode;
    }
}