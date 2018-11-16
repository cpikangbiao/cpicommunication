/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: CountryListBean
 * Author:   admin
 * Date:     2018/11/15 15:47
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cpi.communication.service.bean.list;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author admin
 * @create 2018/11/15
 * @since 1.0.0
 */
public class CountryListBean implements Serializable{

    private Long id;

    private String countryName;

    public CountryListBean(Long id, String countryName) {
        this.id = id;
        this.countryName = countryName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public CountryListBean() {

    }
}
