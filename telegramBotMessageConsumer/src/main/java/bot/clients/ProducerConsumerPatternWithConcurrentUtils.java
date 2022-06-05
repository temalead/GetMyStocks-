package bot.clients;

import bot.model.Order;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerConsumerPatternWithConcurrentUtils {
    public static void main(String[] args) {
        BlockingQueue<Order> queue = new ArrayBlockingQueue<>(10);

        /*final Runnable producer = () -> {
            while (true) {
                try {
                    queue.put(createOrder());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        new Thread(producer).start();
        new Thread(producer).start();


        final Runnable consumer = ()->{
            while (true){
                try {
                    Order order=queue.take();
                    process(order);


                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        };*/

       /* new Thread(consumer).start();
        new Thread(consumer).start();

        Thread.sleep(1000);*/
    }


    private static Order createOrder() {
        return Order.builder().build();
    }

    private static void process(Order order){
        System.out.println(order);
    }
}
