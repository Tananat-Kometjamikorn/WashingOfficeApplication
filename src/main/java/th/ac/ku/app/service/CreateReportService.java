package th.ac.ku.app.service;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

public class CreateReportService {

    private String date;
    private int allOrderQuantity;
    private int allClothQuantity;
    private int allIncome;
    private int allSuccessCleaned;
    private int allClothDamaged;

    private Stage stage;
    Color myColor = new DeviceRgb(235, 240, 50);
    private final String comic = "font\\COMIC.TTF";


    public void createReport(Stage stage) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Report");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
        fileChooser.setInitialFileName("Report_Date_"+date);
        File file = fileChooser.showSaveDialog(stage);
        String path = file.getAbsolutePath();

        PdfWriter pdfWriter = new PdfWriter(path);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        Document document = new Document(pdfDocument);
        pdfDocument.setDefaultPageSize(PageSize.A4);

        addContent1(document);
        document.add(new Paragraph("\n"));
        addContent2(document);
        document.add(new Paragraph("\n"));

        document.close();
        System.out.println("Report created");

    }

    private void addContent1(Document document) throws IOException {
        PdfFont comic1 = PdfFontFactory.createFont(comic);
        //head content
        float col = 300f;
        float[] colWidthArray = {col, col};
        Table headTable = new Table(colWidthArray);
        headTable.setBackgroundColor(myColor);
        headTable.setFont(comic1);

        Cell c1 = new Cell();
        c1.add(new Paragraph("SUMMARY REPORT\nDATE: "+date));
        c1.setFont(comic1);
        c1.setTextAlignment(TextAlignment.LEFT);
        c1.setVerticalAlignment(VerticalAlignment.MIDDLE);
        c1.setMarginTop(30f);
        c1.setMarginBottom(30f);
        c1.setFontSize(28f);
        c1.setBorder(Border.NO_BORDER);
        headTable.addCell(c1);

        Cell c2 = new Cell();
        c2.add(new Paragraph("Print on "+ LocalDate.now()));
        c2.setFont(comic1);
        c2.setTextAlignment(TextAlignment.RIGHT);
        c2.setMarginTop(30f);
        c2.setMarginBottom(30f);
        c2.setMarginRight(10f);
        c2.setBorder(Border.NO_BORDER);
        headTable.addCell(c2);
        document.add(headTable);
    }

    private void addContent2(Document document) throws IOException {

    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAllOrderQuantity() {
        return allOrderQuantity;
    }

    public void setAllOrderQuantity(int allOrderQuantity) {
        this.allOrderQuantity = allOrderQuantity;
    }

    public int getAllClothQuantity() {
        return allClothQuantity;
    }

    public void setAllClothQuantity(int allClothQuantity) {
        this.allClothQuantity = allClothQuantity;
    }

    public int getAllIncome() {
        return allIncome;
    }

    public void setAllIncome(int allIncome) {
        this.allIncome = allIncome;
    }

    public int getAllSuccessCleaned() {
        return allSuccessCleaned;
    }

    public void setAllSuccessCleaned(int allSuccessCleaned) {
        this.allSuccessCleaned = allSuccessCleaned;
    }

    public int getAllClothDamaged() {
        return allClothDamaged;
    }

    public void setAllClothDamaged(int allClothDamaged) {
        this.allClothDamaged = allClothDamaged;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }




}
