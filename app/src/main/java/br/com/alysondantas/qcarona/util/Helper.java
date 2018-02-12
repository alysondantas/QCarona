package br.com.alysondantas.qcarona.util;

/**
 * Created by marcos on 08/02/2018.
 */

public class Helper {

    public static void pause(int mili){
        try {
            Thread.sleep(mili);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
