import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
import java.util.concurrent.*; 
public class Semaphore {
    private static Semaphore semaphore;
    private final Lock lock = new ReentrantLock();
    private final ArrayList<Condition> waitList = new ArrayList<>();
    private final Queue<Integer> waitListIDs = new LinkedList<>();

    Integer capacity = 3;
    Integer state = 0;

    public void lock() {
        lock.lock();
        try {
            if(capacity-state>0 && waitList.isEmpty()){
                state++;
                return;
            }

            Condition condition = lock.newCondition();
            waitList.add(condition);
            waitListIDs.add(ThreadID.get());

            try{
                if(capacity == state || waitList.get(0) != condition)
                    System.out.println(ThreadID.get() + " is waiting, QUEUE: "+waitListIDs);
                while(capacity == state || waitList.get(0) != condition){
                    condition.await();
                }

                state++;
                waitList.remove(condition);
                waitListIDs.remove(ThreadID.get());

                if(waitList.size() > 0 && capacity-state > 0){
                    waitList.get(0).signal();
                }
            }catch(InterruptedException e){ //first thread
                if(waitList.get(0) == condition && capacity-state > 0){
                    state++;
                    return;
                }
                waitList.remove(condition);  
                waitListIDs.remove(ThreadID.get());
                //notification
                if(waitList.size() > 0){
                    waitList.get(0).signal();
                }
            }

      } finally {
          lock.unlock();
      }
    }

      public void unlock() {
        lock.lock();
        try {
            state--;
            if(!waitList.isEmpty())
                waitList.get(0).signal();
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