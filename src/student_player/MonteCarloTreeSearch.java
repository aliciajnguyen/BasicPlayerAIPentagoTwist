package student_player;


import boardgame.Board;
import boardgame.Move;
import pentago_twist.PentagoBoardState;
import pentago_twist.PentagoMove;

import java.util.ArrayList;
import java.util.Random;


/* MCST algorithm implementation:
*
* Uses MC_Node, MC_NodeState, MC_SearchTree, UC_Tree Classes
* 
* -MonteCarloSearchTree
* -- MC_Node
* --- MC_NodeState
* -- MC_SearchTree
* -- UC_Tree Classes
*
* External Code Sources (also in Sources Cited): https://www.baeldung.com/java-monte-carlo-tree-search
*/

public class MonteCarloTreeSearch{
    int win_score = 1; //the reward we give
    int level;
    int opponent;

    //overall implementation of the the MCTS
    public Move findNextMove(PentagoBoardState board, int player_num){
        int time_limit = 1700;     //note that end time is our variable letting the algo run
        long start_time = System.currentTimeMillis();
        opponent = 1 - player_num;

        //initialize the search tree for this iteration of the algorithm
        MC_SearchTree tree = new MC_SearchTree(board); 
        MC_Node rootNode = tree.getRootNode();
        rootNode.getState().setPlayerNum(opponent);

        //while the current time - start time is less than elapsed time
        while(System.currentTimeMillis() - start_time < time_limit){

            //SELECTION
            MC_Node promisingNode = selectPromisingNode(rootNode);         
                        
            //EXPANSION

            //check if the selected node is a terminal node (ends the game)
            //IF THERE IS NO WINNER in this state, expand node            
            if (promisingNode.getState().getBoard().getWinner() 
              == Board.NOBODY) {
                expandNode(promisingNode);
            }
            
            //REV note that if we're optimizing move selection, shouldn't select child randomly?
            MC_Node nodeToExplore = promisingNode;
            if (promisingNode.getChildren().size() > 0) {
                nodeToExplore = promisingNode.getRandomChildNode();
            }
            
            //SIMULATION          
            int playoutResult = simulateRandomPlayout(nodeToExplore);
            
            //BACKPROPOGATION
            backPropogation(nodeToExplore, playoutResult);
        }

        //build the tree now select the 'best' child node from the root
        //which represents the best move from the current state
        MC_Node winningMoveNode = rootNode.getBestChildNode();
        Move bestMove = winningMoveNode.getMove();
        return bestMove;
    }

/****************************************************************************************
SELECTION PHASE

Methods helpful for the selection phase of the MCTS Algo 
****************************************************************************************/
    private MC_Node selectPromisingNode(MC_Node rootNode) {
        MC_Node node = rootNode;
        try{ 
            while (node.getChildren().size() != 0) {
                node = UC_Tree.findBestNodeWithUCT(node);
            }
        }
        catch(Exception e){
            System.out.println("MCST: selectPromisingNode: Exception:");
            System.out.println(e.toString());
        }
        return node;
    }

/****************************************************************************************
EXPANSION PHASE
Methods helpful for the expansion phase of the MCTS Algo:
    To save on processing in the tree policy, we'll just generate a 
    subset of all legal moves, then randomly select one and process it, 
    (back in the findBestMove function). The original intent was to generate
    this subset using a policy ascertained from a state value function.
    Here  in expandNode we create the new nodes representing the potential moves to be explored. 
****************************************************************************************/
private void expandNode(MC_Node node) {
    Random rand = new Random();
    int numChildren = 10;

    //get all legal moves
    ArrayList<PentagoMove> allPossibleMoves = node.getState().getBoard().getAllLegalMoves();

    while(numChildren>0){
    
        //clone the board, get a (random) legal move from list and del, update the new board
        PentagoBoardState newBoard = (PentagoBoardState)node.getState().getBoard().clone();
        PentagoMove move = allPossibleMoves.get(rand.nextInt(allPossibleMoves.size()));
        allPossibleMoves.remove(move);
        newBoard.processMove(move);
       
            //now create a node for the board w the new move, update old node as parent
            MC_Node newNode = new MC_Node(newBoard);
        try{
            //newNode.getState().setBoard(newBoard);
            newNode.setMove(move);
            newNode.setParent(node);        
            newNode.getState().setPlayerNum(node.getState().getOpponent());
            node.addChild(newNode); 
        }
        catch(Exception e){
            System.out.println("MCST: expandNode: Exception:");
            System.out.println(e.toString());
        }
    
        numChildren--;
    }
}

/****************************************************************************************
SIMULATION PHASE
Methods helpful for the simulation phase of the MCTS Algo 
    if we wanted to optimize, we could also make our rollouts non rand
    ie we could actually select moves stategically instead of randomly during rollouts
****************************************************************************************/
private int simulateRandomPlayout(MC_Node node) {
    Random rand = new Random();

    //create a clone of the board and check its win status
    PentagoBoardState tempBoard = (PentagoBoardState)node.getState().getBoard().clone();
    int boardWinnerStatus = tempBoard.getWinner();
    
    //play the game while no one has won
    while (boardWinnerStatus==Board.NOBODY){       
        //turn player gets a random move and processes it
        ArrayList<PentagoMove> allPossibleMoves = tempBoard.getAllLegalMoves();
        PentagoMove move = allPossibleMoves.get(rand.nextInt(allPossibleMoves.size()));
        tempBoard.processMove(move);
        
        boardWinnerStatus = tempBoard.getWinner();
    }

    return boardWinnerStatus;
}


/****************************************************************************************
BACKPROPOGATION PHASE

Methods helpful for the backpropogation phase of the MCTS Algo 
****************************************************************************************/

//a method to propogate the results of the simulation up the tree
private void backPropogation(MC_Node nodeToExplore, int playerNo) {
    MC_Node tempNode = nodeToExplore;
    while (tempNode != null) {
        tempNode.getState().incrementVisit();
        if (tempNode.getState().getPlayerNum() == playerNo) {
            tempNode.getState().addWinScore(win_score);
        }
        tempNode = tempNode.getParent();
    }
}

}