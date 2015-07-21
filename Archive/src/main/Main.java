package main;

import java.util.ArrayList;

/**
 * Created by berg on 18/07/15.
 */
public class Main {

    ArrayList streamlist = new ArrayList<Stream>();

    public static void main(String[] args) {

        Main main = new Main();
    }

    public Main() {
        Stream handler = new Stream("https://api.twitch.tv/kraken/streams/DreamhackCS");
        System.out.println(handler.getStreamJSON());
    }
}
