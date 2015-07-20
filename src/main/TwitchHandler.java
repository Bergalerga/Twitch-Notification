package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import jdk.nashorn.internal.parser.JSONParser;
import org.json.*;

/**
 * Created by berg on 18/07/15.
 */
public class TwitchHandler {

    private String url = "";
    private String streamInformation = "";

    public TwitchHandler(String url) {

        this.url = url;
    }

    public JSONObject getStreamJSON() {

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
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject streamInformation = new JSONObject(result.toString());
        this.streamInformation = streamInformation
        return streamInformation;
    }

    public String getStreamerName() {

    }
}
