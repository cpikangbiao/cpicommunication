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
 * Criteria class for the {@link com.cpi.communication.domain.Country} entity. This class is used
 * in {@link com.cpi.communication.web.rest.CountryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /countries?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CountryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter countryName;

    private StringFilter countryNameAbbr;

    private StringFilter countryNameChinese;

    private StringFilter dialCode;

    public CountryCriteria(){
    }

    public CountryCriteria(CountryCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.countryName = other.countryName == null ? null : other.countryName.copy();
        this.countryNameAbbr = other.countryNameAbbr == null ? null : other.countryNameAbbr.copy();
        this.countryNameChinese = other.countryNameChinese == null ? null : other.countryNameChinese.copy();
        this.dialCode = other.dialCode == null ? null : other.dialCode.copy();
    }

    @Override
    public CountryCriteria copy() {
        return new CountryCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCountryName() {
        return countryName;
    }

    public void setCountryName(StringFilter countryName) {
        this.countryName = countryName;
    }

    public StringFilter getCountryNameAbbr() {
        return countryNameAbbr;
    }

    public void setCountryNameAbbr(StringFilter countryNameAbbr) {
        this.countryNameAbbr = countryNameAbbr;
    }

    public StringFilter getCountryNameChinese() {
        return countryNameChinese;
    }

    public void setCountryNameChinese(StringFilter countryNameChinese) {
        this.countryNameChinese = countryNameChinese;
    }

    public StringFilter getDialCode() {
        return dialCode;
    }

    public void setDialCode(StringFilter dialCode) {
        this.dialCode = dialCode;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CountryCriteria that = (CountryCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(countryName, that.countryName) &&
            Objects.equals(countryNameAbbr, that.countryNameAbbr) &&
            Objects.equals(countryNameChinese, that.countryNameChinese) &&
            Objects.equals(dialCode, that.dialCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        countryName,
        countryNameAbbr,
        countryNameChinese,
        dialCode
        );
    }

    @Override
    public String toString() {
        return "CountryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (countryName != null ? "countryName=" + countryName + ", " : "") +
                (countryNameAbbr != null ? "countryNameAbbr=" + countryNameAbbr + ", " : "") +
                (countryNameChinese != null ? "countryNameChinese=" + countryNameChinese + ", " : "") +
                (dialCode != null ? "dialCode=" + dialCode + ", " : "") +
            "}";
    }

}
