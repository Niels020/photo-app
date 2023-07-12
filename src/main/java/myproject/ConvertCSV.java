package myproject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConvertCSV {
    private String path;
    private List<String[]> data;

    public ConvertCSV(String path) {
        this.path = path;
        this.data = new ArrayList<>();
    }

    public void convert() {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] CSVData = line.split(";");
                data.add(CSVData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String[]> getData() {
        return data;
    }

    public void setData(List<String[]> data) {
        this.data = data;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
