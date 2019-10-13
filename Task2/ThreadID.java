public class ThreadID
{
  private static volatile int nextID = 0;

  private static class ThreadLocalID
    extends ThreadLocal<Integer>
  {
    protected synchronized Integer initialValue()
    {
      return nextID++;
    }
  }
  
  private static ThreadLocalID ThreadID = new ThreadLocalID();
  
  public static int get()
  {
    return ThreadID.get();
  }
  
  public static void set(int index)
  {
    ThreadID.set(index);
  }
}