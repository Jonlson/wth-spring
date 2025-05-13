package wth.nacos.timeTask;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimerWheel {
    List<TimeSlot> slots;

    int currentIndex;
    int slotNum;
    long tick;
    ScheduledExecutorService timer;


    public void addTask(TimerTask task) {

        // 时间精度限制：以满足不会少个圈
        // 计算时间
        long delay = task.delay;
        int round = (int) (delay / tick);
        // 计算槽位索引
        int index = (currentIndex + round) % slotNum;
        TimeSlot timeSlot = slots.get(index);
        timeSlot.tasks.add(task);
    }
    public void start() {
        timer.schedule(() -> {

            TimeSlot slot = slots.get(currentIndex);
            Iterator<TimerTask> it = slot.tasks.iterator();
            while (it.hasNext()) {
                TimerTask task = it.next();
                if (task.round <= 0) {
                    task.task.run();
                    it.remove();
                } else {
                    task.round--;
                }
            }
            currentIndex = (currentIndex + 1) % slotNum;

        }, tick, TimeUnit.SECONDS);
    }
}
