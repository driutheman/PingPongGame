package sevenrooms.driver;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Random;
import sevenrooms.constants.ShotTypes;
import sevenrooms.exceptions.NumberRangeOutOfBoundException;
import sevenrooms.exceptions.UnknownShotTypeException;
import sevenrooms.objects.PingPongGame;
import sevenrooms.objects.PingPongPlayer;

/**
 * @author rayedchan
 */
public class TestDriver 
{
    public static void main(String[] args) throws NumberRangeOutOfBoundException, UnknownShotTypeException 
    {
        // Enum constants used as key in Map Object
        ShotTypes[] shotTypes = ShotTypes.values();
        
        // Stage player's hit and return statistics
        EnumMap<ShotTypes,Integer> p1_hit_percentages = transformArrayToMap(new int[]{47,25,25,3},shotTypes);     
        EnumMap<ShotTypes,Integer> p1_return_percentages = transformArrayToMap(new int[]{80,45,75,0},shotTypes);
        EnumMap<ShotTypes,Integer> p2_hit_percentages = transformArrayToMap(new int[]{10,20,66,4},shotTypes);
        EnumMap<ShotTypes,Integer> p2_return_percentages = transformArrayToMap( new int[]{65,50,85,0},shotTypes);
        EnumMap<ShotTypes,Integer> p3_hit_percentages = transformArrayToMap(new int[]{70,10,15,5},shotTypes);  
        EnumMap<ShotTypes,Integer> p3_return_percentages = transformArrayToMap(new int[]{90,25,85,0},shotTypes);
        
        // Create ping pong Players
        PingPongPlayer player1 = new PingPongPlayer("Bruce Leeds", p1_hit_percentages, p1_return_percentages);
        PingPongPlayer player2 = new PingPongPlayer("Serena Williamson", p2_hit_percentages, p2_return_percentages);
        PingPongPlayer player3 = new PingPongPlayer("Jean Claude Van Dime", p3_hit_percentages, p3_return_percentages);
        
        // Create ping pong game between two players and begin game
        PingPongGame simpleGame = new PingPongGame(player1, player2);
        simpleGame.startPingPongGame();
        
        // Extreme round-robin game; Comment out the System.out.println lines in PingPongGame when running this
        /*int tournmentCount = 0;
        while(tournmentCount < 100000)
        {
            // Represent game arrangement equally
            PingPongGame game = new PingPongGame(player1, player2);
            PingPongGame game2 = new PingPongGame(player1, player3);
            PingPongGame game3 = new PingPongGame(player2, player1);
            PingPongGame game4 = new PingPongGame(player2, player3);
            PingPongGame game5 = new PingPongGame(player3, player1);
            PingPongGame game6 = new PingPongGame(player3, player2);
            game.startPingPongGame();
            game2.startPingPongGame();
            game3.startPingPongGame();
            game4.startPingPongGame();
            game5.startPingPongGame();
            game6.startPingPongGame();
            tournmentCount++;
        }*/
              
        // Print player's statistics
        System.out.println(player1);
        System.out.println(player2);
        System.out.println(player3);
    }
    
    /**
     * Method created for convenience of staging data for PingPongPlayer
     * @param   array       Use percentages as values
     * @param   shotTypes   Use Enum as keys
     * @return  Associate mapping between shot type and percentage 
     */
    public static EnumMap<ShotTypes,Integer> transformArrayToMap(int[] array, ShotTypes[] shotTypes)
    {
        EnumMap<ShotTypes,Integer> map = new EnumMap<ShotTypes,Integer>(ShotTypes.class);
        int length = shotTypes.length;
        
        for(int i = 0; i < length; i++)
        {
            map.put(shotTypes[i], array[i]); 
        }
        
        return map;
    }
}

