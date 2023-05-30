package myproject;

import java.util.List;

public class Photos {
    // convert pricelist.csv to a 2d array
    String csvFilePath = "./photoshop/Data/PhotoShop_OpeningHours.csv";
    CSVToListConverter converter = new CSVToListConverter(csvFilePath);
    List<List<String>> dataList = converter.convertToList();


    public void getPhotos() {
        for (List<String> row : dataList) {
            for (String value : row) {
                System.out.print(value + " ");
            }
        System.out.println();
        }    
    }
}
