/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: CorrespondentBookUtility
 * Author:   admin
 * Date:     2018/11/15 11:13
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cpi.communication.service.utility;

import com.cpi.communication.domain.Correspondent;
import com.cpi.communication.domain.CorrespondentContact;
import com.cpi.communication.domain.Country;
import com.cpi.communication.domain.Port;
import com.cpi.communication.repository.CorrespondentContactRepository;
import com.cpi.communication.repository.CorrespondentRepository;
import com.cpi.communication.repository.CountryRepository;
import com.cpi.communication.repository.PortRepository;
import com.cpi.communication.repository.excel.ExcelUtility;
import com.cpi.communication.repository.jasperreport.JasperReportUtility;
import com.cpi.communication.service.bean.CorrespondentBean;
import com.cpi.communication.service.bean.CorrespondentContactBean;
import com.cpi.communication.service.bean.CountryBean;
import com.cpi.communication.service.bean.PortBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author admin
 * @create 2018/11/15
 * @since 1.0.0
 */

@Service
@Transactional
public class CorrespondentBookUtility {

    private CountryRepository countryRepository;

    private PortRepository portRepository;

    private CorrespondentRepository correspondentRepository;

    private CorrespondentContactRepository correspondentContactRepository;

    @Autowired
    private JasperReportUtility jasperReportUtility;

    @Autowired
    private ExcelUtility excelUtility;

    public CorrespondentBookUtility(CountryRepository countryRepository, PortRepository portRepository, CorrespondentRepository correspondentRepository, CorrespondentContactRepository correspondentContactRepository) {
        this.countryRepository = countryRepository;
        this.portRepository = portRepository;
        this.correspondentRepository = correspondentRepository;
        this.correspondentContactRepository = correspondentContactRepository;
    }

    public byte[] createCorrespondentBookExcel() {
        Map<String, Object> parameter = new HashMap<String, Object>();
        ResponseEntity<byte[]> responseEntity  = new ResponseEntity(HttpStatus.OK);
        parameter.put("results", createCorrespondentBookBean());
        parameter.put("jxlid", 2);
        responseEntity  = excelUtility.processExcel(parameter);

        return responseEntity.getBody();
    }

    public byte[] createCorrespondentBookPDF() {
        Map<String, Object> parameter = new HashMap<String, Object>();
        ResponseEntity<byte[]> responseEntity  = new ResponseEntity(HttpStatus.OK);
        String jasperFilePath = "Correspondent_Book.jasper";
        parameter.put("datasource", createCorrespondentBookBean());
        Map<String, Object> subParameter = new HashMap<String, Object>();
        parameter.put("SUBREPORT_PARAMETER", subParameter);

        responseEntity  = jasperReportUtility.processPDF(jasperFilePath, parameter);

        return responseEntity.getBody();
    }


    @Transactional(readOnly = true)
    protected List<CountryBean> createCorrespondentBookBean() {
        List<CountryBean> countryBeans = new ArrayList<>();

        List<Country> countries = countryRepository.findAllByOrderByCountryName();
        for (Country country : countries) {
            CountryBean countryBean = new CountryBean();
            countryBean.init(
                country.getCountryName(),
                country.getCountryNameAbbr(),
                country.getCountryNameChinese(),
                country.getDialCode()
            );

            List<Port> ports = portRepository.findAllByCountryOrderByPortName(country);
            for (Port port : ports) {
                PortBean portBean = new PortBean();
                portBean.init(
                    port.getPortCode(),
                    port.getPortName(),
                    port.getPortNameChinese()
                );

                List<Correspondent> correspondents = correspondentRepository.findAllByPortOrderByCorrespondentName(port);
                for (Correspondent correspondent : correspondents) {
                    CorrespondentBean correspondentBean = new CorrespondentBean();
                    correspondentBean.init(
                        correspondent.getCorrespondentName(),
                        correspondent.getFaxNumber(),
                        correspondent.getAddress(),
                        correspondent.getTelephoneOffice(),
                        correspondent.getTelephoneAlternate(),
                        correspondent.getWebSite()
                    );

                    List<CorrespondentContact> correspondentContacts = correspondentContactRepository.findAllByCorrespondentOrderByCorrespondentContactName(correspondent);
                    for (CorrespondentContact correspondentContact : correspondentContacts) {
                        CorrespondentContactBean correspondentContactBean = new CorrespondentContactBean();
                        correspondentContactBean.init(
                            correspondentContact.getCorrespondentContactName(),
                            correspondentContact.geteMail(),
                            correspondentContact.getTelephoneOffice(),
                            correspondentContact.getTelephone(),
                            correspondentContact.getWebSite()
                        );
                        correspondentBean.addCorrespondentContactBean(correspondentContactBean);
                    }

                    portBean.addCorrespondentBean(correspondentBean);
                }
                if (portBean.getCorrespondentBeans() != null
                    && portBean.getCorrespondentBeans().size() > 0) {
                    countryBean.addPortBean(portBean);
                }
            }
            if (countryBean.getPortBeans().size() > 0) {
                countryBeans.add(countryBean);
            }

        }

        return countryBeans;
    }

    protected StringBuilder createCorrespondentBookName() {
        StringBuilder correspondentBookName = new StringBuilder();
        correspondentBookName.append("CPI Correspondent List (");
        correspondentBookName.append(Instant.now().toString()).append(")");
        return correspondentBookName;
    }

    public String createCorrespondentBookNamePDF() {
        return createCorrespondentBookName().append(".pdf").toString();
    }
    public String createCorrespondentBookNameExcel() {
        return createCorrespondentBookName().append(".xlsx").toString();
    }

}
