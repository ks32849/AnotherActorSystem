package actor.example;

import actor.Actor;
import actor.ActorNode;
import actor.Command;
import actor.Route;

/**
 * Example actor the can receive GreetCommands and replies with a GreetedCommand.
 * 
 * Note how both receivable and reply commands are defined within the actor.
 * 
 */
public class GreetingActor implements Actor {

    /**
     * Command to deliver a greeting to this actor.
     * The command contains the greeting string and an instance to reply to.
     */
    public static final class GreetCommand implements Command {
        public String greeting;
        public ActorNode replyTo;
        public GreetCommand(String greeting, ActorNode replyTo) { 
            this.greeting = greeting; 
            this.replyTo = replyTo;
        }
    }
    
    /**
     * Note how the response to a Command uses the past tense. It is up to the
     * user to define a consistent syntax. Other options might be to provide a 
     * Response or Reploy suffix in the command class name.
     * Also note this command has no instance to reply to.
     */
    public static final class GreetedCommand implements Command {
        public String reply;
        public GreetedCommand(String reply) { 
            this.reply = reply; 
        }
    }
    
    /**
     * Specifies the command - command-handler mappings.
     * The RouteBuilder provides a utility to create a set of such mappings and
     * build a Route from them.
     * The lambda is a useful way to provide the CommandHandler to handle the GreetCommand.
     * Note it is necessary to cast the command parameter to the correct type.
     * The node parameter is the wrapper of <bold>this</bold> actor and is not used in this example.
     * It is provided for the case where a reply to commands sent by this actor are expected.
     * The foreign actor we want to reply to is specified in the received GreetCommand itself.
     * 
     * @return Route
     */
    @Override
    public Route routes() {
         return new Route.RouteBuilder()
             .with(GreetCommand.class, (cmd, node) -> this.receive((GreetCommand)cmd))
             .build();
     }
          
    /**
     * Handle the GreetCommand.
     * 
     * Sends a reply to the node specified in the command itself.
     * @param cmd - received command
     */
    private void receive(GreetCommand cmd) {
        cmd.replyTo.send(new GreetedCommand("And a " + cmd.greeting + " to you too!"));
    }
}