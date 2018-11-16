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
import com.cpi.communication.service.utility.common.TimeFormatUtility;
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
public class CorrespondentToolUtility {

    @Autowired
    public JasperReportUtility jasperReportUtility;

    @Autowired
    public ExcelUtility excelUtility;

}
