/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: TimeFormatUtility
 * Author:   admin
 * Date:     2018/10/30 13:54
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cpi.communication.service.utility.common;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author admin
 * @create 2018/10/30
 * @since 1.0.0
 */
public class TimeFormatUtility {

    private static final String DATE_FORMAT_ENGLISH = "dd MMMM yyyy";

    private static final String DATE_FORMAT_CHINESE = "yyyy年MM月dd日";


    public static String formatEnglishInstant(Instant instant){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT_ENGLISH, Locale.ENGLISH);
        return simpleDateFormat.format(new Date(instant.toEpochMilli()));
    }

    public static String formatChineseInstant(Instant instant){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT_CHINESE, Locale.CHINESE);
        return simpleDateFormat.format(new Date(instant.toEpochMilli()));
    }
}
