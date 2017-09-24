import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.io.IOException;
import java.util.jar.JarEntry;

/**
 * Created by sree on 02/09/17.
 */
public class HttpClient {

    JSONArray photoLinks ;

    public String getPhotoLinks() {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet("https://api.meetup.com/MadrasJUG/photos?photo-host=public&sig_id=94668622&sig=77c19931147032f48c5964b3b6a3a4cc638e4614");
            CloseableHttpResponse response1 = httpclient.execute(httpGet);
            try {
                System.out.println(response1.getStatusLine());
                HttpEntity entity1 = response1.getEntity();
                System.out.println();
                return EntityUtils.toString(entity1);
                //EntityUtils.consume(entity1);
            } finally {
                response1.close();
            }
        } catch (Exception e) {
                return e.getMessage();
        }

    }
    public JSONArray getjsonArray()  {

        JSONParser parser = new JSONParser();
        JSONArray photoLinks = null;
        try {
            photoLinks = (JSONArray) parser.parse(getPhotoLinks());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return photoLinks;
    }

    public String generateEmbdeddedTemplate() {

         //String html="";
         StringBuilder html=new StringBuilder();
         String template = "\n <div class=\"card\">\n" +
                 "          <img height=\"250\" src=\"http://res.cloudinary.com/madrasjug/image/fetch/replace\" alt=\"Card image cap\"></iframe>\n" +
                 "          <p class=\"card-text\"></p>\n" +
                 "          </div>\n";

         JSONArray photoLinks = getjsonArray();
         JSONObject photoMetaData;
         for(int i=0;i<photoLinks.size();i++) {
             photoMetaData = (JSONObject) photoLinks.get(i);
             html.append(template.replace("replace", (CharSequence) photoMetaData.get("highres_link")));
         }
        return html.toString();
    }
}