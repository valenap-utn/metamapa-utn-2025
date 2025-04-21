package ar.edu.utn.frba.dds.utils;

import java.util.Collection;
import java.util.function.Function;

public class FoldlDeFunciones {
  public static <T> T foldl(T base, Collection<Function<T, T>> listaDeFunciones) {
    T auxiliar = base;
    for(Function<T, T> funcion : listaDeFunciones) {
      auxiliar = funcion.apply(auxiliar);
    }
    return auxiliar;
  }
}
