package java.class_loader;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;

public class Test123 {
    public static void main(String[] args) throws UnsupportedEncodingException, ParseException {
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//        LocalDate dateFrom = LocalDate.parse("2019-10-01", dtf);
//
//        LocalDate dateTo = null;
//
//        if (dateTo == null) {
//            dateTo = LocalDate.now();
//        } else {
//            dateTo = LocalDate.parse("2019-10-31", dtf);
//        }
//
//        for (LocalDate everyDay = dateFrom; everyDay.isBefore(dateTo); everyDay = everyDay.plusDays(1L)) {
//            System.out.println(everyDay);
//        }
//
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date(1569853821000L);
        System.out.println(sdf.format(d));

        System.out.println(sdf.parse("1970-01-19").getTime());


    }
}
