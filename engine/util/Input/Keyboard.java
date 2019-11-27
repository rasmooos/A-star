package engine.util.Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

    private static boolean[] keys = new boolean[128];
    private static boolean[] pressed = new boolean[128];

    public static boolean up_all, down_all, left_all, right_all, move_any;
    public static boolean up, down, left, right, w, a, s, d;
    public static boolean zero, one, two, three, four, five, six, seven, eight, nine;
    public static boolean q, e, space, enter, tab, shift, backspace, esc;

    public static boolean esc_once, enter_once;


    public static void update() {
        move_any = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W] || keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S] || keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A] || keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];

        up_all = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
        down_all = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
        left_all = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
        right_all = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];

        up = keys[KeyEvent.VK_UP];
        down = keys[KeyEvent.VK_DOWN];
        left = keys[KeyEvent.VK_LEFT];
        right = keys[KeyEvent.VK_RIGHT];

        w = keys[KeyEvent.VK_W];
        s = keys[KeyEvent.VK_S];
        a = keys[KeyEvent.VK_A];
        d = keys[KeyEvent.VK_D];

        zero = keys[KeyEvent.VK_0];
        one = keys[KeyEvent.VK_1];
        two = keys[KeyEvent.VK_2];
        three = keys[KeyEvent.VK_3];
        four = keys[KeyEvent.VK_4];
        five = keys[KeyEvent.VK_5];
        six = keys[KeyEvent.VK_6];
        seven = keys[KeyEvent.VK_7];
        eight = keys[KeyEvent.VK_8];
        nine = keys[KeyEvent.VK_9];

        q = keys[KeyEvent.VK_Q];
        e  = keys[KeyEvent.VK_E];
        space = keys[KeyEvent.VK_SPACE];
        enter = keys[KeyEvent.VK_ENTER];
        tab = keys[KeyEvent.VK_TAB];
        shift = keys[KeyEvent.VK_SHIFT];
        backspace = keys[KeyEvent.VK_BACK_SPACE];
        esc = keys[KeyEvent.VK_ESCAPE];

        esc_once = esc && !pressed[KeyEvent.VK_ESCAPE];
        enter_once = enter && !pressed[KeyEvent.VK_ENTER];


        if(esc && esc_once) pressed[KeyEvent.VK_ESCAPE] = true;
        if(enter && enter_once) pressed[KeyEvent.VK_ENTER] = true;
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
        pressed[e.getKeyCode()] = false;
    }

    private static int i;
}
