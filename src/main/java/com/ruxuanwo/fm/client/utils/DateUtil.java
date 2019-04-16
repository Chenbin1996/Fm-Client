package com.ruxuanwo.fm.client.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 操作时间
 *
 * @author ruxuanwo
 */
public class DateUtil {
    private DateUtil() {

    }

    private static final String DAY = "日";
    private static final String HOUR = "时";
    private static final String MINUTE = "分";
    private static final String SECONDS = "秒";

    private static final SimpleDateFormat SDF = new SimpleDateFormat();


    public static final String DATE_MMDDHHMMSS = "MM-dd HH:mm:ss";


    public static final String DATE_YYYY_MMDD_HHMMSS = "yyyyMMddHHmmss";


    public static final String DATE_YYYYMMDDHHMM = "yyyyMMddHHmm";


    public static final String DATE_YYYYMMDDHH = "yyyyMMddHH";


    public static final String DATE_YYYYMMDD = "yyyyMMdd";


    public static final String DATE_YYYYMM = "yyyyMM";


    public static final String DATE_YYYYMMDD_CHINA = "yyyy年MM月dd日";

    public static final String DATE_YYYYMMDDHHMMSS_CHINA = "yyyy年MM月dd日 HH时mm分ss秒";


    public static final String DATE_YYYY_M_MDD_E_CHINA = "yyyy年MM月dd日 E";


    public static final String DATE_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";


    public static final String DATE_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";


    public static final String DATE_YYYY_MM_DD_HH = "yyyy-MM-dd HH";


    public static final String DATE_YYYY_MM_DD = "yyyy-MM-dd";


    public static final String DATE_YYYY_MM_DD1 = "yyyy/MM/dd";


    public static final String DATE_EEEE = "EEEE";
    /**
     * 默认日期格式
     */
    public static final String DATE_DEFAULT_FORMAT = "yyyy-MM-dd";

    /**
     * 默认日期时间格式
     */
    public static final String DATETIME_DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 默认时间格式
     */
    public static final String TIME_DEFAULT_FORMAT = "HH:mm:ss";

    /**
     * 锁对象
     */
    private static final Object lockObj = new Object();

    /**
     * 存放不同的日期模板格式的sdf的Map
     */
    private static Map<String, ThreadLocal<SimpleDateFormat>> sdfMap = new HashMap<String, ThreadLocal<SimpleDateFormat>>();

    /**
     * 格式化日期
     *
     * @param date    日期
     * @param pattern 格式
     * @return 日期字符串
     */
    public static String formatDate(Date date, String pattern) {
        synchronized (SDF) {
            SDF.applyPattern(pattern);
            try {
                return SDF.format(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * 格式化日期
     *
     * @param time    秒数
     * @param pattern 格式
     * @return 日期字符串
     */
    public static String formatDate(long time, String pattern) {
        synchronized (SDF) {
            SDF.applyPattern(pattern);
            try {
                return SDF.format(new Date(time));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * 解析日期
     *
     * @param date    日期字符串
     * @param pattern 格式
     * @return 日期
     */
    public static Date parseDate(String date, String pattern) {
        synchronized (SDF) {
            SDF.applyPattern(pattern);
            try {
                return SDF.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    public static Long timeDiffMin(Date pBeginTime, Date pEndTime) {
        Long beginL = pBeginTime.getTime();
        Long endL = pEndTime.getTime();
        Long min = ((endL - beginL) % 86400000 % 3600000) / 60000;
        return min;
    }

    public static String formatDuring(long mss) {
        if (mss <= 0) {
            return String.valueOf(mss);
        }
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        StringBuilder sb = new StringBuilder();
        if (days > 0) {
            sb.append(days).append(DAY);
        }
        if (hours > 0) {
            sb.append(hours).append(HOUR);
        }
        if (minutes > 0) {
            sb.append(minutes).append(MINUTE);
        }
        if (seconds > 0) {
            sb.append(seconds).append(SECONDS);
        }
        return sb.toString();
    }

    /**
     * 返回一个ThreadLocal的SimpleDateFormat, 每个线程只会new一次SimpleDateFormat
     *
     * @param pattern
     * @return
     */
    private static SimpleDateFormat getSdf(final String pattern) {
        ThreadLocal<SimpleDateFormat> tl = sdfMap.get(pattern);

        // 此处的双重判断和同步是为了防止sdfMap这个单例被多次put重复的sdf
        if (tl == null) {
            synchronized (lockObj) {
                tl = sdfMap.get(pattern);
                if (tl == null) {
                    /// 只有Map中还没有这个pattern的sdf才会生成新的sdf并放入map
                    ///LOGGER.info("put new sdf of pattern {} to map", pattern);

                    ///  这里是关键,使用ThreadLocal<SimpleDateFormat>替代原来直接new SimpleDateFormat
                    tl = new ThreadLocal<SimpleDateFormat>() {

                        @Override
                        protected SimpleDateFormat initialValue() {

                            /// LOGGER.info("thread: {}, init pattern: {}.", Thread.currentThread(), pattern);
                            return new SimpleDateFormat(pattern);
                        }
                    };
                    sdfMap.put(pattern, tl);
                }
            }
        }

        return tl.get();
    }

    /**
     * 是用ThreadLocal<SimpleDateFormat>来获取SimpleDateFormat,这样每个线程只会有一个SimpleDateFormat
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, String pattern) {
        return getSdf(pattern).format(date);
    }

    public static Date parse(String dateStr, String pattern) throws ParseException {
        return getSdf(pattern).parse(dateStr);
    }
}
