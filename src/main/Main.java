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
        Stream test = new Stream("chiefmango");
        streamlist.add(test);
        tray = new Systemtray();
        mainLoop();
    }

    private void mainLoop() {
        Thread thread = new Thread();
        while(true) {
            for (Stream stream : streamlist) {

                if (stream.getInternetConnection()) {
                    System.out.println(streamlist.get(0));
                    Boolean online = stream.isOnline();

                    if (!stream.getLastOnlineStatus() && online) {
                        tray.displayPopup(true);
                        System.out.println("Streamer went online");
                        stream.setLastOnlineStatus(true);

                    } else if (stream.getLastOnlineStatus() && !online) {
                        tray.displayPopup(false);
                        System.out.println("Streamer went offline");
                        stream.setLastOnlineStatus(false);
                    }
                }
            }
            System.out.println("Loop completed");
            try {
                thread.sleep(10000);
            }
            catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
