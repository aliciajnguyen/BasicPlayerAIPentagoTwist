package student_player;

import boardgame.Move;
import pentago_twist.PentagoBoardState;
import pentago_twist.PentagoMove;
import static pentago_twist.PentagoBoardState.Piece.*;


import java.util.ArrayList;
import java.util.Random;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;

public class MyTools {

    public static double getSomething() {
        return Math.random();
    }

    //a method to get the initial move based on a winning state heuristc
    //only use for first 2 turns
    public static Move getInitMove(PentagoBoardState boardClone, int turnPlayer){
            Random rand = new Random(System.currentTimeMillis());
         
            PentagoMove PMove1 = new PentagoMove("1 1 0 0 " + turnPlayer);
            PentagoMove PMove2 = new PentagoMove("1 4 1 0 " + turnPlayer);
            PentagoMove PMove3 = new PentagoMove("4 1 2 0 " + turnPlayer);
            PentagoMove PMove4 = new PentagoMove("4 4 3 0 " + turnPlayer);

            ArrayList<PentagoMove> moves = new ArrayList();

            //if space is free add to list for rand selection
            if (boardClone.getPieceAt(1, 1)== EMPTY){ 
                moves.add(PMove1);
            }
            if (boardClone.getPieceAt(1, 4)== EMPTY){
                moves.add(PMove2);
            }
            if (boardClone.getPieceAt(4, 1)== EMPTY){
                moves.add(PMove3);
            }
            if (boardClone.getPieceAt(4, 4)== EMPTY){
                moves.add(PMove4);
            }

            Move myMove = (Move) moves.get(rand.nextInt(moves.size()));            
            return myMove;
    }

    //a method that takes a move and a board state and performs updates 
    public static void saveTurn(PentagoBoardState board, Move m){
        
        //check if board state exists
        //if it does, update existing

        //otherwise create the state, add new action
        State s = new State(board);
        s.addNewAction(m);

        //save to a file
        writeTurn(s);
    }

    //a method that takes an obhects and write to disk 
    public static void writeTurn(Object serObj){
        
        //https://stackoverflow.com/questions/10654236/java-save-object-data-to-a-file
        final String path="C:/Users/alici/FP1/pentago_twist/data";

        try(ObjectOutputStream write= new ObjectOutputStream (new FileOutputStream(path)))        {
            write.writeObject(serObj);
        }

        
        catch(NotSerializableException nse){
            //do something
            nse.printStackTrace();
        }
        catch(IOException eio){
            //do something
            eio.printStackTrace();
        }
        catch(Exception e){
            System.out.println("Saving state: Exception:");
            System.out.println(e.toString());
        }
    }

}