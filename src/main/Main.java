package main;

import frontend.Systemtray;

import java.util.ArrayList;

/**
 * Created by berg on 18/07/15.
 */
public class Main {

    ArrayList<Stream> streamlist = new ArrayList();
    Systemtray tray;

    public static void main(String[] args) {

        Main main = new Main();
    }

    public Main() {
        Stream handler = new Stream("esl_csgo");
        Stream handler2 = new Stream("AmazHS");
        Stream handler3 = new Stream("zfg1");

        streamlist.add(handler);
        streamlist.add(handler2);
        streamlist.add(handler3);
        tray = new Systemtray();
        mainLoop();
    }

    private void mainLoop() {
        Thread thread = new Thread();
        System.out.println(streamlist.get(0));
        while(true) {
            for (Stream stream : streamlist) {
                Boolean online = stream.isOnline();
                if (!stream.getLastOnlineStatus() && online) {
                    tray.displayPopup(stream.getStreamerName());
                    stream.setLastOnlineStatus(true);
                }
                else if (stream.getLastOnlineStatus() && !online) {
                    stream.setLastOnlineStatus(false);
                }
                System.out.println("Online: " + stream.isOnline().toString() + "," + " Game: " + stream.getGame() + "," + " Name: " + stream.getStreamerName());
            }
            try {
                thread.sleep(30000);
            }
            catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
