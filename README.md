PingPongGame
============
Language: Java
IDE: Netbeans

1. Bruce Leeds wins the most. Serena Williamson loses the most.

When I was constructing my solution, I had to consider the following factors: 
- Serving first provides an advantage of winning a game
- Each player must play against another player the same amount of games 

Given below are all the possible game arrangements:  
(Player 1 [Serve First]) vs (Player 2)
1. Bruce Leeds vs. Serena Williamson
2. Bruce Leeds vs. Jean Claude Van Dime
3. Serena Williamson vs. Bruce Leeds
4. Serena Williamson vs. Jean Claude Van Dime
5. Jean Claude Van Dime vs. Bruce Leeds
6. Jean Claude Van Dime vs. Serena Williamson

By having these arrangements, I am able isolate the "serving first advantage" variable. 

To ensure a player is playing against another player the same amount of games, the number of
samples must be in sets of 6 so that each game arrangement is represented equally.  

I've noticed Bruce Leeds and Jean Claude Van Dime win percentages were really close to each other.
But by running a very large number of samples, I was about to find exact convergent points of
each player's win percentage.


2. 600,000 samples (100,000 for each game arrangement)
