package br.com.wildyoshi.exerciciosspringboot.models.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DateUtils {
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String DATE_PATTERN_FULL = "yyyy-MM-dd HH:mm:ss";

    private DateUtils() {
    }

    public static Date fixDateFinalFilterForRegistrationDate(Date dateFinal) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFinal);
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }

    public static Date stringToDate(String strDate) {
        return stringToDate(strDate, DATE_PATTERN);
    }

    public static Date getDateMinus3Hours() {
        return Date.from(LocalDateTime.now().minusHours(3).atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date stringToDate(String strDate, String pattern) {
        Date date = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            date = simpleDateFormat.parse(strDate);
        } catch (ParseException pe) {
            date = new Date();
        }
        return date;
    }

    public static String monthName(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("MMMMM", new Locale("pt", "BR"));
        return dateFormat.format(date);
    }

    public static Date getDateMidnight(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    public static Date getDateLastTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);

        return calendar.getTime();
    }

    public static Date getTodayMidnight() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getDuplicateRechargeTimeLimit() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -1);
        calendar.add(Calendar.SECOND, -10);
        return calendar.getTime();
    }

    public static Date getDateLimitForSimulatePJLoan(Integer daysBeforeTimeLimit) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getTodayMidnight());
        cal.add(Calendar.DAY_OF_MONTH, daysBeforeTimeLimit);
        return cal.getTime();
    }

    public static Date getFirstDueDate() {
        Calendar calendar = Calendar.getInstance();
        Date dueDate = null;

        if (calendar.get(Calendar.DATE) <= 19) {
            calendar.set(Calendar.DATE, 27);

            if (calendar.get(Calendar.DAY_OF_WEEK) == 1) {
                calendar.add(Calendar.DATE, 1);
            }
            if (calendar.get(Calendar.DAY_OF_WEEK) == 7) {
                calendar.add(Calendar.DATE, 2);
            }

            dueDate = calendar.getTime();// vencimento sera sempre dia 25 (mes corrente)
        } else {
            // solicitacoes da antecipacao depois do dia 25 irão vencer no dia 25 do proximo
            // mes
            calendar.add(Calendar.MONTH, 1);
            calendar.set(Calendar.DATE, 25);

            if (calendar.get(Calendar.DAY_OF_WEEK) == 1) {
                calendar.add(Calendar.DATE, 1);
            }
            if (calendar.get(Calendar.DAY_OF_WEEK) == 7) {
                calendar.add(Calendar.DATE, 2);
            }

            dueDate = calendar.getTime();// vencimento sera sempre dia 25
        }
        return dueDate;
    }

    public static Date getReleaseDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);

        if (calendar.get(Calendar.DAY_OF_WEEK) == 1) {
            calendar.add(Calendar.DATE, 1);
        }
        if (calendar.get(Calendar.DAY_OF_WEEK) == 7) {
            calendar.add(Calendar.DATE, 2);
        }

        return calendar.getTime();
    }

//    public static Integer diffBetweenDates(Date dateInitial, Date dateFinal, Integer type) {
//        Integer result = 0;
//        Period period = new Period(dateInitial.getTime(), dateFinal.getTime());
//        if (type == Constants.DATE_TYPE_DIFF_MINUTES) {
//            result = period.getMinutes();
//        }
//
//        if (type == Constants.DATE_TYPE_DIFF_DAYS) {
//            result = period.getMonths();
//        }
//
//        return result;
//    }

    public static void limitDateSearch(Date dateInitial, Date dateFinal, int datelimit) {
        long diff = dateFinal.getTime() - dateInitial.getTime();
        long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        if (days > datelimit)  {
            throw new RuntimeException("Intervalo de pesquisa acima do limite permitido. Máximo: 31 dias.");
        }
    }

    public static Date changeMonthDate(Date date, Integer value) {
        Calendar modifiedDate = Calendar.getInstance();
        modifiedDate.setTime(date);
        modifiedDate.add(Calendar.MONTH, value);
        return modifiedDate.getTime();
    }

    public static String dateToString(Date date) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN);
            return simpleDateFormat.format(date);
        } catch (Exception pe) {
            throw new RuntimeException("Falha na conversão da Data");
        }
    }
}
