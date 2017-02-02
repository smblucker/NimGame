/*
 * Support class of NimApp that executes gameplay, stores all state
 * information, and returns values to NimApp.
 * 
 */
package nim;

import java.util.Random;

/**
 *
 * @author seanblucker
 */
public class NimGame 
{
    private int [] nimArray = new int[3];                                       //Array that stores random number of sticks in three rows
    private int randRow;                                                        //Variable that stores random number indicating row choice of AI
    private int randSticks;                                                     //Variable that stores random number indicating stick choice of AI
    
    //Constructor that creates new Nim Game with initial number of sticks in each row, and random values for AI moves
    public NimGame(int[] initialSticks)
    {
        for(int i = 0; i<3; i++)
        {
            if(initialSticks[i] <= 0)
                throw new InvalidSticksEntryException();
        }
        
        for(int i = 0; i<3; i++)
            nimArray[i] = initialSticks[i];
        
        Random rn4 = new Random();
        randRow = rn4.nextInt(3) + 1;
        
        Random rn5 = new Random();
        randSticks = rn5.nextInt(3) + 1;

    }
    
    //Inspector method that returns current number of sticks in specified row r
    public int getRow(int r)
    {
        return nimArray[r];
    }
    
    //Modifier method that removes specified number of sticks (s) from specified row (r)
    public void play(int r, int s) throws NoSuchRowException, IllegalSticksException, NotEnoughSticksException
    {
        //NoSuchRowException
        if(r<0 || r>2)
            throw new NoSuchRowException();
        
        //IllegalSticksException
        if (s<1 || s>3)
            throw new IllegalSticksException();
        
        //NotEnoughSticksException
        if(s>nimArray[r])
            throw new NotEnoughSticksException();
        
        //Remove specified number of sticks from specified row
        else
            nimArray[r] -= s;
    }
    
    //Inspector method that tracks when game is over. Game is over when sum of sticks is zero. 
    public boolean isOver()
    {
        if(nimArray[0] + nimArray[1] + nimArray[2] == 0)
            return true;
        else
            return false;
    }
    
    //Modifier method that takes a random number of sticks from a random row.
    public void AIMove()
    {
        while(nimArray[randRow - 1] < randSticks)
        {
            Random rn4 = new Random();
            randRow = rn4.nextInt(3) + 1;
            
            Random rn5 = new Random();
            randSticks = rn5.nextInt(3) + 1;
        }

        nimArray[randRow - 1] -= randSticks;
    }
    
    //Method that returns state information for JUnit Testing
    public String toString()
    {
        String state = nimArray[0] + " " + nimArray[1] + " " + nimArray[2];
        
        return state;
    }
}
