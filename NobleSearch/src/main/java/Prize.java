import java.util.Arrays;

public class Prize {

  String year;
  String category;
  String share;
  String motivation;
  Affiliation[] affiliations;

  public Prize(String year, String category, String share, String motivation,
      Affiliation[] affiliations) {
    this.year = year;
    this.category = category;
    this.share = share;
    this.motivation = motivation;
    this.affiliations = affiliations;
  }

  @Override
  public String toString() {
    return "Prize{" +
        "year=" + year +
        ", category='" + category + '\'' +
        ", share=" + share +
        ", motivation='" + motivation + '\'' +
        ", affiliations=" + Arrays.toString(affiliations) +
        '}';
  }
}
