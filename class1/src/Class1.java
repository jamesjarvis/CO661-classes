public class Class1 {
    public static void main(String[] args) {
        System.out.println("Hello");

        Thread t = new Thread(new Worker());
        t.start();
    }

    static class Worker implements Runnable {
        public void run() {
            try {
                Thread.sleep(1000);
                System.out.println("World!");
            }
            catch(java.lang.InterruptedException e) {
                System.out.println(e);
            }
        }
    }
}