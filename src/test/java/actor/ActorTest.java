package actor;

import actor.example.GreetingActor;
import actor.sync.FutureValue;
import actor.sync.ModifyableFuture;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;


public class ActorTest {
    
    // A test actor to receive the reply of the actor under test (GreetingActor)
    public static class TestActor implements Actor {
        FutureValue<String> future = new ModifyableFuture<>();
        
        @Override
        public Route routes() {
            return new Route.RouteBuilder()
                .with(GreetingActor.GreetedCommand.class, (cmd, self) -> this.receive((GreetingActor.GreetedCommand)cmd))
                .build();
        }
        
        private void receive(GreetingActor.GreetedCommand cmd) {
            future.setResult(cmd.reply);
        }
    }    

    
    private ActorNode greetingNode; //actor we want to test
    private ActorNode testNode;     //actor the reply should be sent to
    private final TestActor testActor = new TestActor();
    
    @Before
    public void setUp() {
        greetingNode = new ActorSystems().createActor(new GreetingActor(), "greeting");
        testNode = new ActorSystems().createActor(testActor, "test");
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testActorRepliesToCommand() throws InterruptedException {
        //send a command and provide an actor to reply to
        greetingNode.send(new GreetingActor.GreetCommand("hello", testNode));
        
        String reply = (String)testActor.future.getResult();
        assertEquals("And a hello to you too!", reply);
    }    
}
