package org.example;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;

import java.io.IOException;

public  class Utils {
    static String userName = "mohammadk";
    static String accessKey = "rakcBoBYHiy8BW7osVi4N1LGYjgJhRfAwvL1pPUvwCA1wfNChd";
    static String credential = Credentials.basic(userName, accessKey);
    public static String getVideoUrlFromLT(String sessionId) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        //RequestBody body = RequestBody.create(mediaType, (String) null);
        Request request = new Request.Builder()
                .url("https://api.lambdatest.com/automation/api/v1/sessions/"+sessionId+"/video")
                .method("GET", null)
                .addHeader("accept", "application/json")
                .addHeader("Authorization", credential)
                .build();
        Response response = client.newCall(request).execute();

        return extractVideoUrl(response.body().string());
    }

    private static String extractVideoUrl(String responseJson){
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(responseJson).getAsJsonObject();

        String viewVideoUrl = jsonObject.get("view_video_url").getAsString();
        System.out.println("view_video_url: " + viewVideoUrl);
        return viewVideoUrl;
    }
}
