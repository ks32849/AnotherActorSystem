package actor.sync;

public class ModifyableFuture<T> extends FutureValue<T> {

    private T value;

    @Override
    protected T getConcreteResult() {
        return value;
    }

    @Override
    protected void setConcreteResult(T res) {
        value = res;
    }

}