package sevenrooms.objects;

import java.util.Random;
import sevenrooms.constants.ShotTypes;
import sevenrooms.exceptions.NumberRangeOutOfBoundException;
import sevenrooms.exceptions.UnknownShotTypeException;

/**
 * @author rayedchan
 * Represents a ping pong game
 */
public class PingPongGame 
{
    boolean whoseTurnToServe = true; // True for side 1 and false for side 2
    boolean whichSidePongIsGoingTo = false; // True for side 1 and false for side 2
    int sideOneScore = 0; 
    int sideTwoScore = 0;

    PingPongPlayer player1; // Assumed player one is on side one
    PingPongPlayer player2; // Assumed player two is on side two
    
    /**
     * Constructor: 1 vs 1 ping pong game
     * @param   player1     Ping Pong Player One
     * @param   player2     Ping Pong Player Two
     */
    public PingPongGame(PingPongPlayer player1, PingPongPlayer player2)
    {
        this.player1 = player1;
        this.player2 = player2;
    }
    
    /*
     * Starts the ping pong game
     */
    public void startPingPongGame() throws NumberRangeOutOfBoundException, UnknownShotTypeException
    {
        int turnCount = 1; // Used to determine serving position
        Random randomNumberGenerator = new Random();
        String playerOneName = player1.getName();
        String playerTwoName = player2.getName(); 
        
        // Loop until one player reaches 21
        while(this.sideOneScore != 21 && this.sideTwoScore != 21)
        {
            // Alternate after every five serves; Rotation happens on the sixth 
            if(turnCount % 6 == 0)
            {
                this.whoseTurnToServe = (this.whoseTurnToServe) ? false : true;
            }
             
            this.whichSidePongIsGoingTo = !this.whoseTurnToServe; // Opposite side of player who is serving; Refresh before starting new rally
            rally(randomNumberGenerator, playerOneName, playerTwoName);
            System.out.printf("The score is now %s: %s, %s: %s\n", playerOneName, this.sideOneScore, playerTwoName, this.sideTwoScore);
            turnCount++;
        }
        
        // Increment win count
        if(this.sideOneScore == 21)
        {
             int winCount = player1.getNumberOfWins();
             player1.setNumberOfWins(++winCount);
        }
        
        else
        {         
            int winCount = player2.getNumberOfWins();
            player2.setNumberOfWins(++winCount);
        }
    }
    
    /**
     * Rally between players. Ends when til someone scores.
     * @param   randNumGen      Random number generator
     * @param   playerOneName   Name of player one
     * @param   playerTwoName   Name of player two
     */
    private void rally(Random randNumGen, String playerOneName, String playerTwoName) throws NumberRangeOutOfBoundException, UnknownShotTypeException
    {   
        // Serving shot
        int shotTypeNum = randNumGen.nextInt(100) + 1;
        ShotTypes shotType = (whoseTurnToServe) ? this.player1.determineTypeOfShot(shotTypeNum) : this.player2.determineTypeOfShot(shotTypeNum);  
        System.out.printf("%s served a %s [%s] to %s.\n",(whoseTurnToServe)? playerOneName : playerTwoName, shotType ,shotTypeNum, (whichSidePongIsGoingTo)? playerOneName : playerTwoName);
        
        // Returning serve shot
        int returnNum = randNumGen.nextInt(100) + 1;
        boolean isShotReturn = (whichSidePongIsGoingTo) ? this.player1.isShotReturn(returnNum, shotType, 10) : this.player2.isShotReturn(returnNum, shotType, 10);
        
        // Variable used for accurate logging purposes and validation
        boolean isAServeShot = !isShotReturn; 
        
        // Rally
        while(isShotReturn)
        {
            System.out.printf("%s successfully returns %s [%s].\n",(whichSidePongIsGoingTo)? playerOneName : playerTwoName, shotType, returnNum);
            
            // Alternate destination of ping pong ball
            this.whichSidePongIsGoingTo = (this.whichSidePongIsGoingTo) ? false : true;
           
            // Determine return shot type
            shotTypeNum = randNumGen.nextInt(100) + 1;
            shotType = (!whichSidePongIsGoingTo) ? this.player1.determineTypeOfShot(shotTypeNum) : this.player2.determineTypeOfShot(shotTypeNum);
            System.out.printf("%s returns with %s [%s].\n",(!whichSidePongIsGoingTo)? playerOneName : playerTwoName, shotType, shotTypeNum);
            
            // Determine a response from a return shot
            returnNum = randNumGen.nextInt(100) + 1;
            isShotReturn = (whichSidePongIsGoingTo) ? this.player1.isShotReturn(returnNum, shotType, 0) : this.player2.isShotReturn(returnNum, shotType, 0);
        }
        
        // Player One scores
        if(!whichSidePongIsGoingTo)
        {
            sideOneScore++; 
            System.out.printf("%s was unable to return%s%s [%s], %s scores a point!\n", playerTwoName, isAServeShot ? " serve ":" ", shotType, returnNum, playerOneName);  
        }
        
        // Player Two scores
        else
        {
            sideTwoScore++;
            System.out.printf("%s was unable to return%s%s [%s], %s scores a point!\n", playerOneName, isAServeShot ? " serve ":" ", shotType, returnNum, playerTwoName);
        }
    }
}
