import java.util.Arrays;

public class Laureate {
  String id;
  String surname;
  String born;
  String died;
  String bornCountry;
  String bornCountryCode;
  String bornCity;
  String diedCountry;
  String diedCountryCode;
  String diedCity;
  String gender;
  Prize[] prizes;

  public Laureate(String id, String surname, String born, String died, String bornCountry,
      String bornCountryCode, String bornCity, String diedCountry, String diedCountryCode,
      String diedCity, String gender, Prize[] prizes) {
    this.id = id;
    this.surname = surname;
    this.born = born;
    this.died = died;
    this.bornCountry = bornCountry;
    this.bornCountryCode = bornCountryCode;
    this.bornCity = bornCity;
    this.diedCountry = diedCountry;
    this.diedCountryCode = diedCountryCode;
    this.diedCity = diedCity;
    this.gender = gender;
    this.prizes = prizes;
  }

  @Override
  public String toString() {
    return "Laurate{" +
        "id=" + id +
        ", surname='" + surname + '\'' +
        ", born='" + born + '\'' +
        ", died='" + died + '\'' +
        ", bornCountry='" + bornCountry + '\'' +
        ", bornCountryCode='" + bornCountryCode + '\'' +
        ", bornCity='" + bornCity + '\'' +
        ", diedCountry='" + diedCountry + '\'' +
        ", diedCountryCode='" + diedCountryCode + '\'' +
        ", diedCity='" + diedCity + '\'' +
        ", gender='" + gender + '\'' +
        ", prizes=" + Arrays.toString(prizes) +
        '}';
  }
}
