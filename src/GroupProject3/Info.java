package GroupProject3;
/**
 *
 * @author USER
 */
public class Info {
    int[] ownerships;
    PlayerState[] playerStates;

    public Info(int[] ownerships, PlayerState[] playerStates) {
        this.ownerships = ownerships;
        this.playerStates = playerStates;
    }

    public int[] getLandOwnerships() {
        return ownerships;
    }

    public PlayerState[] getPlayerStates() {
        return playerStates;
    }
    
    
}
