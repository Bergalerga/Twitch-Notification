package main;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

import jdk.nashorn.internal.parser.JSONParser;
import org.json.*;

import javax.imageio.ImageIO;

/**
 * Created by berg on 18/07/15.
 * Class responsible for handling the interaction with the twitch API.
 */
public class Stream {

    private String url = "";
    private JSONObject streamJSON = null;
    private Boolean lastOnlineStatus;
    private Boolean internetConnection = true;
    private Boolean streamValidity;
    private String channelName;
    private String streamerName;
    private String game;
    private String streamHeader;

    /**
     * Generates a valid JSON Object from the channelName provided
     *
     * @param url name of the twitch channel
     */

    public Stream(String channelName) {
        this.channelName = channelName;
        this.url = "https://api.twitch.tv/kraken/channels/" + channelName;
        checkStreamStatus();

    }

    public void checkStreamStatus() {
        URL urlobj;
        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        StringBuilder result = new StringBuilder();
        System.out.println(url);
        try {
            urlobj = new URL(this.url);
            conn = (HttpURLConnection) urlobj.openConnection();
            conn.setRequestMethod("GET");
            System.out.println(conn);
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            System.out.println(rd );
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
            streamJSON = new JSONObject(result.toString());
        }
        catch (UnknownHostException ex) {
            //No internet
            streamJSON = new JSONObject("{\"status\":\"No internet\"}");
        }
        catch (IOException e) {
            //No such streamer
            streamJSON = new JSONObject("{\"status\":\"404\"}");
            e.printStackTrace();
        }
        if (!streamJSON.get("status").toString().equals("No internet")) {
            if (streamJSON.get("status").toString().equals("404")) {
                streamValidity = false;
            }
            else {
                streamValidity = true;
                game = streamJSON.get("game").toString();
                streamerName = streamJSON.get("display_name").toString();
                streamHeader = streamJSON.get("status").toString();
            }
        }
        else {
            internetConnection = false;
        }
    }

    /**
     * Checks the online status of the channel
     * @return the online status
     */
    public Boolean isOnline() {
        checkStreamStatus();
        if (streamJSON.get("status").toString().equals("No internet") {
            return true;
    }

    /**
     * Returns the name of the streamer
     * @return the name
     */
    public String getStreamerName() {
        return streamerName;
    }

    /**
     * Returns the game being played
     * @return game
     */
    public String getGame() {
        return this.game;

    }
    public Boolean getLastOnlineStatus() {
        return lastOnlineStatus;
    }

    public void setLastOnlineStatus(Boolean isOnline) {
        lastOnlineStatus = isOnline;
    }

    public Boolean getInternetConnection(){
        return internetConnection;
    }

    public String getStreamHeader(){
        return this.streamHeader;
    }

    public Boolean getStreamValidity() {
        return streamValidity;
    }
    public String toString() {
        /*
        if (streamInformation != null) {
            if (isOnline()) {
                return getStreamerName() + " - " + getGame() + " - ";
            }
            else {
                return getStreamerName() + " Offline ";
            }
        }
        return "{}";
        */
        return streamJSON.toString();
    }
}
