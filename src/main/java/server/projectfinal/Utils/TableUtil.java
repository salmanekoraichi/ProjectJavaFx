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

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class TableUtil {

    public static TableView<ObservableList<String>> FillTable(ResultSet rs) throws Exception {
        TableView<ObservableList<String>> tableView = new TableView<>();

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

    public static void exportToPDF(TableView<ObservableList<String>> table, String fileName) {
        Document document = new Document(); // Correct instantiation
        try {
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            // Create PDF table with column count matching the TableView
            PdfPTable pdfTable = new PdfPTable(table.getColumns().size());

            // Add column headers
            for (TableColumn<?, ?> column : table.getColumns()) {
                pdfTable.addCell(new PdfPCell(new Phrase(column.getText())));
            }

            // Add rows
            for (ObservableList<String> row : table.getItems()) {
                for (String cell : row) {
                    pdfTable.addCell(new PdfPCell(new Phrase(cell)));
                }
            }

            document.add(pdfTable);
            showSuccess("Exported successfully to " + fileName);
        } catch (Exception e) {
            showError("Error exporting to PDF: " + e.getMessage());
        } finally {
            document.close();
        }
    }

    public static void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public static void showSuccess(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
