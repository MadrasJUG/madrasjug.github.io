/**
 * Created by sree on 02/09/17.
 */
public class HtmlEmbeddedImageTagGenerator {
    public static void main(String[] args) {

        HttpClient client = new HttpClient();
        //client.getPhotoLinks();
        System.out.println(client.generateEmbdeddedTemplate());

    }
}
