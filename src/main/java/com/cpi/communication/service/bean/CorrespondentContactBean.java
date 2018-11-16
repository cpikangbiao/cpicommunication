/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: CorrespondentContactBean
 * Author:   admin
 * Date:     2018/11/15 11:07
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cpi.communication.service.bean;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author admin
 * @create 2018/11/15
 * @since 1.0.0
 */
public class CorrespondentContactBean implements Serializable{

    private String correspondentContactName;

    private String eMail;

    private String telephoneOffice;

    private String telephone;

    private String webSite;

    public void init(String correspondentContactName, String eMail, String telephoneOffice, String telephone, String webSite) {
        this.correspondentContactName = correspondentContactName;
        this.eMail = eMail;
        this.telephoneOffice = telephoneOffice;
        this.telephone = telephone;
        this.webSite = webSite;
    }

    public String getCorrespondentContactName() {
        return correspondentContactName;
    }

    public void setCorrespondentContactName(String correspondentContactName) {
        this.correspondentContactName = correspondentContactName;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getTelephoneOffice() {
        return telephoneOffice;
    }

    public void setTelephoneOffice(String telephoneOffice) {
        this.telephoneOffice = telephoneOffice;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public CorrespondentContactBean() {

    }
}
