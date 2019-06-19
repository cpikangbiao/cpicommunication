package com.cpi.communication.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.cpi.communication.domain.Port} entity. This class is used
 * in {@link com.cpi.communication.web.rest.PortResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ports?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PortCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter portCode;

    private StringFilter portName;

    private StringFilter portNameChinese;

    private LongFilter countryId;

    public PortCriteria(){
    }

    public PortCriteria(PortCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.portCode = other.portCode == null ? null : other.portCode.copy();
        this.portName = other.portName == null ? null : other.portName.copy();
        this.portNameChinese = other.portNameChinese == null ? null : other.portNameChinese.copy();
        this.countryId = other.countryId == null ? null : other.countryId.copy();
    }

    @Override
    public PortCriteria copy() {
        return new PortCriteria(this);
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PortCriteria that = (PortCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(portCode, that.portCode) &&
            Objects.equals(portName, that.portName) &&
            Objects.equals(portNameChinese, that.portNameChinese) &&
            Objects.equals(countryId, that.countryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        portCode,
        portName,
        portNameChinese,
        countryId
        );
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
