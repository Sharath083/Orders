package com.task.orders.service.impl.pdf;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.task.orders.constants.Constants;
import com.task.orders.dto.OrderData;
import com.task.orders.service.impl.OrderDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PdfGenerator {

    public static final String IMAGE_URL = "https://www.gstatic.com/webp/gallery3/1.sm.png";
    @Autowired
    OrderDataService orderDataService;


    public void generatePdf(UUID userId,String userName) throws FileNotFoundException, MalformedURLException {
        PdfWriter writer = new PdfWriter(new File(Constants.PATH, Constants.ORDERS_PDF));
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);

        document.add(new Paragraph(Constants.MAIL_Message.replace("User",userName)));

        float[] pointColumnWidths = {250F, 150F, 150F};
        Table table = new Table(pointColumnWidths);

        table.addCell(new Cell().add(new Paragraph(Constants.ORDER_ID_COLUMN)));
        table.addCell(new Cell().add(new Paragraph(Constants.PRODUCTS_COLUMN)));
        table.addCell(new Cell().add(new Paragraph(Constants.TOTAL_PRICE_COLUMN)));

        var data=orderDataService.getAllOrders(userId);

        data.forEach(
                order -> {
                    table.addCell(order.getOrderId().toString());
                    table.addCell(appender(order.getProducts()));
                    table.addCell(String.valueOf(order.getTotalPrice()));
                }
        );
        document.add(table);
        ImageData imageData = ImageDataFactory.create(IMAGE_URL);
        Image image = new Image(imageData);
        document.add(image);
        document.close();
    }

    private String appender(List<OrderData> data){
        StringBuilder sb = new StringBuilder();
        AtomicInteger i= new AtomicInteger();
        data.forEach(
                order -> {
                    i.getAndIncrement();
                    sb.append(i.get()).append(". ")
                            .append(order.getProductName()).append(" x ")
                            .append(order.getQuantity()).append("\n");
                }
        );
        return sb.toString();
    }
}
