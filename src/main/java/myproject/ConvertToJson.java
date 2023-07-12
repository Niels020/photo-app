package myproject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class ConvertToJson<T> {
    private T object;
    private String path;

    public ConvertToJson(T object,String path, String endPoint) {
        this.object = object;
        this.path = path + endPoint + ".json";
    }

    public void save() {
        createNewFile();

        Gson gson = new GsonBuilder().create();
        String jsonString = gson.toJson(object);

        try (FileWriter fileWriter = new FileWriter(path)) {
            fileWriter.write(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createNewFile() {
        File file = new File(path);
        if (file.exists()) {
            System.out.println("File already exists.");
        } else {
            try {
                boolean created = file.createNewFile();
                if (!created) {
                    System.out.println("Failed to create new file.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
