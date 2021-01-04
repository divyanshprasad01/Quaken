package com.example.quaken;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

//Helper methods defined in this class for making an httpRequest for getting data from USGS  API.
public final class getOnlineContent {

//    method which takes url and does all the work and returns the list of earthquakes with required information to show on main ui.
    public static List<newEarthQuake> fetch(String Surl){
        Log.e("TEST","TEST: FETCH METHOD CALLED STARTED");
        List<newEarthQuake> Events;

//      takes a string and calls a method urlCreator defined in this class and checks weather the given string is a valid url and stores it in a URL object if it is valid .
        URL url = urlCreator(Surl);

//      jsonResponse is a String to store the JSONResponse given by the server in string type.
        String jsonResponse = null;

//      performs HttpRequest to get jsonResponse and store it as a String the Method makeHttpRequest is defined in this class.
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e("LOG_TAG","Error in fetch method",e);
        }

//       Calls a method defined in this class  extractFetauresfromJSON which takes in the string response and returns the List of Events of newEarthQuake type objects.
        Events = extractFeaturesfromJSON(jsonResponse);

        return Events;
    }

//   Method takes in a string and check weather if it is a valid url or not and if valid then returns it as a URL Object.
    private static URL urlCreator(String url){
        URL urlObject = null;
        try{
            urlObject = new URL(url);
        }catch (MalformedURLException e){

            Log.e("urlCreator Method","URL OBJECT ERROR!!",e);
        }
        return urlObject;
    }


//    this Method takes in URL and makes a connection recieves a json response from server and returns it.
    private static String makeHttpRequest(URL urls) throws IOException {

        String jsonResponse = null;

        if(urls == null){
            return null;
        }

        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try{
//          Opens a connection between server and the device.
            httpURLConnection = (HttpURLConnection) urls.openConnection();
//          sets response read timeout.
            httpURLConnection.setReadTimeout(10000);
//          Sets connection timeout.
            httpURLConnection.setConnectTimeout(15000);
//          Sets request method.
            httpURLConnection.setRequestMethod("GET");
//          Connects to the server.
            httpURLConnection.connect();

//           Checks weather connection is established or not, if established then starts reading data from an inputstream and stores it in a string jsonResponse.
            if (httpURLConnection.getResponseCode()==200){
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);

            }else{
                Log.e("BAD_REQUEST","RESPONSE ERROR!!!" + httpURLConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e("IN_MAKE_REQUEST","Error caused while opening connection",e);
        }finally {
            if (httpURLConnection!=null){
                httpURLConnection.disconnect();
            }
            if (inputStream!=null){
                inputStream.close();
            }
        }

//        System.out.println(jsonResponse);
        return jsonResponse;
    }

//  A method which takes input stream and reads from stream using a String Builder it is necessary because String type is immutable so we have to use string builder and
//    then cast it in string and then return the string.
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if(inputStream!=null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line!=null){
                output.append(line);
                line = bufferedReader.readLine();
            }
        }

        return output.toString();
    }

//    Extracts required fields from the json response, and returns the list of events.
    private static List<newEarthQuake> extractFeaturesfromJSON(String output){
        if(output.length()==0){
            return null;
        }

       List<newEarthQuake> Events = new ArrayList<>();
        newEarthQuake Event ;

        try {
            JSONObject baseObj = new JSONObject(output);

            JSONArray feturesArray = baseObj.getJSONArray("features");

            for(int i=0; i<feturesArray.length(); i++){

                JSONObject EarthquakeObj = feturesArray.getJSONObject(i);

                JSONObject properties = EarthquakeObj.getJSONObject("properties");

                Double magnitude = properties.getDouble("mag");

                // Extract the value for the key called "place"
                String location = properties.getString("place");

                // Extract the value for the key called "time"
                long time = properties.getLong("time");

                Event = new newEarthQuake(magnitude , location,time);

//                Event.magnitude =  properties.getDouble("mag");
//                Event.time = properties.getLong("time");
//                Event.location = properties.getString("place");
//                System.out.println(Event.magnitude);
//                System.out.println(Event.location);

                Events.add(Event);

            }
        } catch (JSONException e) {
            Log.e("LOG_TAG","Some error occured in extract features from json",e);
        }

//        System.out.println(Events);
        return  Events;
    }

}
