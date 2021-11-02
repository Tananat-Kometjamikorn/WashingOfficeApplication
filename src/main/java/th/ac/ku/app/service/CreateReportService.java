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
import th.ac.ku.app.models.OrderInfo;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CreateReportService {

    private String date;
    private int allOrderQuantity;
    private int allClothQuantity;
    private int allIncome;
    private int allSuccessCleaned;
    private int allClothDamaged;
    private List<OrderInfo> successOrderInfoList;
    private List<OrderInfo> damagedOrderInfoList;

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
        addContent3(document);
        document.add(new Paragraph("\n"));
        addContent4(document);
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
        float[] allOrderCol = {100, 200, 100, 100};
        Table successOrderTable = new Table(allOrderCol);
        successOrderTable.setFont(comic1);

        successOrderTable.addCell(new Cell(0,4)
                .add(new Paragraph("All success order"))
                .setBold()
                .setBorder(Border.NO_BORDER)
                .setTextAlignment(TextAlignment.LEFT)
                .setFontSize(20f));
        document.add(new Paragraph("\n"));

        successOrderTable.addCell(new Cell()
                .add(new Paragraph("Order ID")).setBold().setBorder(Border.NO_BORDER)
                .setBackgroundColor(myColor)
                .setTextAlignment(TextAlignment.CENTER)
        );
        successOrderTable.addCell(new Cell()
                        .add(new Paragraph("Customer name")).setBold().setBorder(Border.NO_BORDER)
                .setBackgroundColor(myColor)
                .setTextAlignment(TextAlignment.CENTER)
        );
        successOrderTable.addCell(new Cell()
                        .add(new Paragraph("Quantity")).setBold().setBorder(Border.NO_BORDER)
                .setBackgroundColor(myColor)
                .setTextAlignment(TextAlignment.CENTER)
        );
        successOrderTable.addCell(new Cell()
                        .add(new Paragraph("Income")).setBold().setBorder(Border.NO_BORDER)
                .setBackgroundColor(myColor)
                .setTextAlignment(TextAlignment.CENTER));

        for(OrderInfo i : successOrderInfoList){
            successOrderTable.addCell(new Cell()
                    .add(new Paragraph(Integer.toString(i.getOrderId())).setTextAlignment(TextAlignment.RIGHT)));
            successOrderTable.addCell(new Cell()
                    .add(new Paragraph(i.getCustomerName()).setTextAlignment(TextAlignment.CENTER)));
            successOrderTable.addCell(new Cell()
                    .add(new Paragraph(Integer.toString(i.getCloth().getClothQuantity())).setTextAlignment(TextAlignment.RIGHT)));
            successOrderTable.addCell(new Cell()
                    .add(new Paragraph(Integer.toString(i.getOrderBill().getCost())).setTextAlignment(TextAlignment.RIGHT)));
        }

        Cell cell1 = new Cell();
        cell1.add(new Paragraph(""));
        cell1.setBorder(Border.NO_BORDER);
        successOrderTable.addCell(cell1);

        Cell cell2 = new Cell();
        cell2.add(new Paragraph(""));
        cell2.setBorder(Border.NO_BORDER);
        successOrderTable.addCell(cell2);

        Cell cell3 = new Cell();
        cell3.add(new Paragraph("Total income"));
        cell3.setBorder(Border.NO_BORDER);
        cell3.setTextAlignment(TextAlignment.RIGHT);
        successOrderTable.addCell(cell3);

        Cell cell4 = new Cell();
        cell4.add(new Paragraph(Integer.toString(allIncome)));
        cell4.setBorder(Border.NO_BORDER);
        cell4.setTextAlignment(TextAlignment.RIGHT);
        successOrderTable.addCell(cell4);

        document.add(successOrderTable);
    }

    private void addContent3(Document document) throws IOException {
        PdfFont comic1 = PdfFontFactory.createFont(comic);
        //second content
        float[] allOrderCol = {100, 200, 100};
        Table damagedOrderTable = new Table(allOrderCol);
        damagedOrderTable.setFont(comic1);

        damagedOrderTable.addCell(new Cell(0,4)
                .add(new Paragraph("All damaged order"))
                .setBold()
                .setBorder(Border.NO_BORDER)
                .setTextAlignment(TextAlignment.LEFT)
                .setFontSize(20f));
        document.add(new Paragraph("\n"));

        damagedOrderTable.addCell(new Cell()
                .add(new Paragraph("Order ID")).setBold().setBorder(Border.NO_BORDER)
                .setBackgroundColor(myColor)
                .setTextAlignment(TextAlignment.CENTER)
        );
        damagedOrderTable.addCell(new Cell()
                .add(new Paragraph("Customer name")).setBold().setBorder(Border.NO_BORDER)
                .setBackgroundColor(myColor)
                .setTextAlignment(TextAlignment.CENTER)
        );
        damagedOrderTable.addCell(new Cell()
                .add(new Paragraph("Quantity")).setBold().setBorder(Border.NO_BORDER)
                .setBackgroundColor(myColor)
                .setTextAlignment(TextAlignment.CENTER)
        );

        for(OrderInfo i : damagedOrderInfoList){
            damagedOrderTable.addCell(new Cell()
                    .add(new Paragraph(Integer.toString(i.getOrderId())).setTextAlignment(TextAlignment.RIGHT)));
            damagedOrderTable.addCell(new Cell()
                    .add(new Paragraph(i.getCustomerName()).setTextAlignment(TextAlignment.CENTER)));
            damagedOrderTable.addCell(new Cell()
                    .add(new Paragraph(Integer.toString(i.getCloth().getClothQuantity())).setTextAlignment(TextAlignment.RIGHT)));
        }

        document.add(damagedOrderTable);
    }

    private void addContent4(Document document) throws IOException {
        PdfFont comic1 = PdfFontFactory.createFont(comic);
        //third content
        float[] colWidthArray2 = {170,120,140,140};
        Table summaryTable = new Table(colWidthArray2);
        summaryTable.setFont(comic1);

        summaryTable.addCell(new Cell(0,4)
                .add(new Paragraph("Order Summary"))
                .setBold()
                .setBorder(Border.NO_BORDER)
                .setFontSize(20f));

        Cell c4 = new Cell();
        c4.add(new Paragraph("Total number of orders"));
        c4.setBorder(Border.NO_BORDER);
        c4.setTextAlignment(TextAlignment.CENTER);
        c4.setBackgroundColor(myColor);
        summaryTable.addCell(c4);

        Cell c6 = new Cell();
        c6.add(new Paragraph("All cloth quantity"));
        c6.setBorder(Border.NO_BORDER);
        c6.setTextAlignment(TextAlignment.CENTER);
        c6.setBackgroundColor(myColor);
        summaryTable.addCell(c6);

        Cell c8 = new Cell();
        c8.add(new Paragraph("All success order"));
        c8.setBorder(Border.NO_BORDER);
        c8.setTextAlignment(TextAlignment.CENTER);
        c8.setBackgroundColor(myColor);
        summaryTable.addCell(c8);

        Cell c10 = new Cell();
        c10.add(new Paragraph("All damaged order"));
        c10.setBorder(Border.NO_BORDER);
        c10.setTextAlignment(TextAlignment.CENTER);
        c10.setBackgroundColor(myColor);
        summaryTable.addCell(c10);

        Cell c5 = new Cell();
        c5.add(new Paragraph(Integer.toString(allOrderQuantity)));
        c5.setTextAlignment(TextAlignment.RIGHT);
        summaryTable.addCell(c5);

        Cell c7 = new Cell();
        c7.add(new Paragraph(Integer.toString(allClothQuantity)));
        c7.setTextAlignment(TextAlignment.RIGHT);
        summaryTable.addCell(c7);

        Cell c9 = new Cell();
        c9.add(new Paragraph(Integer.toString(allSuccessCleaned)));
        c9.setTextAlignment(TextAlignment.RIGHT);
        summaryTable.addCell(c9);

        Cell c11 = new Cell();
        c11.add(new Paragraph(Integer.toString(allClothDamaged)));
        c11.setTextAlignment(TextAlignment.RIGHT);
        summaryTable.addCell(c11);

        document.add(summaryTable);
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

    public void setSuccessOrderInfoList(List<OrderInfo> successOrderInfoList) {
        this.successOrderInfoList = successOrderInfoList;
    }

    public void setDamagedOrderInfoList(List<OrderInfo> damagedOrderInfoList) {
        this.damagedOrderInfoList = damagedOrderInfoList;
    }
}
