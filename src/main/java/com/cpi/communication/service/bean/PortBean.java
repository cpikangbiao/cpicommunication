/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: PortBean
 * Author:   admin
 * Date:     2018/11/15 11:04
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
public class PortBean implements Serializable{

    private String portCode;

    private String portName;

    private String portNameChinese;

    private List<CorrespondentBean> correspondentBeans;

    public void init(String portCode, String portName, String portNameChinese) {
        this.portCode = portCode;
        this.portName = portName;
        this.portNameChinese = portNameChinese;
    }

    public void addCorrespondentBean(CorrespondentBean correspondentBean) {
        this.correspondentBeans.add(correspondentBean);
    }

    public String getPortCode() {
        return portCode;
    }

    public void setPortCode(String portCode) {
        this.portCode = portCode;
    }

    public String getPortName() {
        return portName;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }

    public String getPortNameChinese() {
        return portNameChinese;
    }

    public void setPortNameChinese(String portNameChinese) {
        this.portNameChinese = portNameChinese;
    }

    public List<CorrespondentBean> getCorrespondentBeans() {
        return correspondentBeans;
    }

    public void setCorrespondentBeans(List<CorrespondentBean> correspondentBeans) {
        this.correspondentBeans = correspondentBeans;
    }

    public PortBean() {
        this.correspondentBeans = new ArrayList<>();
    }
}
