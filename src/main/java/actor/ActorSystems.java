package actor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Facility to creator actor nodes and store them.
 * 
 * TODO: make them findable by name
 * 
 */
public class ActorSystems implements ActorSystem {

    List<ActorNode> nodes = new ArrayList<>();

    @Override
    public ActorNode createActor(Actor actor, String name) {
        ActorNodeImpl node = new ActorNodeImpl(name, actor);
        node.start();
        nodes.add(node);
        return node;
    }

}

/**
 * Runnable implementation that uses a blocking queue to send and deliver 
 * Commands to CommandHandlers based on an Actors Route.
 * 
 */
class ActorNodeImpl implements ActorNode, Runnable {

    private final String name;
    private final Route route;
    private final BlockingQueue<Command> q;


    public ActorNodeImpl(String name, Actor actor) {
        this.name = name;
        this.route = actor.routes();
        this.q = new LinkedBlockingQueue<>();
    }
       
    public void start() {
        new Thread(this).start();
    }
    
    @Override
    public void send(Command cmd) {
        try {
            this.q.put(cmd);
        } catch (InterruptedException ex) {
            throw new RuntimeException("Problems sending command", ex);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Command cmd = receive();
                route.dispatch(cmd, this);
                //cmd.execute(actor, this);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
    
    //this will block the Actor until a Command is available in the queue
    private Command receive() throws InterruptedException {
        Command cmd = q.take();
        return cmd;
    }
}
