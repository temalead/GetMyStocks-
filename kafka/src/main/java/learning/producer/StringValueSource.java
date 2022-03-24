package learning.producer;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

public class StringValueSource implements ValueSource {
    private final AtomicLong nextValue=new AtomicLong(1);

    private final Consumer<Value> valueConsumer;

    public StringValueSource(Consumer<Value> valueConsumer) {
        this.valueConsumer = valueConsumer;
    }


    private Value makeValue() {
        long id = nextValue.getAndIncrement();
        return new Value(id,"value: "+id);
    }

    @Override
    public void generate() {
        ScheduledExecutorService executorService= Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(()->valueConsumer.accept(makeValue()),
                0,
                1,
                TimeUnit.SECONDS);
    }
}
