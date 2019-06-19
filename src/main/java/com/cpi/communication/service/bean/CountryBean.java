/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: CountryBean
 * Author:   admin
 * Date:     2018/11/15 11:02
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cpi.communication.service.bean;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author admin
 * @create 2018/11/15
 * @since 1.0.0
 */
public class CountryBean implements Serializable {

    private String countryName;

    private String countryNameAbbr;

    private String countryNameChinese;

    private String dialCode;

    private String timeZone;

    List<PortBean> portBeans;

    public CountryBean() {
        this.portBeans = new ArrayList<>();
    }

    public void init(String countryName, String countryNameAbbr, String countryNameChinese, String dialCode, String timeZone) {
        this.countryName = countryName;
        this.countryNameAbbr = countryNameAbbr;
        this.countryNameChinese = countryNameChinese;
        this.dialCode = dialCode;
        this.timeZone = timeZone;
    }

    public void addPortBean(PortBean portBean) {
        this.portBeans.add(portBean);
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryNameAbbr() {
        return countryNameAbbr;
    }

    public void setCountryNameAbbr(String countryNameAbbr) {
        this.countryNameAbbr = countryNameAbbr;
    }

    public String getCountryNameChinese() {
        return countryNameChinese;
    }

    public void setCountryNameChinese(String countryNameChinese) {
        this.countryNameChinese = countryNameChinese;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getDialCode() {
        return dialCode;
    }

    public void setDialCode(String dialCode) {
        this.dialCode = dialCode;
    }

    public List<PortBean> getPortBeans() {
        return portBeans;
    }

    public void setPortBeans(List<PortBean> portBeans) {
        this.portBeans = portBeans;
    }
}
