package actor;

/**
 * Wrapper of foreign actor.
 * 
 * ActorNodes can only be created via the ActorSystem.
 * They provide a way to send commands.
 */
public interface ActorNode {
    
    /**
     * Send a command to the actor represented by this node.
     * 
     * @param cmd - instance of Command
     */
    public void send(Command cmd) ;
}