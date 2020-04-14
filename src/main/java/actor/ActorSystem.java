package actor;

/**
 * An actor system provides means to create and find actors (wrapped into ActorNodes).
 * 
 * Actors run in their own thread and are started by the ActorSystem.
 * The means for communication between Actors is soley based on sending Commands,
 * which are processed asynchronously by the receiving actor, i.e. the processing
 * runs in the receiving actors thread which is different to that of the sender.
 * 
 * If the sender of a command is expecting a reply the command should contain
 * a field providing the ActorNode of the sender.
 * 
 */
public interface ActorSystem {
    
    /**
     * Create a representation of the given actor such that commands can be sent
     * to it. Identify the actor by the given name.
     * 
     * @param actor - instance of Actor
     * @param actorName - name of the Actor
     * @return Wrapper of an Actor that can be used to send commands to.
     */
    public ActorNode createActor(Actor actor, String actorName);
}

