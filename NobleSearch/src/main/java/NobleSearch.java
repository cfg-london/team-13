// Imports the Google Cloud client library
import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.Document.Type;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.cloud.language.v1.Sentiment;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class NobleSearch {



   public static void main(String... args) throws Exception {
     try {
       BufferedReader reader = new BufferedReader(new FileReader("/Users/pranav/team-13/NobleSearch/target/classes/NobelPrize.json"));
       Gson gson = new Gson();
       Laureates laureates;
       laureates = gson.fromJson(reader, Laureates.class);
       System.out.println(laureates.toString());
       // Instantiates a client
       try (LanguageServiceClient language = LanguageServiceClient.create()) {

         // The text to analyze
         List<Sentiment> sentiments = new ArrayList<>();
         for (Laureate laureate : laureates.laureates) {
           String text = laureate.prizes[0].motivation;
           Document doc = Document.newBuilder()
             .setContent(text).setType(Type.PLAIN_TEXT).build();

           // Detects the sentiment of the text
           Sentiment sentiment = language.analyzeSentiment(doc).getDocumentSentiment();
           System.out.printf("Text: %s%n", text);
           System.out.printf("Sentiment: %s, %s%n", sentiment.getScore(), sentiment.getMagnitude());
           sentiments.add(sentiment);
         }

       } catch (Exception e) {
         System.out.println("ERROR" + e);
       }
     } catch (FileNotFoundException e) {
       System.out.println("File not found execption :" + e);
     }

   }
}
