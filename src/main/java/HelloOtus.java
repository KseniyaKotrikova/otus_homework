import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

class HelloOtus{
    


                public static void main(String[] args) {

                    Executor executor = Executors.newSingleThreadExecutor();
                    executor.execute(()-> System.out.println("Hello OTUS"));
                    }

        }