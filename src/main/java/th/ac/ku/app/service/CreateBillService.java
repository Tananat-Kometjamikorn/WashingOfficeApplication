package th.ac.ku.app.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import th.ac.ku.app.models.OrderInfo;

import java.io.FileOutputStream;
import java.io.IOException;

public class CreateBillService {

    private AccountManager accountManager;
    private OrderInfo selectOrderInfo;
//    private final int totalCost = selectOrderInfo.getCloth().getClothQuantity() * 20;

    public void createPdf() throws IOException, DocumentException {
        String path = "D:\\"+selectOrderInfo.getOrderId()+".pdf";
        Document document = new Document();
        document.setPageSize(PageSize.A4);
        PdfWriter.getInstance(document, new FileOutputStream(path));
        document.open();
        addContent1(document);
        addContent2(document);
        document.close();
    }

    private void addContent1(Document document) throws DocumentException {
        BaseColor myColor = BaseColor.CYAN;
        //head content
        float col = 280f;
        float[] colWidthArray = {col, col};
        PdfPTable headTable = new PdfPTable(colWidthArray);
        PdfPCell c1 = new PdfPCell(new Phrase("INVOICE"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setVerticalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(myColor);
        c1.setBorder(Rectangle.NO_BORDER);
        headTable.addCell(c1);
        PdfPCell c2 = new PdfPCell(new Phrase("Washing Office\n555 S.dfsd\nTel. 0894445747"));
        c2.setHorizontalAlignment(Element.ALIGN_RIGHT);
        c2.setVerticalAlignment(Element.ALIGN_CENTER);
        c2.setBackgroundColor(myColor);
        c2.setBorder(Rectangle.NO_BORDER);
        headTable.addCell(c2);
        document.add(headTable);
        //second content
        float[] colWidthArray2 = {80,300,100,80};
        PdfPTable secondTable = new PdfPTable(colWidthArray2);
        PdfPCell c3 = new PdfPCell(new Phrase("Order Information"));
        c3.setRowspan(0);
        c3.setColspan(4);
        c3.setBorder(Rectangle.NO_BORDER);
        secondTable.addCell(c3);

        PdfPCell c4 = new PdfPCell(new Phrase("Order ID"));
        c4.setBorder(Rectangle.NO_BORDER);
        secondTable.addCell(c4);
        PdfPCell c5 = new PdfPCell(new Phrase(Integer.toString(selectOrderInfo.getOrderId())));
        c5.setBorder(Rectangle.NO_BORDER);
        secondTable.addCell(c5);

        PdfPCell c6 = new PdfPCell(new Phrase("Customer Name"));
        c6.setBorder(Rectangle.NO_BORDER);
        secondTable.addCell(c6);
        PdfPCell c7 = new PdfPCell(new Phrase(selectOrderInfo.getCustomerName()));
        c7.setBorder(Rectangle.NO_BORDER);
        secondTable.addCell(c7);

        PdfPCell c8 = new PdfPCell(new Phrase("Customer Phone Number"));
        c8.setBorder(Rectangle.NO_BORDER);
        secondTable.addCell(c8);
        PdfPCell c9 = new PdfPCell(new Phrase(selectOrderInfo.getCustomerPhone()));
        c9.setBorder(Rectangle.NO_BORDER);
        secondTable.addCell(c9);

        PdfPCell c10 = new PdfPCell(new Phrase("Place Order Date"));
        c10.setBorder(Rectangle.NO_BORDER);
        secondTable.addCell(c10);
        PdfPCell c11 = new PdfPCell(new Phrase(selectOrderInfo.getOrderDate()));
        c11.setBorder(Rectangle.NO_BORDER);
        secondTable.addCell(c11);

        document.add(secondTable);
    }

    private void addContent2(Document document) throws DocumentException {
        float[] colwidth = {140,140,140,140};
        PdfPTable table = new PdfPTable(colwidth);
        PdfPCell c10 = new PdfPCell(new Phrase("Service"));
        table.addCell(c10);
        PdfPCell c11 = new PdfPCell(new Phrase("Unit"));
        table.addCell(c11);
        PdfPCell c12 = new PdfPCell(new Phrase("Price/Unit"));
        table.addCell(c12);
        PdfPCell c13 = new PdfPCell(new Phrase("Total"));
        table.addCell(c13);

        PdfPCell c14 = new PdfPCell(new Phrase("Washing service"));
        table.addCell(c14);
        PdfPCell c15 = new PdfPCell(new Phrase(Integer.toString(selectOrderInfo.getCloth().getClothQuantity())));
        table.addCell(c15);
        PdfPCell c16 = new PdfPCell(new Phrase("20"));
        table.addCell(c16);
        PdfPCell c17 = new PdfPCell(new Phrase(Integer.toString(0)));
        table.addCell(c17);

        document.add(table);
    }


    public AccountManager getAccountManager() {
        return accountManager;
    }

    public void setAccountManager(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    public OrderInfo getSelectOrderInfo() {
        return selectOrderInfo;
    }

    public void setSelectOrderInfo(OrderInfo selectOrderInfo) {
        this.selectOrderInfo = selectOrderInfo;
    }
}
