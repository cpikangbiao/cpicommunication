/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: CorrespondentBean
 * Author:   admin
 * Date:     2018/11/15 11:05
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
public class CorrespondentBean implements Serializable {

    private String correspondentName;

    private String faxNumber;

    private String address;

    private String telephoneOffice;

    private String telephoneAlternate;

    private String webSite;

    private List<CorrespondentContactBean> correspondentContactBeans;

    public CorrespondentBean() {
        this.correspondentContactBeans = new ArrayList<>();
    }

    public void init(String correspondentName, String faxNumber, String address, String telephoneOffice, String telephoneAlternate, String webSite) {
        this.correspondentName = correspondentName;
        this.faxNumber = faxNumber;
        this.address = address;
        this.telephoneOffice = telephoneOffice;
        this.telephoneAlternate = telephoneAlternate;
        this.webSite = webSite;
    }

    public void addCorrespondentContactBean(CorrespondentContactBean correspondentContactBean) {
        this.correspondentContactBeans.add(correspondentContactBean);
    }

    public String getCorrespondentName() {
        return correspondentName;
    }

    public void setCorrespondentName(String correspondentName) {
        this.correspondentName = correspondentName;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephoneOffice() {
        return telephoneOffice;
    }

    public void setTelephoneOffice(String telephoneOffice) {
        this.telephoneOffice = telephoneOffice;
    }

    public String getTelephoneAlternate() {
        return telephoneAlternate;
    }

    public void setTelephoneAlternate(String telephoneAlternate) {
        this.telephoneAlternate = telephoneAlternate;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public List<CorrespondentContactBean> getCorrespondentContactBeans() {
        return correspondentContactBeans;
    }

    public void setCorrespondentContactBeans(List<CorrespondentContactBean> correspondentContactBeans) {
        this.correspondentContactBeans = correspondentContactBeans;
    }
}
