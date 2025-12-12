package GroupProject;
/**
 *
 * @author USER
 */
public class Info {
    int[] ownerships;
    PlayerState[] playerStates;
    int turn;

    public Info(int[] ownerships, PlayerState[] playerStates, int turn) {
        this.ownerships = ownerships;
        this.playerStates = playerStates;
        this.turn = turn;
    }

    public int[] getLandOwnerships() {
        return ownerships;
    }

    public PlayerState[] getPlayerStates() {
        return playerStates;
    }

    public int getTurn() {
        return turn;
    }
    
    
}
