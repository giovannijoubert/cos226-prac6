import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class Semaphore {
    private static Semaphore semaphore;
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    int capacity = 3;
    int state = 0;

    public void lock() {
        lock.lock();
        try {
            if(state == capacity)
                System.out.println(ThreadID.get() + " is waiting");
          while (state == capacity){
            try	{
                condition.await();
			}
			catch (InterruptedException exception)	{
				//handleit
			}    
          }
               
      state++;
      } finally {
          lock.unlock();
      }
    }

      public void unlock() {
        lock.lock();
        try {
            state--;
          condition.signalAll();
        } finally {
          lock.unlock();
      }
    }

    public int getState(){
        return capacity-state;
    }

    public final static Semaphore getInstance(){
        if(semaphore == null){
            semaphore = new Semaphore();
        }
        return semaphore;
    }

}