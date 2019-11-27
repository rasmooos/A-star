package aStar;

import engine.core.Main;

public class Start {

    public static void main(String[] args) {
        Main.createRenderer(new AStar(), AStar.WIDTH + 17, AStar.HEIGHT + 40, "A-Star");
    }

}
