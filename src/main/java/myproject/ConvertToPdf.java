package myproject;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

public class ConvertToPdf {

    private String jsonContent;
    private String path;
    private PDPageContentStream contentStream;
    
    public ConvertToPdf(String path, String endPoint) {
        this.path = path + endPoint;
        this.jsonContent = readJsonFromFile();
    }

    private String readJsonFromFile() {
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(path + ".json"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content.toString();
    }

    public void ConvertPdf() throws IOException {
        // Parse the JSON content
        JsonElement element = JsonParser.parseString(jsonContent);
        JsonObject jsonObject = element.getAsJsonObject();

        // Create a new document
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        // Create a new content stream for writing to the PDF
        contentStream = new PDPageContentStream(document, page);

        // Write the JSON content to the PDF
        contentStream.beginText();
        contentStream.newLineAtOffset(40, 700);

        // Write the order Title
        JsonPrimitive orderId = jsonObject.getAsJsonPrimitive("orderId");
        String id = orderId.getAsString();
        String title = "Invoice of Photoshop order: " + id;
        addTitle(title);

        // Write the customer info
        addTitle("Customer: ");
        JsonArray customerInfo = jsonObject.getAsJsonArray("customerInfo");
        for (JsonElement item : customerInfo) {
            contentStream.showText(item.getAsString());
            contentStream.newLineAtOffset(0, -20);
        }

        // Write the shop assistant
        addTitle("Shop assistant: ");
        JsonArray shopAssistant = jsonObject.getAsJsonArray("shopAssistant");
        for (JsonElement item : shopAssistant) {
            contentStream.showText(item.getAsString());
            contentStream.newLineAtOffset(0, -20);
        }

        // Write the order details
        addTitle("Order details: ");
        JsonObject orderDetails = jsonObject.getAsJsonObject("orderDetails");
        for (String key : orderDetails.keySet()) {
            contentStream.showText(key + " " + orderDetails.get(key).getAsString());
            contentStream.newLineAtOffset(0, -20);
        }

        // Write the order table
        addTitle("Order: ");
        JsonArray order = jsonObject.getAsJsonArray("order");
        for (JsonElement row : order) {
            JsonArray rowData = row.getAsJsonArray();
            for (JsonElement item : rowData) {
                contentStream.showText(item.getAsString());
                contentStream.newLineAtOffset(150, 0);
            }
            contentStream.newLineAtOffset(-600, -20);
        }

        contentStream.endText();
        contentStream.close();

        // Save the document to the specified file path
        document.save(path + ".pdf");
        document.close();
    }

    public void openPdfFile() throws IOException {
        File file = new File(path + ".pdf");
        if (file.exists() && file.isFile()) {
            Desktop.getDesktop().open(file);
        } else {
            throw new FileNotFoundException("File not found: " + path);
        }
    }

    private void addTitle(String title) throws IOException {
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
        contentStream.newLineAtOffset(0, -20);
        contentStream.showText(title);
        contentStream.newLineAtOffset(0, -20);
        contentStream.setFont(PDType1Font.HELVETICA, 12);
    }
}


