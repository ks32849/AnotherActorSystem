package actor;

import java.util.HashMap;
import java.util.Map;

/**
 * Facility to build a route, that is a mapping of Commands to CommandHandlers.
 * 
 * Follow the typical builder pattern, e.g.:
 * <code>
 * return new Route.RouteBuilder()
 *         .with(Command.class, (cmd, node) -> this.receive(Command)cmd, node)
 *         .build();
 * </code>
 * where you replace Command with your specific Command implementation class.
 * 
 */
public class Route {
    
    private final Map<Class<? extends Command>, CommandHandler> routing;
    
    private Route(RouteBuilder builder) {
        routing = new HashMap<>();
        this.routing.putAll(builder.routing);
    }
    
    /**
     * Internal method to dispatch commands to a handler.
     * 
     * @param cmd
     * @param node 
     */
    void dispatch(Command cmd, ActorNode node) {
        CommandHandler handler = routing.get(cmd.getClass());
        handler.receive(cmd, node);
    }

    public static class RouteBuilder {
        private final Map<Class<? extends Command>, CommandHandler> routing = new HashMap<>();

        /**
         * Adds the command, handler pair to the Route.
         * 
         * @param command - the class the given handles processes
         * @param handler - this is the instance that handles the given command
         * @return this RouteBuilder instance
         */
        public RouteBuilder with(Class<? extends Command> command, CommandHandler handler) {
            routing.put(command, handler);
            return this;
        }  
        
        /**
         * Build a route from the prviously provided command/handler pairs.
         * 
         * @return instance of a Route
         */
        public Route build() {
            return new Route(this);
        }
    }
}

