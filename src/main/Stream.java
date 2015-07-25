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
public class Stream {

    private String url = "";
    private JSONObject streamInformation = null;
    private Boolean lastOnlineStatus;
    private Boolean internetConnection = true;

    /**
     * Generates a valid JSON Object from the channelName provided
     *
     * @param url name of the twitch channel
     */

    public Stream(String channelName) {
        this.url = "https://api.twitch.tv/kraken/streams/" + channelName;
        checkStreamStatus();
        if (streamInformation.get("stream").toString() == "null") {
            lastOnlineStatus = false;
        }
        else {
            lastOnlineStatus = true;
        }
    }

    private void checkStreamStatus() {
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
            System.out.println("Offline");
            internetConnection = false;
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
        checkStreamStatus();
        if (streamInformation == null) {
            return false;
        }
        if (streamInformation.get("stream").toString() == "null") {
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
        if (streamInformation.get("stream").toString() != "null") {
            String streamerName = streamInformation.toString().substring(60);
            streamerName = streamerName.substring(0, streamerName.indexOf('"'));
            return streamerName;
        }
        return "";
    }

    /**
     * Returns the game being played
     * @return game
     */
    public String getGame() {
        if (streamInformation == null) {
            return "";
        }
        if (streamInformation.get("stream").toString() != "null") {
            JSONObject temp = (JSONObject) streamInformation.get("stream");
            return temp.get("game").toString();
        }
        else {
            return "";
        }

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

    public String toString() {
        if (streamInformation != null) {
            return streamInformation.toString();
        }
        return "{}";
    }

    public String getStreamHeader(){
        if (streamInformation != null) {
            JSONObject temp = (JSONObject) streamInformation.get("stream");
            JSONObject temp2 = (JSONObject) temp.get("channel");
            return temp2.get("status").toString();
            //return temp2.get("status").toString();

        }
        return "";
    }
}
