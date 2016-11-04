package vn.hackathon.likeme.until;

import java.text.SimpleDateFormat;
import java.util.Date;

import static vn.hackathon.likeme.constant.SystemConstant.DATE_FORMAT;

/**
 * Created by linhnd on 2016/11/03.
 */
public class DateUntil {
    public static String getDateByPattern(Date date) {

        String output;
        SimpleDateFormat formatter;

        formatter = new SimpleDateFormat(DATE_FORMAT);
        System.out.println("output" + formatter.format(date));
        return formatter.format(date);
    }

    /*public static void main(String[] args) {
        Date date = new Date();
        getDateFromSystem(date);
    }*/
}
