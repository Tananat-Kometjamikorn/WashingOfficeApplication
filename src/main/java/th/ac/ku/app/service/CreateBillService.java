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

public class CreateBillService{

    private AccountManager accountManager;
    private OrderInfo selectOrderInfo;
    private Stage stage;
    Color myColor = new DeviceRgb(235, 240, 50);
    Color redColor = new DeviceRgb(255,0,0);
    private final String comic = "font\\COMIC.TTF";


    private int totalCost;

    public void createPdf() throws IOException{
        if (selectOrderInfo.getOrderBill().getCleanStatus().equals("Success")) {
            totalCost = selectOrderInfo.getCloth().getClothQuantity() * 20;
        }
        else{
            totalCost = 0;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Bill");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
        fileChooser.setInitialFileName(Integer.toString(selectOrderInfo.getOrderId()));
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
        if (selectOrderInfo.getOrderBill().getCleanStatus().equals("Success")) {
            document.add(new Paragraph("Thank you for using our services"));
        }
        else {
            document.add(new Paragraph("We sincerely apologize for any damage to your clothes"));
        }
        document.close();
        System.out.println("Bill created");
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
        c1.add(new Paragraph("INVOICE"));
        c1.setFont(comic1);
        c1.setTextAlignment(TextAlignment.CENTER);
        c1.setVerticalAlignment(VerticalAlignment.MIDDLE);
        c1.setMarginTop(30f);
        c1.setMarginBottom(30f);
        c1.setFontSize(28f);
        c1.setBorder(Border.NO_BORDER);
        headTable.addCell(c1);

        Cell c2 = new Cell();
        c2.add(new Paragraph("Washing Office\n555 hhh\nTel. 0894445747"));
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
        PdfFont comic1 = PdfFontFactory.createFont(comic);
        //second content
        float[] colWidthArray2 = {160,90,130,160};
        Table secondTable = new Table(colWidthArray2);
        secondTable.setFont(comic1);

        secondTable.addCell(new Cell(0,4)
                .add(new Paragraph("Order Information"))
                .setBold()
                .setBorder(Border.NO_BORDER));

        Cell c4 = new Cell();
        c4.add(new Paragraph("Order ID"));
        c4.setBorder(Border.NO_BORDER);
        secondTable.addCell(c4);

        Cell c5 = new Cell();
        c5.add(new Paragraph(Integer.toString(selectOrderInfo.getOrderId())));
        c5.setBorder(Border.NO_BORDER);
        secondTable.addCell(c5);

        Cell c6 = new Cell();
        c6.add(new Paragraph("Customer Name"));
        c6.setBorder(Border.NO_BORDER);
        secondTable.addCell(c6);

        Cell c7 = new Cell();
        c7.add(new Paragraph(selectOrderInfo.getCustomerName()));
        c7.setBorder(Border.NO_BORDER);
        secondTable.addCell(c7);

        Cell c8 = new Cell();
        c8.add(new Paragraph("Customer Phone Number"));
        c8.setBorder(Border.NO_BORDER);
        secondTable.addCell(c8);

        Cell c9 = new Cell();
        c9.add(new Paragraph(selectOrderInfo.getCustomerPhone()));
        c9.setBorder(Border.NO_BORDER);
        secondTable.addCell(c9);

        Cell c10 = new Cell();
        c10.add(new Paragraph("Placed Order Date"));
        c10.setBorder(Border.NO_BORDER);
        secondTable.addCell(c10);

        Cell c11 = new Cell();
        c11.add(new Paragraph(selectOrderInfo.getOrderDate()));
        c11.setBorder(Border.NO_BORDER);
        secondTable.addCell(c11);

        Cell c12 = new Cell();
        c12.add(new Paragraph("Clean Status"));
        c12.setBorder(Border.NO_BORDER);
        secondTable.addCell(c12);

        Cell c13 = new Cell();
        c13.add(new Paragraph(selectOrderInfo.getOrderBill().getCleanStatus()));
        c13.setBorder(Border.NO_BORDER);
        secondTable.addCell(c13);

        Cell c14 = new Cell();
        c14.add(new Paragraph("Branch"));
        c14.setBorder(Border.NO_BORDER);
        secondTable.addCell(c14);

        Cell c15 = new Cell();
        c15.add(new Paragraph(selectOrderInfo.getBranchName()));
        c15.setBorder(Border.NO_BORDER);
        secondTable.addCell(c15);

        document.add(secondTable);
    }

    private void addContent3(Document document) throws IOException {
        PdfFont comic1 = PdfFontFactory.createFont(comic);
        float[] colwidth = {140,100,100,100,120};
        Table table = new Table(colwidth);
        table.setFont(comic1);
        Cell c0 = new Cell();
        c0.add(new Paragraph("Services"));
        c0.setBackgroundColor(myColor);
        table.addCell(c0);
        Cell c1 = new Cell();
        c1.add(new Paragraph("Unit"));
        c1.setBackgroundColor(myColor);
        table.addCell(c1);
        Cell c2 = new Cell();
        c2.add(new Paragraph("Cost/Unit"));
        c2.setBackgroundColor(myColor);
        table.addCell(c2);
        Cell c3 = new Cell();
        c3.add(new Paragraph("Cost"));
        c3.setBackgroundColor(myColor);
        table.addCell(c3);
        Cell c11 = new Cell();
        c11.add(new Paragraph("note"));
        c11.setBackgroundColor(myColor);
        table.addCell(c11);

        Cell c4 = new Cell();
        c4.add(new Paragraph("Washing service"));
        table.addCell(c4);
        Cell c5 = new Cell();
        c5.add(new Paragraph(Integer.toString(selectOrderInfo.getCloth().getClothQuantity())));
        table.addCell(c5);
        Cell c6 = new Cell();
        c6.add(new Paragraph("20"));
        table.addCell(c6);
        Cell c7 = new Cell();

        table.addCell(c7);
        Cell c13 = new Cell();
        if (selectOrderInfo.getOrderBill().getCleanStatus().equals("Success")) {
            c7.add(new Paragraph(Integer.toString(totalCost)));
            c13.add(new Paragraph(""));
        }
        else{
            c7.add(new Paragraph(Integer.toString(totalCost)).setFontColor(redColor));
            c13.add((new Paragraph("Cloth damaged")));
        }
        table.addCell(c13);

        Cell c8 = new Cell();
        c8.add(new Paragraph(""));
        c8.setBorder(Border.NO_BORDER);
        c8.setBackgroundColor(myColor);
        table.addCell(c8);
        Cell c9 = new Cell();
        c9.add(new Paragraph(""));
        c9.setBorder(Border.NO_BORDER);
        c9.setBackgroundColor(myColor);
        table.addCell(c9);
        Cell c10 = new Cell();
        c10.add(new Paragraph("Total Cost"));
        c10.setBorder(Border.NO_BORDER);
        c10.setBackgroundColor(myColor);
        table.addCell(c10);
        Cell c12 = new Cell();
        if (selectOrderInfo.getOrderBill().getCleanStatus().equals("Success")) {
            c12.add(new Paragraph("" + totalCost));
        }
        else {
            c12.add(new Paragraph("" + totalCost).setFontColor(redColor));
        }
        c12.setBorder(Border.NO_BORDER);
        c12.setBackgroundColor(myColor);
        table.addCell(c12);
        Cell c14 = new Cell();
        c14.add(new Paragraph(""));
        c14.setBorder(Border.NO_BORDER);
        c14.setBackgroundColor(myColor);
        table.addCell(c14);
        document.add(table);
    }

    public void setAccountManager(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    public void setSelectOrderInfo(OrderInfo selectOrderInfo) {
        this.selectOrderInfo = selectOrderInfo;
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }
}
