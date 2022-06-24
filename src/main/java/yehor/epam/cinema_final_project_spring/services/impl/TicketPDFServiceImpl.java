package yehor.epam.cinema_final_project_spring.services.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import yehor.epam.cinema_final_project_spring.dto.FilmDTO;
import yehor.epam.cinema_final_project_spring.dto.SeatDTO;
import yehor.epam.cinema_final_project_spring.dto.SessionDTO;
import yehor.epam.cinema_final_project_spring.dto.TicketDTO;
import yehor.epam.cinema_final_project_spring.exceptions.PDFException;
import yehor.epam.cinema_final_project_spring.services.TicketPDFService;
import yehor.epam.cinema_final_project_spring.utils.constants.Constants;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Locale;

/**
 * Class service for ticket in particular for PDF formation purpose
 */
@Service
@Slf4j
public class TicketPDFServiceImpl implements TicketPDFService {
    private final Font headFont;
    private final MessageSource messageSource;

    /**
     * Create TicketService object and set main Font for pdf file
     */
    @Autowired
    public TicketPDFServiceImpl(MessageSource messageSource) {
        this.messageSource = messageSource;
        headFont = getHeadFont();
    }

    private Font getHeadFont() {
        final Font headFont;
        BaseFont unicode = null;
        try {
            unicode = BaseFont.createFont(Constants.FONTS_BAHNSCHRIFT_TTF_PATH, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        } catch (DocumentException | IOException e) {
            log.error("Couldn't set font for PDF", e);
            throw new PDFException("Couldn't set font for PDF", e);
        }
        headFont = new Font(unicode, 12);
        return headFont;
    }

    /**
     * create ByteArrayOutputStream from ticket object and form it to PDF table
     *
     * @param ticket Ticket object
     * @return ByteArrayOutputStream
     */
    @Override
    public ByteArrayOutputStream formPDFTicketToStream(TicketDTO ticket, Locale locale) {
        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setTotalWidth(PageSize.A4.getWidth() - 25);

            final SessionDTO session = ticket.getSession();
            final FilmDTO film = session.getFilm();
            final SeatDTO seat = ticket.getSeat();

            final String ticketNo = messageSource.getMessage("ticket.pdf.number", null, locale);
            final String filmName = messageSource.getMessage("ticket.pdf.film", null, locale);
            final String date = messageSource.getMessage("ticket.pdf.date", null, locale);
            final String time = messageSource.getMessage("ticket.pdf.time", null, locale);
            final String rowNo = messageSource.getMessage("ticket.pdf.rowNo", null, locale);
            final String placeNo = messageSource.getMessage("ticket.pdf.placeNo", null, locale);
            final String ticketPrice = messageSource.getMessage("ticket.pdf.ticketPrice", null, locale);
            final String ticketPriceInFormat = getTicketPriceInFormat(ticket.getSession().getTicketPrice());
            final String currency = messageSource.getMessage("general.currency.short", null, locale);
            final String postfix = ": ";

            addRowToTable(ticketNo, postfix, table, String.valueOf(ticket.getId()));
            addRowToTable(filmName, postfix, table, film.getName());
            addRowToTable(date, postfix, table, session.getDate().toString());
            addRowToTable(time, postfix, table, session.getTime().toString());
            addRowToTable(rowNo, postfix, table, String.valueOf(seat.getRowNo()));
            addRowToTable(placeNo, postfix, table, String.valueOf(seat.getPlaceNo()));
            addRowToTable(ticketPrice, postfix, table, ticketPriceInFormat + " " + currency);

            PdfWriter.getInstance(document, outputStream);
            document.open();
            document.add(table);

            document.close();
        } catch (Exception e) {
            log.error("Couldn't create pdf ticket", e);
            throw new PDFException();
        }
        return outputStream;
    }

    /**
     * Add appropriate row to PDF table
     *
     * @param string text
     * @param table  the PDF table
     * @param ticket Ticket
     */
    private void addRowToTable(String string, String postfix, PdfPTable table, String ticket) {
        PdfPCell lCell;
        PdfPCell rCell;
        lCell = new PdfPCell(new Phrase(string + postfix, headFont));
        setLeftCell(lCell);
        table.addCell(lCell);

        rCell = new PdfPCell(new Phrase(ticket, headFont));
        setRightCell(rCell);
        table.addCell(rCell);
    }

    private void setLeftCell(PdfPCell lCell) {
        lCell.setHorizontalAlignment(Element.ALIGN_LEFT);
    }

    private void setRightCell(PdfPCell cell) {
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
        cell.setPaddingRight(5);
    }

    /**
     * get ticket price from BigDecimal in two digits after comma format
     *
     * @param ticketPrice ticketPrice
     * @return formatted value in String
     */
    private String getTicketPriceInFormat(BigDecimal ticketPrice) {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(0);
        df.setGroupingUsed(false);
        return df.format(ticketPrice);
    }
}
