package com.dnt.graph.biz.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * date 工具类 DateUtil
 * 
 * @author ting.weit 2011-8-23
 * @alitalkID weitingjava
 */
public class DateUtil {

    private DateUtil() {
    }

    public static final String                         DATE_PATTERN_SIMPLE                  = "yyyy-MM-dd";

    public static final String                         DATE_PATTERN_ZH                      = "yyyy年MM月dd日";

    public static final String                         DATE_TIME_PATTERN_SIMPLE             = "yyyy-MM-dd HH:mm:ss";

    public static final String                         DATE_TIME_PATTERN_ZH                 = "yyyy年MM月dd日 HH:mm:ss";

    public static final String                         YYYYMM                               = "yyyyMM";
    
    public static final String                         DATE_TIME_PATTERN_NO_LINE            = "yyyyMMddHHmmss";

    private static final ThreadLocal<SimpleDateFormat> THREADLOCAL_EMPTY_PATTERN            = new ThreadLocal<SimpleDateFormat>() {

                                                                                                @Override
                                                                                                protected synchronized SimpleDateFormat initialValue() {
                                                                                                    return new SimpleDateFormat();
                                                                                                }
                                                                                            };

    private static final ThreadLocal<SimpleDateFormat> THREADLOCAL_DATE_PATTERN_SIMPLE      = new ThreadLocal<SimpleDateFormat>() {

                                                                                                @Override
                                                                                                protected synchronized SimpleDateFormat initialValue() {
                                                                                                    return new SimpleDateFormat(
                                                                                                                                DATE_PATTERN_SIMPLE);
                                                                                                }
                                                                                            };

    private static final ThreadLocal<SimpleDateFormat> THREADLOCAL_DATE_PATTERN_ZH          = new ThreadLocal<SimpleDateFormat>() {

                                                                                                @Override
                                                                                                protected synchronized SimpleDateFormat initialValue() {
                                                                                                    return new SimpleDateFormat(
                                                                                                                                DATE_PATTERN_ZH);
                                                                                                }
                                                                                            };

    private static final ThreadLocal<SimpleDateFormat> THREADLOCAL_DATE_TIME_PATTERN_ZH     = new ThreadLocal<SimpleDateFormat>() {

                                                                                                @Override
                                                                                                protected synchronized SimpleDateFormat initialValue() {
                                                                                                    return new SimpleDateFormat(
                                                                                                                                DATE_TIME_PATTERN_ZH);
                                                                                                }
                                                                                            };

    private static final ThreadLocal<SimpleDateFormat> THREADLOCAL_DATE_TIME_PATTERN_SIMPLE = new ThreadLocal<SimpleDateFormat>() {

                                                                                                @Override
                                                                                                protected synchronized SimpleDateFormat initialValue() {
                                                                                                    return new SimpleDateFormat(
                                                                                                                                DATE_TIME_PATTERN_SIMPLE);
                                                                                                }
                                                                                            };
    private static final ThreadLocal<SimpleDateFormat> THREADLOCAL_DATE_TIME_PATTERN_NO_LINE = new ThreadLocal<SimpleDateFormat>() {

        @Override
        protected synchronized SimpleDateFormat initialValue() {
            return new SimpleDateFormat(
            		DATE_TIME_PATTERN_NO_LINE);
        }
    };
/**
     * 按“yyyy-MM-dd HH:mm:ss”格式化Date为String
     * 
     * @param date Date对象
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String formatDateTimeSimple(Date date) {
        return THREADLOCAL_DATE_TIME_PATTERN_SIMPLE.get().format(date);
    }
    /**
     * 按“yyyy-MM-dd HH:mm:ss”格式化Date为String
     * 
     * @param date Date对象
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String formatDateTimeNoLine(Date date) {
        return THREADLOCAL_DATE_TIME_PATTERN_NO_LINE.get().format(date);
    }
    /**
     * 按“yyyy年MM月dd日 HH:mm:ss”格式化Date为String
     * 
     * @param date Date对象
     * @return yyyy年MM月dd日 HH:mm:ss
     */
    public static String formatDateTimeZH(Date date) {
        return THREADLOCAL_DATE_TIME_PATTERN_ZH.get().format(date);
    }

    /**
     * 按“yyyy-MM-dd”格式化Date为String
     * 
     * @param date Date对象
     * @return yyyy-MM-dd
     */
    public static String formatDateSimple(Date date) {
        return THREADLOCAL_DATE_PATTERN_SIMPLE.get().format(date);
    }

    /**
     * 按“yyyy年MM月dd日”格式化Date为String
     * 
     * @param date Date对象
     * @return yyyy年MM月dd日
     */
    public static String formatDateZH(Date date) {
        return THREADLOCAL_DATE_PATTERN_ZH.get().format(date);
    }

    /**
     * 按 pattern 格式化Date为String
     * 
     * @param date Date对象
     * @param pattern 转换后String的格式
     * @return pattern
     */
    public static String format(Date date, String pattern) {
        THREADLOCAL_EMPTY_PATTERN.get().applyPattern(pattern);
        return THREADLOCAL_EMPTY_PATTERN.get().format(date);
    }

    /**
     * 按 pattern 解析dateString为Date
     * 
     * @param dateString 日期的字符串形式
     * @param pattern dateString的格式
     * @return Date
     * @throws ParseException 解析错误时抛出异常
     */
    public static Date parse(String dateString, String pattern) throws ParseException {
        THREADLOCAL_EMPTY_PATTERN.get().applyPattern(pattern);
        return THREADLOCAL_EMPTY_PATTERN.get().parse(dateString);
    }

    public static String[] getStringDate(String dateStringYYYYMM) {
        String array[] = new String[2];
        Calendar calendar = Calendar.getInstance();
        Date date = null;
        try {
            date = parse(dateStringYYYYMM, YYYYMM);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(date);
        int day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        String str = String.format("%1$tY-%1$tm", date);
        array[0] = str + "-01";
        array[1] = str + "-" + day;
        return array;
    }

    public static String[] addMonthToStringDate(final String dateStringYYYYMM, int num) {
        String array[] = new String[2];
        Calendar calendar = Calendar.getInstance();
        Date date = null;
        try {
            date = parse(dateStringYYYYMM, YYYYMM);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(date);
        int day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);// 获取月份没有进行过相加后的月份总天数
        array[1] = String.format("%1$tY-%1$tm", calendar.getTime()) + "-" + day;// 结束日期
        calendar.add(Calendar.MONTH, num);
        array[0] = String.format("%1$tY-%1$tm", calendar.getTime()) + "-01";// 开始日期
        return array;
    }
}
