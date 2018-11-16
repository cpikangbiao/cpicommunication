package com.cpi.communication.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cpi.communication.service.CorrespondentQueryService;
import com.cpi.communication.service.CorrespondentService;
import com.cpi.communication.service.dto.CorrespondentCriteria;
import com.cpi.communication.service.dto.CorrespondentDTO;
import com.cpi.communication.service.utility.CorrespondentBookUtility;
import com.cpi.communication.web.rest.errors.BadRequestAlertException;
import com.cpi.communication.web.rest.util.HeaderUtil;
import com.cpi.communication.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Correspondent.
 */
@RestController
@RequestMapping("/api")
public class CorrespondentExtResource {

    private final Logger log = LoggerFactory.getLogger(CorrespondentExtResource.class);

    private static final String ENTITY_NAME = "correspondent";

    private CorrespondentBookUtility correspondentBookUtility;

    public CorrespondentExtResource(CorrespondentBookUtility correspondentBookUtility) {
        this.correspondentBookUtility = correspondentBookUtility;
    }

    @GetMapping("/correspondents/book/pdf")
    @Timed
    public ResponseEntity<byte[]> createCorrespondentBookPDF() {
        log.debug("REST request to get createCorrespondentBook " );
        byte[] bytes = correspondentBookUtility.createCorrespondentBookPDF();

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.parseMediaType("application/pdf"));
        StringBuilder fileName = new StringBuilder();
        fileName.append("attachment; filename=").append("\"").append(correspondentBookUtility.createCorrespondentBookNamePDF()).append("\"");
        header.set(HttpHeaders.CONTENT_DISPOSITION, fileName.toString());

        if (bytes != null) {
            header.setContentLength(bytes.length);
        }

        return new ResponseEntity<>(bytes, header, HttpStatus.OK);
    }

    @GetMapping("/correspondents/book/excel")
    @Timed
    public ResponseEntity<byte[]> createCorrespondentBookExcel() {
        log.debug("REST request to get createCorrespondentBook " );
        byte[] bytes = correspondentBookUtility.createCorrespondentBookExcel();

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        StringBuilder fileName = new StringBuilder();
        fileName.append("attachment; filename=").append("\"").append(correspondentBookUtility.createCorrespondentBookNameExcel()).append("\"");
        header.set(HttpHeaders.CONTENT_DISPOSITION, fileName.toString());

        if (bytes != null) {
            header.setContentLength(bytes.length);
        }

        return new ResponseEntity<>(bytes, header, HttpStatus.OK);
    }

}
