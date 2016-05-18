package os1;


import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author yaron
 */
class Task implements Runnable {

    String name;

    public Task(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println("start run: " + name);
//        try {
//           Thread.sleep(1000);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(Task.class.getName()).log(Level.SEVERE, null, ex);
//        }
        System.out.println("end run: " + name);
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(Task.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}


public class TestThreadPool {

    static int name = 0;
    public static void main(String[] args) {
        ThreadPool pool = new ThreadPool(5);

        for (int i = 0; i < 10000; i++){
            try {
                pool.execute(new Task("task "+(++name)));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        
    }

}
