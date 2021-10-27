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
import th.ac.ku.app.models.OrderInfo;
import java.io.IOException;

public class CreateBillService{

    private AccountManager accountManager;
    private OrderInfo selectOrderInfo;
    Color myColor = new DeviceRgb(235, 240, 50);
    private final String comic = "font\\COMIC.TTF";


    //private int totalCost = selectOrderInfo.getCloth().getClothQuantity() * 20;

    public void createPdf() throws IOException{
        String path = "pdf_bill\\"+selectOrderInfo.getOrderId()+".pdf";

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
        document.close();
        System.out.println("Pdf created");
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
        c2.add(new Paragraph("Washing Office\n555 S.un1\nTel. 0894445747"));
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

        document.add(secondTable);
    }

    private void addContent3(Document document) throws IOException {
        PdfFont comic1 = PdfFontFactory.createFont(comic);
        float[] colwidth = {140,140,140,140};
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
        c7.add(new Paragraph(Integer.toString(0)));
        table.addCell(c7);

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
        Cell c11 = new Cell();
        c11.add(new Paragraph(""+0));
        c11.setBorder(Border.NO_BORDER);
        c11.setBackgroundColor(myColor);
        table.addCell(c11);

        document.add(table);
    }

    public void setAccountManager(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    public void setSelectOrderInfo(OrderInfo selectOrderInfo) {
        this.selectOrderInfo = selectOrderInfo;
    }
}
