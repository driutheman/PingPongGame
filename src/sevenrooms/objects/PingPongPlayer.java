package sevenrooms.objects;

import java.util.EnumMap;
import java.util.Map;
import sevenrooms.constants.ShotTypes;
import sevenrooms.exceptions.NumberRangeOutOfBoundException;
import sevenrooms.exceptions.UnknownShotTypeException;

/**
 * @author rayedchan
 * A class to represent a Ping Pong player.
 */
public class PingPongPlayer 
{
    // Name of the Player
    private String name;
    
    // Player's hit shot percentages
    private EnumMap<ShotTypes,Integer> hit_shot_percentages;
    
    // Player's return shot percentages
    private EnumMap<ShotTypes,Integer> return_percentages;
    
    // Store max percent range of each hit shot 
    private EnumMap<ShotTypes,Integer> hit_shot_max_range_percentages;
    
    // Number of wins a player has
    private int numberOfWins = 0;
    
    /** 
     * Constructor
     * @param   name                    Player's full name
     * @param   hit_shot_percentages    Player's hit percentages of each type of shot
     * @param   return_percentages      Player's return percentages of each type of shot 
     */
    public PingPongPlayer(String name, EnumMap<ShotTypes,Integer> hit_shot_percentages, EnumMap<ShotTypes,Integer> return_percentages)
    {
        this.name = name;
        this.hit_shot_percentages = hit_shot_percentages;
        this.return_percentages = return_percentages;
        
        // Call helper method to determine the max range percent of each hit shot type
        this.hit_shot_max_range_percentages = determineHitShotMaxRangePercent(hit_shot_percentages);
    }
    
   /** 
    * Helper method used to create a max range limit of each hit shot. 
    * The probabilities of all the hit shot type add up to 100 percent.
    * E.g. Flat = 47%, Slice 25%, Topspin = 25%, Unreturnable = 3%
    * Threshold Result: 47% 72% 97% 100% 
    * Ranges for each shot: 1 - 47, 48 - 72, 73 - 97, 98 - 100 
    * @param   hit_shot_percentages    Player's hit shot type percentages 
    * @return  Max percent range of each hit shot type 
    */
    private EnumMap<ShotTypes,Integer> determineHitShotMaxRangePercent(EnumMap<ShotTypes,Integer> hit_shot_percentages)
    {
        EnumMap<ShotTypes,Integer> hit_shot_threshold = new EnumMap<ShotTypes,Integer>(ShotTypes.class);
        ShotTypes prevEntryKey = null; // Keep track of previous shot type inside for loop
        
        // Calculate max range percent of each shot type 
        for(Map.Entry<ShotTypes,Integer> entry : hit_shot_percentages.entrySet())
        {
            ShotTypes currentKey = entry.getKey();
            Integer currentValue = entry.getValue();
            
            // First item already has max percent
            if(prevEntryKey == null)
            {
                hit_shot_threshold.put(currentKey, currentValue);
            }
            
            // Second item and above need to be calculated
            else
            {
                // Add previous max percent with current hit percent to get max range for current shot type
                hit_shot_threshold.put(currentKey, hit_shot_threshold.get(prevEntryKey) + currentValue);
            }
            
            prevEntryKey = entry.getKey(); // Store the current key as previous key for the next iteration
        }
        
        return hit_shot_threshold;
    }
    
    /** 
     * Determines the type of shot a player is going to hit
     * @param number    A number between 1 to 100
     * @return the type of hit shot as an enum
     * @throws NumberRangeOutOfBound if given number is not between 1 to 100
     * @throws UnknownShotTypeException if unknown shot type
     */
    public ShotTypes determineTypeOfShot(Integer number) throws NumberRangeOutOfBoundException, UnknownShotTypeException
    {
        // Throw exception here if number is not between 1 to 100
        if(number < 1 || number > 100)
        {
            throw new NumberRangeOutOfBoundException("Number must be between 1 to 100.");
        }
        
        // Iterate max range of each type of shot
        for(Map.Entry<ShotTypes,Integer> entry : this.hit_shot_max_range_percentages.entrySet())
        {
            ShotTypes shotType = entry.getKey();
            Integer shotTypeMaxRange = entry.getValue();
            
            // Determines if the given number is within the current shot type range
            if(number <= shotTypeMaxRange)
            {
                return shotType;
            }
        }
        
        // Throw unknown shot type exception here
        throw new UnknownShotTypeException("Unknown shot type.");
    }
    
   /** 
    * Determine if a player returns a shot
    * @param   randNum     Number between 1 to 100  
    * @param   shotType    An enum that represents the type of shot
    * @param   serveAdjustment  Percentage decrease of returning a serve
    * @return  true if player returns shot; false otherwise
    *      E.g. Given random integer 25,  80% success return percent, and 0 serveAdjustment
    *           Results: 1 to 80 produces true; 81 to 100 produces false => true
    */ 
    public boolean isShotReturn(Integer randNum, ShotTypes shotType, Integer serveAdjustment)
    {
        int returnShotPercent = this.return_percentages.get(shotType); // Get player's return percentage of given shot type       
        return randNum <= (returnShotPercent - serveAdjustment);
    }
    
    // Getter Methods
    public String getName()
    {
        return name;
    }
        
    public EnumMap<ShotTypes,Integer> getHitShotPercentages()
    {
        return hit_shot_percentages;
    }
    
    public EnumMap<ShotTypes,Integer> getReturnPercentages()
    {
        return return_percentages;
    }
    
    public EnumMap<ShotTypes,Integer> getHitShotMaxRangePercentages()
    {
        return hit_shot_max_range_percentages;
    }
    
    public int getNumberOfWins()
    {
        return numberOfWins;
    }
    
    // Setter Methods
    public void setName(String name)
    {
        this.name = name;
    }
    
    public void setHitShotPercentages(EnumMap<ShotTypes,Integer> hit_shot_percentages)
    {
        this.hit_shot_percentages = hit_shot_percentages;
    }
    
    public void setReturnPercentages(EnumMap<ShotTypes,Integer> return_percentages)
    {
        this.return_percentages = return_percentages;
    }
    
    public void setHitShotMaxRangePercentages(EnumMap<ShotTypes,Integer> hit_shot_max_range_percentages)
    {
        this.hit_shot_max_range_percentages = hit_shot_max_range_percentages;
    }
       
    public void setNumberOfWins(int numberOfWins)
    {
        this.numberOfWins = numberOfWins;
    }
    
    // String representation of PingPongPlayer object
    @Override
    public String toString()
    {
        return String.format("Player's Name: %s\nHit Shot Percentages: %s \nReturn Shot Percentages: %s "
                + "\nMax Range Hit Shot Percentages %s \nWins: %s\n", name, hit_shot_percentages,
                return_percentages, hit_shot_max_range_percentages, numberOfWins);
    }
}
