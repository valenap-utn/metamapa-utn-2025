package utils;

import java.util.ArrayList;
import java.util.List;

public class ReverseList {
  public static <T> List<T> reverse(List<T> lista) {
    List<T> listaAlReves = new ArrayList<>();
    for (int i= lista.size()-1; i>-1; i--) {
      listaAlReves.add(lista.get(i));
    }
    return listaAlReves;
  }
}
