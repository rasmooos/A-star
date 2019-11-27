package engine.util;

public class Time {

    private static int timer;

    public static void update() {
        timer++;

        if(timer >= Integer.MAX_VALUE) timer = 0;
    }

    public static int getTime() {
        return timer;
    }

    public static boolean second() {
        return timer % 60 == 0;
    }

    public static boolean passedTime(float duration) {
        return timer % duration == 0;
    }
}
