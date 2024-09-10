import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class EjemploDebounce {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private ScheduledFuture<?> pendingTask;
    private final long debounceDelay = 500; // 500 ms debounce delay

    public static void main(String[] args) {
        EjemploDebounce example = new EjemploDebounce();
        example.runExample();
    }

    private void runExample() {
        List<String> list = new ArrayList<>();
        list.add("Elemento1");
        list.add("Elemento2");

        // Apply debounce on the sort operation
        applyDebounce(() -> {
            Collections.sort(list);
            System.out.println("List sorted: " + list);
        });

        // Simulate multiple triggers
        simulateTrigger();
    }

    private void applyDebounce(Runnable task) {
        if (pendingTask != null) {
            pendingTask.cancel(false); // Cancel the previous task if it is still scheduled
        }
        pendingTask = scheduler.schedule(() -> {
            task.run();
            pendingTask = null; // Reset the pendingTask to null after execution
        }, debounceDelay, TimeUnit.MILLISECONDS);
    }

    private void simulateTrigger() {
        applyDebounce(() -> System.out.println("Task executed after debounce"));
        applyDebounce(() -> System.out.println("Another task executed after debounce"));
        applyDebounce(() -> System.out.println("Yet another task executed after debounce"));
    }
}
