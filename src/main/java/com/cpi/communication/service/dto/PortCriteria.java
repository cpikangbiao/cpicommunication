package com.cpi.communication.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the Port entity. This class is used in PortResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /ports?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PortCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter portCode;

    private StringFilter portName;

    private StringFilter portNameChinese;

    private LongFilter countryId;

    public PortCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getPortCode() {
        return portCode;
    }

    public void setPortCode(StringFilter portCode) {
        this.portCode = portCode;
    }

    public StringFilter getPortName() {
        return portName;
    }

    public void setPortName(StringFilter portName) {
        this.portName = portName;
    }

    public StringFilter getPortNameChinese() {
        return portNameChinese;
    }

    public void setPortNameChinese(StringFilter portNameChinese) {
        this.portNameChinese = portNameChinese;
    }

    public LongFilter getCountryId() {
        return countryId;
    }

    public void setCountryId(LongFilter countryId) {
        this.countryId = countryId;
    }

    @Override
    public String toString() {
        return "PortCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (portCode != null ? "portCode=" + portCode + ", " : "") +
                (portName != null ? "portName=" + portName + ", " : "") +
                (portNameChinese != null ? "portNameChinese=" + portNameChinese + ", " : "") +
                (countryId != null ? "countryId=" + countryId + ", " : "") +
            "}";
    }

}
