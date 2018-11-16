package com.cpi.communication.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cpi.communication.service.utility.CorrespondentBookUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing Correspondent.
 */
@RestController
@RequestMapping("/api/test")
public class TestResource {

    private final Logger log = LoggerFactory.getLogger(TestResource.class);

    private static final String ENTITY_NAME = "correspondent";

    private CorrespondentBookUtility correspondentBookUtility;

    public TestResource(CorrespondentBookUtility correspondentBookUtility) {
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

    @GetMapping("/correspondents/book/word")
    @Timed
    public ResponseEntity<byte[]> createCorrespondentBookWord() {
        log.debug("REST request to get createCorrespondentBook " );
        byte[] bytes = correspondentBookUtility.createCorrespondentBookWord();

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document"));
        StringBuilder fileName = new StringBuilder();
        fileName.append("attachment; filename=").append("\"").append(correspondentBookUtility.createCorrespondentBookNameWord()).append("\"");
        header.set(HttpHeaders.CONTENT_DISPOSITION, fileName.toString());

        if (bytes != null) {
            header.setContentLength(bytes.length);
        }

        return new ResponseEntity<>(bytes, header, HttpStatus.OK);
    }

}
