import java.util.Random;

class Executor {
	public static void main (String args[]) {
        Random rand = new Random(); 
        MyThread thread;
        
        for(int i = 0; i < Integer.parseInt(args[0]); i++){
            try	{
                Thread.sleep(rand.nextInt(300));
            }
            catch (InterruptedException exception)	{
                //handleit
            }

            thread = new MyThread(Semaphore.getInstance());
            thread.start();
        }		
	}
}