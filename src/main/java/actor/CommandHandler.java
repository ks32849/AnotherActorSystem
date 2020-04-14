package actor;

/**
 * Commands are delivered to implementations of this interface.
 * 
 * For each command there should be a different command handler instance, because
 * java does not support implementing this method with subclasses of Command.
 * One way to achieve this is to use lambdas when specifying the handlers
 * -- see Route and RouteBuilder.
 */
public interface CommandHandler {
    
    /**
     * Process the given command.
     * 
     * @param cmd - command to process
     * @param node - instance to send replies too.
     */
    void receive(Command cmd, ActorNode node);
}
