package in.royalguru.knowledgeExchange.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kalmeshwar on 21 May 2018 at 17:46.
 */
public class DateTimeHelper {
    private final String TAG = DateTimeHelper.class.getSimpleName();

    private DateTimeHelper() {
    }

    private static DateTimeHelper dateTimeHelper = null;

    public static DateTimeHelper getInstance() {
        return (dateTimeHelper == null) ? dateTimeHelper = new DateTimeHelper() : dateTimeHelper;
    }

//TODO---------------------------------------------Parse String into Date AND date into String-----------------------------------------------------------------------------------------

    public Date parseStringToDate(String mStringDate, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

        try {
            return sdf.parse(mStringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
//TODO---------------------------------------------Give output of date or time you want-----------------------------------------------------------------------------------------

    public String returnFormattedOutput(String mStringDate, String oldFormat, String newFormat) {
//        String mStringDate = "25-Nov-15 14:23:34";
//        String oldFormat= "dd-MMM-yy HH:mm:ss";
//        String newFormat= "dd-MM-yyyy HH:mm";`

        String formatedDate = "";
        SimpleDateFormat sdf = new SimpleDateFormat(oldFormat);
        Date myDate = null;
        try {
            myDate = sdf.parse(mStringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat timeFormat = new SimpleDateFormat(newFormat);
        formatedDate = timeFormat.format(myDate);

        return formatedDate;
    }

    //TODO---------------------------------------------Convert date to server format-----------------------------------------------------------------------------------------
    public String convertToServerFormat2(String inputDate) {
        String outputDateStr = "";

//        DateFormat inputFormat = new SimpleDateFormat(str_inputFormat);
//        DateFormat outputFormat = new SimpleDateFormat(str_outputFormat);

        DateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        try {
            date = inputFormat.parse(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        outputDateStr = outputFormat.format(date);

        return outputDateStr;
    }

    //TODO---------------------------------------------Convert date to Mobile format-----------------------------------------------------------------------------------------

    public String convertToMobileFormat(Date inputDate) {
        String outputDateStr = "";

//        DateFormat inputFormat = new SimpleDateFormat(str_inputFormat);
//        DateFormat outputFormat = new SimpleDateFormat(str_outputFormat);

        DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");

        outputDateStr = outputFormat.format(inputDate);

        return outputDateStr;
    }

    //TODO---------------------------------------------Return current time stamp-----------------------------------------------------------------------------------------
    public String getCurrentDateTime() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
        Date now = new Date();
        return sdfDate.format(now);
    }

    public String getDate() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date now = new Date();
        return sdfDate.format(now);
    }

    public String getBuildTime(Date date) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
        return sdfDate.format(date);
    }

    //TODO---------------------------------------------Update Time to TimePicker Dialog-----------------------------------------------------------------------------------------
    public String updateTime(int hours, int mins) {

        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "pm";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "am";
        } else if (hours == 12)
            timeSet = "pm";
        else
            timeSet = "am";

        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);

        String hourses = "";
        if (hours < 10)
            hourses = "0" + hours;
        else
            hourses = String.valueOf(hours);

        String aTime = new StringBuilder().append(hourses).append(':')
                .append(minutes).append(" ").append(timeSet).toString();
        return aTime;
    }
}
