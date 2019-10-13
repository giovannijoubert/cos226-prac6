import java.util.Random;



public class MyThread extends Thread {
    Random rand = new Random(); 
    Semaphore semaphore;

    public MyThread(Semaphore s){
        semaphore = s;
    }

	public void run() {
		
		System.out.println(ThreadID.get() + " wants to enter shop");
		//Lock it
		try {
            System.out.println("Available spaces: " + semaphore.getState());
            semaphore.lock();
            
			System.out.println(ThreadID.get() + " entered shop");

			//Critical Section
			try	{
				Thread.sleep(rand.nextInt(4000));
			}
			catch (InterruptedException exception)	{
				//handleit
			}

		} finally {
			System.out.println(ThreadID.get() + " exists the shop");
			semaphore.unlock();
		}
	}
}