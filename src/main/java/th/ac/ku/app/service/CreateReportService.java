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
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
    LocalDate date1 = LocalDate.now();


    public void createReport(Stage stage) throws IOException {
        String[] splitDate = date.split("/");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Report");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
        fileChooser.setInitialFileName("Report-Date-"+splitDate[0]+"-"+splitDate[1]+"-"+splitDate[2]);
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
        c1.setFontSize(20f);
        c1.setBorder(Border.NO_BORDER);
        headTable.addCell(c1);

        Cell c2 = new Cell();
        c2.add(new Paragraph("Print on "+formatter.format(date1)));
        c2.setFont(comic1);
        c2.setTextAlignment(TextAlignment.RIGHT);
        c2.setVerticalAlignment(VerticalAlignment.MIDDLE);
        c2.setMarginTop(30f);
        c2.setMarginBottom(30f);
        c2.setMarginRight(10f);
        c2.setFontSize(20f);
        c2.setBorder(Border.NO_BORDER);
        headTable.addCell(c2);
        document.add(headTable);
    }

    private void addContent2(Document document) throws IOException {
        PdfFont comic1 = PdfFontFactory.createFont(comic);
        //second content
        float[] colWidthArray2 = {180,140};
        Table secondTable = new Table(colWidthArray2);
        secondTable.setFont(comic1);

        secondTable.addCell(new Cell(0,4)
                .add(new Paragraph("Order Summary"))
                .setBold()
                .setBorder(Border.NO_BORDER));

        Cell c4 = new Cell();
        c4.add(new Paragraph("Total number of orders"));
        c4.setBorder(Border.NO_BORDER);
        secondTable.addCell(c4);

        Cell c5 = new Cell();
        c5.add(new Paragraph(Integer.toString(allOrderQuantity)));
        c5.setBorder(Border.NO_BORDER);
        secondTable.addCell(c5);

        Cell c6 = new Cell();
        c6.add(new Paragraph("All cloth quantity"));
        c6.setBorder(Border.NO_BORDER);
        secondTable.addCell(c6);

        Cell c7 = new Cell();
        c7.add(new Paragraph(Integer.toString(allClothQuantity)));
        c7.setBorder(Border.NO_BORDER);
        secondTable.addCell(c7);

        Cell c8 = new Cell();
        c8.add(new Paragraph("All success order"));
        c8.setBorder(Border.NO_BORDER);
        secondTable.addCell(c8);

        Cell c9 = new Cell();
        c9.add(new Paragraph(Integer.toString(allSuccessCleaned)));
        c9.setBorder(Border.NO_BORDER);
        secondTable.addCell(c9);

        Cell c10 = new Cell();
        c10.add(new Paragraph("All damaged order"));
        c10.setBorder(Border.NO_BORDER);
        secondTable.addCell(c10);

        Cell c11 = new Cell();
        c11.add(new Paragraph(Integer.toString(allClothDamaged)));
        c11.setBorder(Border.NO_BORDER);
        secondTable.addCell(c11);

        Cell c12 = new Cell();
        c12.add(new Paragraph("Total income"));
        c12.setBorder(Border.NO_BORDER);
        secondTable.addCell(c12);

        Cell c13 = new Cell();
        c13.add(new Paragraph(Integer.toString(allIncome)));
        c13.setBorder(Border.NO_BORDER);
        secondTable.addCell(c13);

        document.add(secondTable);
    }


    public void setDate(String date) {
        this.date = date;
    }

    public void setAllOrderQuantity(int allOrderQuantity) {
        this.allOrderQuantity = allOrderQuantity;
    }

    public void setAllClothQuantity(int allClothQuantity) {
        this.allClothQuantity = allClothQuantity;
    }

    public void setAllIncome(int allIncome) {
        this.allIncome = allIncome;
    }

    public void setAllSuccessCleaned(int allSuccessCleaned) {
        this.allSuccessCleaned = allSuccessCleaned;
    }

    public void setAllClothDamaged(int allClothDamaged) {
        this.allClothDamaged = allClothDamaged;
    }

}
