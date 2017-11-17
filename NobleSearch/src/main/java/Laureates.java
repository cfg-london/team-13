import java.util.Arrays;

public class Laureates {
  Laureate[] laureates;

  public Laureates(Laureate[] laureates) {
    this.laureates = laureates;
  }

  @Override
  public String toString() {
    return "Laureates{" +
        "laurates=" + Arrays.toString(laureates) +
        '}';
  }
}
