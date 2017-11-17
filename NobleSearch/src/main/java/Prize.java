public class Prize {

  Integer year;
  String category;
  Integer share;
  String motivation;
  Affiliation[] affiliations;

  public Prize(Integer year, String category, Integer share, String motivation,
      Affiliation[] affiliations) {
    this.year = year;
    this.category = category;
    this.share = share;
    this.motivation = motivation;
    this.affiliations = affiliations;
  }
}
