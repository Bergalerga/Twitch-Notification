package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by berg on 18/07/15.
 */
public class Main {

    ArrayList streamlist = new ArrayList<TwitchHandler>();

    public static void main(String[] args) {

        Main main = new Main();
    }

    public Main() {
        TwitchHandler handler = new TwitchHandler("https://api.twitch.tv/kraken/streams/DreamhackCS");
        System.out.println(handler.getStreamJSON());
    }
}
