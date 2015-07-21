package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import jdk.nashorn.internal.parser.JSONParser;
import org.json.*;

/**
 * Created by berg on 18/07/15.
 * Class responsible for handling the interaction with the twitch API.
 */
public class TwitchHandler {

    private String url = "";
    private JSONObject streamInformation = null;

    /**
     * Generates a valid JSON Object from the channelName provided
     *
     * @param url name of the twitch channel
     */

    public TwitchHandler(String channelName) {
        this.url = "https://api.twitch.tv/kraken/streams/" + channelName;
        URL urlobj;
        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        StringBuilder result = new StringBuilder();
        try {
            urlobj = new URL(this.url);
            conn = (HttpURLConnection) urlobj.openConnection();
            conn.setRequestMethod("GET");
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
        } catch (IOException e) {
            System.out.println("Stream not found");
            return;
        }
        catch (Exception e) {

        }
        streamInformation = new JSONObject(result.toString());
    }

    /**
     * Checks the online status of the channel
     * @return the online status
     */
    public Boolean isOnline() {
        if (streamInformation == null) {
            return false;
        }
        if (streamInformation.get("stream").equals("null")) {
            return false;
        }
        return true;

    }

    /**
     * Returns the name of the streamer
     * @return the name
     */
    public String getStreamerName() {
        if (streamInformation == null) {
            return "";
        }
        String streamerName = streamInformation.toString().substring(60);
        streamerName = streamerName.substring(0, streamerName.indexOf('"'));
        return streamerName;
    }

    /**
     * Returns the game being played
     * @return game
     */
    public String getGame() {
        if (streamInformation == null) {
            return "";
        }
        JSONObject temp = (JSONObject) streamInformation.get("stream");
        return temp.get("game").toString();

    }

    public String toString() {
        if (streamInformation != null) {
            return streamInformation.toString();
        }
        return "{}";
    }
}
