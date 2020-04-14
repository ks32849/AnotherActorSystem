package actor.sync;

/**
 * Block the thread if getResult is called before a result was stored. If that
 * thread was blocked, it will resume after the result gets set.
 *
 * @author ks32849
 *
 */
public abstract class FutureValue<T> {

    private final Object mutex = new Object();

    protected abstract T getConcreteResult();

    protected abstract void setConcreteResult(T res);

    public T getResult() {
        try {
            synchronized (mutex) {
                if (this.getConcreteResult() == null) {
                    mutex.wait();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return this.getConcreteResult();
    }

    public void setResult(T res) {

        synchronized (mutex) {
            this.setConcreteResult(res);
            mutex.notify();
        }
    }
}
