package myproject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Class to convert the CSV files into a 2d List
public class CSVToListConverter {
    private String csvFilePath;

    // Set the file path here
    public CSVToListConverter(String csvFilePath) {
        this.csvFilePath = csvFilePath;
    }

    public List<List<String>> convertToList() {
        // Instanciates a 2d List
        List<List<String>> dataList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            // Instanciates a String
            String line;
        
            // Run loop as long as there is a new line
            while ((line = reader.readLine()) != null) {

                // Split on ; and assign to a new List
                String[] data = line.split(";");

                // Instanciates a ArrayList
                List<String> rowData = new ArrayList<>();

                // For Each to add the List to the ArrayList
                for (String value : data) {
                    rowData.add(value);
                }

                // End the While loop with adding the ArrayList to the final dataList
                dataList.add(rowData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataList;
    }
}

