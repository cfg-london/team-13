// Imports the Google Cloud client library
import com.google.cloud.language.v1.AnalyzeEntitiesRequest;
import com.google.cloud.language.v1.AnalyzeEntitiesResponse;
import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.Document.Type;
import com.google.cloud.language.v1.EncodingType;
import com.google.cloud.language.v1.Entity;
import com.google.cloud.language.v1.EntityMention;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.cloud.language.v1.Sentiment;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class NobleSearch {

  private static List<String> ignoreNames = Arrays.asList("recognition", "services", "discovery", "discoveries", "law", "laws", "researches", "studies", "advancement", "service", "analysis", "means", "investigations", "merits");


   public static void main(String... args) throws Exception {
     try {
       BufferedReader reader = new BufferedReader(new FileReader("/Users/pranav/team-13/NobleSearch/target/classes/NobelPrize.json"));
       Gson gson = new Gson();
       Laureates laureates;
       laureates = gson.fromJson(reader, Laureates.class);
       //System.out.println(laureates.toString());
       // Instantiates a client
       List<Prize> prizes = new ArrayList<>();
       try (LanguageServiceClient language = LanguageServiceClient.create()) {


         for (Laureate laureate : laureates.laureates) {
           String text = laureate.prizes[0].motivation;
           if (laureate.prizes[0].motivation == null) {
             continue;
           }
           // Detects the sentiment of the text
           Document doc = Document.newBuilder()
               .setContent(text)
               .setType(Type.PLAIN_TEXT)
               .build();
           AnalyzeEntitiesRequest request = AnalyzeEntitiesRequest.newBuilder()
               .setDocument(doc)
               .setEncodingType(EncodingType.UTF16)
               .build();

           AnalyzeEntitiesResponse response = language.analyzeEntities(request);

           System.out.println(text);
           // Print the response
           List<String> parsedMotivations = new ArrayList<>();
           for (Entity entity : response.getEntitiesList()) {
             if(!ignoreNames.contains(entity.getName())){
               System.out.printf("Entity: %s\n", entity.getName());
               parsedMotivations.add(entity.getName());
             }
           }
           laureate.prizes[0].motivationParsed = parsedMotivations;
           if (!(Objects.equals(laureate.prizes[0].share, "1"))) {
             if (prizes.contains(laureate.prizes[0])) {

             } else {
               prizes.add(laureate.prizes[0]);
             }
           } else {
             prizes.add(laureate.prizes[0]);
           }
         }
       }
       System.out.println("Parsed all prizes");
       prizes.stream().forEach(prize -> {
         List<Prize> prizes1 = new ArrayList<>();
         prizes.stream().map(prize1 -> {
           prize1.score = 0;
           for (String string : prize1.motivationParsed) {
             if (prize.motivationParsed.contains(string)) {
               prize1.score++;
             }
           }
           return prize1;
         }).sorted().limit(3).forEach(p -> prizes1.add(p));
         prize.relativeScore = new HashMap<>();
         for (Prize prize1 : prizes1) {
           if (prize1.equals(prize)) {
             continue;
           }
           prize.relativeScore.putIfAbsent(prize1, prize1.score);
         }
       });
       System.out.println(Arrays.toString(prizes.toArray()));

       try {

         PrintWriter fileWriter = new PrintWriter("/Users/pranav/Desktop/output.txt", "UTF-8");
         fileWriter.write(gson.toJson(prizes));
         fileWriter.close();
       }catch (FileNotFoundException e){
         System.out.println(e.toString());
       }
     } catch (FileNotFoundException e) {
       System.out.println("File not found execption :" + e);
     }

   }
}
