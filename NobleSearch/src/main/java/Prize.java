import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Prize implements Comparable {

  String year;
  String category;
  String share;
  String motivation;
  Affiliation[] affiliations;
  List<String> motivationParsed = new ArrayList<>();
  Integer score = 0;

  Map<Prize, Integer> relativeScore;



  public Prize(String year, String category, String share, String motivation,
      Affiliation[] affiliations) {
    this.year = year;
    this.category = category;
    this.share = share;
    this.motivation = motivation;
    this.affiliations = affiliations;
    relativeScore = new HashMap<>();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Prize prize = (Prize) o;

    return motivation.equals(prize.motivation);
  }


  @Override
  public int hashCode() {
    return motivation.hashCode();
  }

  @Override
  public String toString() {
    return "Prize{" +
        "year='" + year + '\'' +
        ", category='" + category + '\'' +
        ", motivation='" + motivation + '\'' +
        ", motivationParsed=" + motivationParsed +
        ", relativeScore=" + printScores() +
        "}\n";
  }

  private String printScores() {
    String returnString = "";
    for (Entry<Prize, Integer> entry : relativeScore.entrySet()) {
      returnString += "Motivation: " + entry.getKey().motivation + " has score:" + entry.getValue() + "\n";
    }
    returnString += "\n";
    return returnString;
  }

  @Override
  public int compareTo(Object o) {
    if (o instanceof Prize) {
      return -this.score.compareTo(((Prize) o).score);
    } else {
      return 0;
    }
  }
}
