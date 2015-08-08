import javafx.scene.image.Image;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;



/**
 * Created by berg on 18/07/15.
 * Class responsible for handling the interaction with the twitch API.
 */
public class Stream implements Comparable<Stream> {

    private String url = "";
    private JSONObject streamJSON = null;
    protected Boolean onlineStatus = false;
    private Boolean internetConnection = true;
    private Boolean streamValidity = false;
    private String channelName;
    private String streamerName;
    private String game;
    private String streamHeader;
    private String viewers;
    private Image streamLogo = null;
    private Image streamBanner = null;

    /**
     * Generates a valid JSON Object from the channelName provided
     *
     * @param channelName name of the twitch channel
     */

    public Stream(String channelName) {
        this.channelName = channelName;
        this.url = "https://api.twitch.tv/kraken/channels/" + channelName;
        updateStreamStatus();

    }

    /**
     * Updates the status of the current channel.
     */
    public void updateStreamStatus() {
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
            streamJSON = new JSONObject(result.toString());
        }
        catch (UnknownHostException ex) {
            //No internet
            streamJSON = new JSONObject("{\"status\":\"No internet\"}");
            System.out.println("No internet");
        }
        catch (IOException e) {
            //No such streamer
            streamJSON = new JSONObject("{\"status\":\"404\"}");
        }
        if (!streamJSON.get("status").toString().equals("No internet")) {
            if (streamJSON.get("status").toString().equals("404")) {
                streamValidity = false;
                System.out.println("No such stream");
            }
            else {
                streamValidity = true;
                game = streamJSON.get("game").toString();
                streamerName = streamJSON.get("display_name").toString();
                streamHeader = streamJSON.get("status").toString();

                if (isOnline()) {
                    onlineStatus = true;
                }
                else {
                    onlineStatus = false;
                }
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
    private Boolean isOnline() {
        URL urlobj;
        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        StringBuilder result = new StringBuilder();
        try {
            urlobj = new URL("https://api.twitch.tv/kraken/streams/" + channelName);
            conn = (HttpURLConnection) urlobj.openConnection();
            conn.setRequestMethod("GET");
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            onlineStatus = false;
            viewers = "0";
            return onlineStatus;
        }
        JSONObject obj = new JSONObject(result.toString());
        if (obj.get("stream").toString().equals("null")) {
            return false;
        }
        JSONObject temp = new JSONObject(obj.get("stream").toString());
        viewers = temp.get("viewers").toString();
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

    /**
     * Returns if there is a valid internet connection.
     * @return
     */
    public Boolean getInternetConnection(){
        return internetConnection;
    }


    /**
     * Returns the stream header, that is, the title of the stream.
     * @return
     */
    public String getStreamHeader(){
        return this.streamHeader;
    }

    /**
     * Returns whether or not this is a valid stream, that is, the username exists on twitch.
     * @return
     */
    public Boolean getStreamValidity() {
        return streamValidity;
    }

    /**
     * Returns the channel name
     * @return channel name
     */
    public String getChannelName() {
        return channelName;
    }

    public String getViewers() {

        isOnline();
        return viewers;
    }
    /**
     * defines how this object is printed
     */
    public String toString() {
        return channelName;
    }

    /**
     * Compare method, sorts on online status, then on alphabetical order.
     */
    public int compareTo(Stream s1) {
        if (s1.onlineStatus && !this.onlineStatus) {
            return 1;
        }
        else if (!s1.onlineStatus && this.onlineStatus) {
            return -1;
        }
        else {
            return this.streamerName.toLowerCase().compareTo(s1.streamerName.toLowerCase());
        }
    }

    /**
     * Static method that gets the channels that a given user follows.
     * @param userName username
     * @return Given a valid username, returns ArrayList of Strings, containing the channelname of the streamers. Else, returns empty ArrayList.
     */
    public static ArrayList<String> getFollowers(String userName) {
        URL urlobj;
        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        StringBuilder result = new StringBuilder();
        try {
            urlobj = new URL("https://api.twitch.tv/kraken/users/" + userName + "/follows/channels");
            conn = (HttpURLConnection) urlobj.openConnection();
            conn.setRequestMethod("GET");
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
        }
        catch (Exception ex) {
            return new ArrayList<String>();
        }
        JSONObject json = new JSONObject(result.toString());
        JSONArray array = new JSONArray(json.get("follows").toString());

        ArrayList<String> follows = new ArrayList<String>();
        for (int x = 0; x < array.length(); x++) {

            JSONObject temp = new JSONObject(array.get(x).toString());
            temp = (JSONObject) temp.get("channel");
            follows.add(temp.get("name").toString());
        }
        return follows;
    }
    public String getStreamURL() {
        return "https://twitch.tv/" + channelName;
    }
    public Image getStreamLogo() {
        return streamLogo;
    }
    public Image getStreamBanner() {
        return streamBanner;
    }

    public void createImages() {
        if (streamJSON.get("logo").toString() != "null") {
            streamLogo = new Image(streamJSON.get("logo").toString());
        }
        if (streamJSON.get("profile_banner").toString() != "null") {
            streamBanner = new Image(streamJSON.get("profile_banner").toString());
        }
    }
}
