public class Affiliation {

  String name;
  String city;
  String country;

  public Affiliation(String name, String city, String country) {
    this.name = name;
    this.city = city;
    this.country = country;
  }


  @Override
  public String toString() {
    return "Affiliation{" +
        "name='" + name + '\'' +
        ", city='" + city + '\'' +
        ", country='" + country + '\'' +
        '}';
  }
}
