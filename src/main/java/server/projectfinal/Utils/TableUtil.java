package server.projectfinal.Utils;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import com.itextpdf.text.Document;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static server.projectfinal.Utils.PopupNotification.showError;
import static server.projectfinal.Utils.PopupNotification.showSuccess;




public class TableUtil {

    public static TableView<ObservableList<String>> FillTable(ResultSet rs) throws Exception {
        TableView<ObservableList<String>> tableView = new TableView<>();

        // Set constrained resize policy
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Retrieve metadata from ResultSet
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        // Dynamically create columns
        for (int i = 1; i <= columnCount; i++) {
            final int colIndex = i - 1; // Zero-based index for ObservableList
            String columnName = metaData.getColumnName(i);

            TableColumn<ObservableList<String>, String> column = new TableColumn<>(columnName);
            column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(colIndex)));
            tableView.getColumns().add(column);
        }

        // Add rows from ResultSet to ObservableList
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
        while (rs.next()) {
            ObservableList<String> row = FXCollections.observableArrayList();
            for (int i = 1; i <= columnCount; i++) {
                row.add(rs.getString(i)); // Add each column value
            }
            data.add(row);
        }
        tableView.setItems(data);
        return tableView;
    }

    public static void exportToCSV(TableView<ObservableList<String>> table, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            // Write column headers
            for (TableColumn<?, ?> column : table.getColumns()) {
                writer.write(column.getText() + ",");
            }
            writer.write("\n");

            // Write row data
            for (ObservableList<String> row : table.getItems()) {
                for (String cell : row) {
                    writer.write(cell + ",");
                }
                writer.write("\n");
            }

            showSuccess("Exported successfully to " + fileName);
        } catch (IOException e) {
            showError("Error exporting to CSV: " + e.getMessage());
        }
    }


    public static void exportToPDF(TableView<ObservableList<String>> table, String baseFileName) {
        if (table.getColumns().isEmpty() || table.getItems().isEmpty()) {
            showError("Table is empty. Cannot generate PDF.");
            return;
        }

        // Generate dynamic file name with current time (HH-mm)
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH-mm"));
        String fileName = baseFileName + "_" + timestamp + ".pdf";

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            // Add ENSA logo
            try {
                Image logo = Image.getInstance("/server/projectfinal/design/images/ensalogo.png"); // Adjust the path
                logo.scaleToFit(100, 100); // Resize the logo
                logo.setAlignment(Image.ALIGN_LEFT);
                document.add(logo);
            } catch (Exception e) {
                System.err.println("Error adding logo: " + e.getMessage());
            }

            // Add a title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK);
            Paragraph title = new Paragraph("Table Data Export", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            // Create the table
            PdfPTable pdfTable = new PdfPTable(table.getColumns().size());
            pdfTable.setWidthPercentage(100);
            pdfTable.setSpacingBefore(10f);

            // Add column headers
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);
            BaseColor headerBackgroundColor = new BaseColor(0, 121, 182); // Blue
            for (TableColumn<?, ?> column : table.getColumns()) {
                PdfPCell headerCell = new PdfPCell(new Phrase(column.getText(), headerFont));
                headerCell.setBackgroundColor(headerBackgroundColor);
                headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                headerCell.setPadding(8);
                pdfTable.addCell(headerCell);
            }

            // Add row data
            Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);
            BaseColor evenRowColor = new BaseColor(240, 240, 240); // Light gray
            BaseColor oddRowColor = BaseColor.WHITE;
            int rowIndex = 0;

            for (ObservableList<String> row : table.getItems()) {
                BaseColor rowColor = (rowIndex % 2 == 0) ? evenRowColor : oddRowColor;
                for (String cell : row) {
                    PdfPCell dataCell = new PdfPCell(new Phrase(cell, cellFont));
                    dataCell.setBackgroundColor(rowColor);
                    dataCell.setPadding(5);
                    pdfTable.addCell(dataCell);
                }
                rowIndex++;
            }

            // Add the table to the document
            document.add(pdfTable);

            // Add Footer
            Font footerFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.GRAY);
            Paragraph footer = new Paragraph("Generated by ENSET System\n" +
                    "Date: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")), footerFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setSpacingBefore(20);
            document.add(footer);

            showSuccess("PDF generated successfully: " + fileName);
        } catch (Exception e) {
            showError("Error exporting to PDF: " + e.getMessage());
        } finally {
            document.close();
        }
    }


}
