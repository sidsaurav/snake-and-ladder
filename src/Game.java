import model.Board;
import model.Dice;
import model.Player;

import java.util.*;

public class Game {
    private int boardSize;
    private Board gameBoard;
    private Map<String, Player> players; //playerId, player -> Player DB
    //always but player identifier not the whole player...
    private Map<String,Integer> playerMap; //playerId, boxNum -> both arguments are id
    private Map<Integer, Integer> snakes;
    private Map<Integer, Integer> ladders;
    private Scanner scn;
    private int maxDiceVal;

    public Game (int boardSize) {
        this.boardSize = boardSize;
        players = new HashMap<>();
        gameBoard = new Board(boardSize);
        playerMap = new HashMap<>();
        snakes = new HashMap<>();
        ladders = new HashMap<>();
        maxDiceVal = 6;
        scn = new Scanner(System.in);
    }

    private void initialise(){
        getSnakes();
        getLadders();
        getPlayers();
        System.out.println("Game started. All players on 1");
    }

    private void getSnakes(){
        System.out.println("Enter number of Snakes");
        int snakesNum = 0;
        snakesNum = scn.nextInt();

        for(int i = 0; i<snakesNum; i++){
            int head = scn.nextInt();
            int tail = scn.nextInt();
            snakes.put(head, tail);
        }
    }

    private void getLadders(){
        System.out.println("Enter number of Ladders");
        int ladderNum = 0;
        ladderNum = scn.nextInt();

        for(int i = 0; i<ladderNum; i++){
            int head = scn.nextInt();
            int tail = scn.nextInt();
            ladders.put(head, tail);
        }
    }

    private void getPlayers() {
        System.out.println("Enter number of players");
        int playerNum = scn.nextInt();

        System.out.println("Enter players name");
        for(int i = 0; i<playerNum; i++){
            String playerName = "";
            playerName = scn.next();
            String pId = String.valueOf(playerMap.size());
            players.put(pId, new Player(playerName, pId));
            playerMap.put(pId, 1);
        }
    }

    void start() {
        initialise();

        Integer currPlayerIndex = 0;
        while(true) {
            String playerId = String.valueOf(currPlayerIndex);
            int numRolled = Dice.roll(maxDiceVal);
            Player currPlayer = players.get(playerId);
            System.out.println(currPlayer);
            makeMove(currPlayer.getPlayerID(), numRolled);
            if(playerMap.get(playerId) == 100){
                declareWinner(currPlayer.getPlayerID());
                break;
            }
            currPlayerIndex = (currPlayerIndex + 1)%players.size();
        }
    }

    //always pass id, not whole player.
    private void makeMove(String playerId, int numRolled) {
        int currNum = playerMap.get(playerId);
        int destBoxNum = Math.min(playerMap.get(playerId) + numRolled, 100);
        if(ladders.containsKey(destBoxNum)){
            destBoxNum = ladders.get(destBoxNum);
        }
        if(snakes.containsKey(destBoxNum)){
            destBoxNum = snakes.get(destBoxNum);
        }
        System.out.println(players.get(playerId).getPlayerName() +  " rolled a " + numRolled + " and moved from " + playerMap.get(playerId) + " to " + destBoxNum);
        playerMap.remove(playerId);
        playerMap.put(playerId, destBoxNum);
    }

    private void declareWinner(String playerID) {
        System.out.println(players.get(playerID).getPlayerName() + " won the game!");
    }
}
