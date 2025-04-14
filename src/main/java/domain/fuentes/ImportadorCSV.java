package domain.fuentes;

public class ImportadorCSV {
  private String pathCsv;

  public ImportadorCSV(String pathCsv) {
    this.pathCsv = pathCsv;
  }

  public ListHechoDataset importarHechosDataset() {
    //TODO: con OpenCSV
    return new ListHechoDataset();
  }


}
