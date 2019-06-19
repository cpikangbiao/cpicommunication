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
 * Criteria class for the Country entity. This class is used in CountryResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /countries?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CountryCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter countryName;

    private StringFilter countryNameAbbr;

    private StringFilter countryNameChinese;

    private StringFilter dialCode;

    public CountryCriteria() {
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
