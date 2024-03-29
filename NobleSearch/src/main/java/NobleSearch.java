// Imports the Google Cloud client library
import com.google.cloud.language.v1.AnalyzeEntitiesRequest;
import com.google.cloud.language.v1.AnalyzeEntitiesResponse;
import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.Document.Type;
import com.google.cloud.language.v1.EncodingType;
import com.google.cloud.language.v1.Entity;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.gson.Gson;
import com.mysql.cj.api.jdbc.Statement;
import com.mysql.cj.jdbc.PreparedStatement;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class NobleSearch {

  private static List<String> ignoreNames = Arrays.asList("recognition", "services", "discovery", "discoveries", "law", "laws", "researches", "studies", "advancement", "service", "analysis", "means", "investigations", "merits");

  private final static int WEIGHTING_MOTIVATION = 3;
  private final static int WEIGHTING_CATEGORY = 4;
  private final static int WEIGHTING_YEAR = 1;
  private final static int WEIGHTING_COUNTRY = 1;
  private final static int WEIGHTING_CITY = 1;
  private final static int WEIGHTING_AFFILIATION_NAME = 1;
  private final static int NUMBER_OF_RECOMMENDATIONS = 3;

   public static void main(String... args) throws Exception {

     String filePath = new File("").getAbsolutePath();
     try {
       //Read JSON data on Nobel prizes, could be improved by getting API data in runtime
       BufferedReader reader = new BufferedReader(new FileReader(filePath + "/NobleSearch/target/classes/NobelPrize.json"));
       Gson gson = new Gson();
       Laureates laureates;
       laureates = gson.fromJson(reader, Laureates.class);
       Map<String, Integer> pages = new HashMap<>();


       try {
         //Read data on page views for each Nobel prize, could be improved by getting API data in runtime
         //by using Google Analytics API
         BufferedReader reader1 = new BufferedReader(new FileReader(filePath + "/NobleSearch/src/main/resources/pageviews.csv"));

         String[] values = reader1.lines().toArray(String[]::new);
         for(String value : values){
           String[] page = value.split(",");
           pages.put(page[0], Integer.parseInt(page[1]));
         }
       } catch (FileNotFoundException e){
         System.out.println(e.toString());
       }
       //System.out.println(Arrays.toString(pages.keySet().toArray()));
       //System.out.println(laureates.toString());
       // Instantiates a client
       List<Prize> prizes = new ArrayList<>();
       try (LanguageServiceClient language = LanguageServiceClient.create()) {

         //Use Google NLP to find correlation between motivation texts
         //Takes ~5min to process all data
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

           //System.out.println(text);
           // Print the response
           List<String> parsedMotivations = new ArrayList<>();
           for (Entity entity : response.getEntitiesList()) {
             if(!ignoreNames.contains(entity.getName())){
               //System.out.printf("Entity: %s\n", entity.getName());
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

       //Score each prize against all other prizes, based on prize details, page views and sentiment analysis
       scorePrizes(pages, prizes);
       System.out.println(Arrays.toString(prizes.toArray()));

       //Push results to database
//       Connection connection = null;
//
//       try {
//         connection = DriverManager
//             .getConnection("jdbc:mysql://54.77.29.175:3306/recommendations","root", "toor");
//
//       } catch (SQLException e) {
//         System.out.println("Connection Failed! Check output console");
//         e.printStackTrace();
//         return;
//       }
//
//       if (connection != null) {
//         final Connection conn = connection;
//         System.out.println("You made it, take control your database now!");
//         prizes.forEach(pFrom -> {
//           pFrom.relativeScore.forEach((pTo, i) -> {
//             System.out.println(pFrom.toURL());
//             System.out.println(pTo.toURL());
//             System.out.println(i);
//
//
//             try {
//               String sql = "INSERT INTO urls (url) VALUES (?)";
//
//               PreparedStatement statement;
//               statement = (PreparedStatement) conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//               statement.setString(1, pFrom.toURL());
//
//               int rowsInserted = statement.executeUpdate();
//               if (rowsInserted > 0) {
//                 System.out.println("A Record inserted successfully!");
//               }
//
//               int from;
//               int to;
//
//               try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
//                 if (generatedKeys.next()) {
//                   from = (int) generatedKeys.getLong(1);
//                 }
//                 else {
//                   throw new SQLException("Creating user failed, no ID obtained.");
//                 }
//               }
//
//               statement = (PreparedStatement) conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//               statement.setString(1, pTo.toURL());
//
//               rowsInserted = statement.executeUpdate();
//               if (rowsInserted > 0) {
//                 System.out.println("A Record inserted successfully!");
//               }
//
//               try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
//                 if (generatedKeys.next()) {
//                   to = (int) generatedKeys.getLong(1);
//                 }
//                 else {
//                   throw new SQLException("Creating user failed, no ID obtained.");
//                 }
//               }
//
//               sql = "INSERT INTO ranks (`from`, `to`, score) VALUES (?, ?, ?)";
//               statement = (PreparedStatement) conn.prepareStatement(sql);
//               statement.setInt(1, from);
//               statement.setInt(2, to);
//               statement.setInt(3, i);
//
//               rowsInserted = statement.executeUpdate();
//               if (rowsInserted > 0) {
//                 System.out.println("A Record inserted successfully!");
//               }
//             } catch (SQLException e) {
//               e.printStackTrace();
//             }
//           });
//         });
//       } else {
//         System.out.println("Failed to make connection!");
//       }
//

       try {
        //Write results to text file to allow reuse of data without processing again
         PrintWriter fileWriter = new PrintWriter(filePath + "/NobleSearch/src/main/resources/output.txt", "UTF-8");
         fileWriter.write(gson.toJson(prizes));
         fileWriter.close();
       }catch (FileNotFoundException e){
         System.out.println(e.toString());
       }

     } catch (FileNotFoundException e) {
       System.out.println("File not found execption :" + e);
     }
   }

  private static void scorePrizes(Map<String, Integer> pages, List<Prize> prizes) {
     //Oh baby what an algorithm
    prizes.stream().forEach(prize -> {
      List<Prize> prizes1 = new ArrayList<>();
      prizes.stream().map(prize1 -> {
        prize1.score = 0;
        for (String string : prize1.motivationParsed) {
          if (prize.motivationParsed.contains(string)) {
            prize1.score+= WEIGHTING_MOTIVATION;
          }
        }
        if (prize.category.equals(prize1.category)) {
          prize1.score+= WEIGHTING_CATEGORY;
        }
        if(prize.year.equals(prize1.year)) {
          prize1.score += WEIGHTING_YEAR;
        }
        //Perform lots of null checks due to holes in data
        if(prize.affiliations != null && prize1.affiliations != null){
          for(int i = 0; i < prize.affiliations.length; i++){
            for(int j = 0; j < prize1.affiliations.length; j++){
              if(prize.affiliations[i].country != null && prize1.affiliations[j].country != null) {
                if (prize.affiliations[i].country.equals(prize1.affiliations[j].country)) {
                  prize1.score += WEIGHTING_COUNTRY;
                }
              }
              if(prize.affiliations[i].city != null && prize1.affiliations[j].city != null) {
                if (prize.affiliations[i].city.equals(prize1.affiliations[j].city)) {
                  prize1.score += WEIGHTING_CITY;
                }
              }
              if(prize.affiliations[i].name != null && prize1.affiliations[j].name != null) {
                if (prize.affiliations[i].name.equals(prize1.affiliations[j].name)) {
                  prize1.score += WEIGHTING_AFFILIATION_NAME;
                }
              }
            }
          }
        }


        prize1.score += ((Double) (Math.log(1 + pages.getOrDefault(prize1.toURL() + "index.html", 0)) * 0.4)).intValue();
        return prize1;
      }).sorted().limit(NUMBER_OF_RECOMMENDATIONS).forEach(p -> prizes1.add(p));
      prize.relativeScore = new HashMap<>();
      for (Prize prize1 : prizes1) {
        if (prize1.equals(prize)) {
          continue;
        }
        prize.relativeScore.putIfAbsent(prize1, prize1.score);
      }
    });
  }
}
