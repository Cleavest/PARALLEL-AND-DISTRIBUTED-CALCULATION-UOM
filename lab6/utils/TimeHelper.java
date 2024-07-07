package utils;

import java.util.concurrent.TimeUnit;

/**
 * @author Cleavest on 13/4/2024
 */
public class TimeHelper {

    private long start;
    private long end;

    public TimeHelper() {
        this.start = - 1;
        this.end = -1;
    }

    public void start() {
        this.start = System.currentTimeMillis();
    }

    public void end() {
        this.end = System.currentTimeMillis();
    }

    public long getTime() {
        if (this.start == -1) {
            System.out.println("dont set start");
            return -1;
        } else if (this.end == -1) {
            System.out.println("dont set end");
            return -1;
        }
        return this.end - this.start;
    }

    public long getSeconds(){
        long millis = getTime();

        return millis == - 1 ? -1 : TimeUnit.MILLISECONDS.toSeconds(getTime());
    }
}
