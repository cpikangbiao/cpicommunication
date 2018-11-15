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
 * Criteria class for the Correspondent entity. This class is used in CorrespondentResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /correspondents?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CorrespondentCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter correspondentName;

    private StringFilter faxNumber;

    private StringFilter address;

    private StringFilter telephoneOffice;

    private StringFilter telephoneAlternate;

    private StringFilter webSite;

    private LongFilter portId;

    public CorrespondentCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCorrespondentName() {
        return correspondentName;
    }

    public void setCorrespondentName(StringFilter correspondentName) {
        this.correspondentName = correspondentName;
    }

    public StringFilter getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(StringFilter faxNumber) {
        this.faxNumber = faxNumber;
    }

    public StringFilter getAddress() {
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
    }

    public StringFilter getTelephoneOffice() {
        return telephoneOffice;
    }

    public void setTelephoneOffice(StringFilter telephoneOffice) {
        this.telephoneOffice = telephoneOffice;
    }

    public StringFilter getTelephoneAlternate() {
        return telephoneAlternate;
    }

    public void setTelephoneAlternate(StringFilter telephoneAlternate) {
        this.telephoneAlternate = telephoneAlternate;
    }

    public StringFilter getWebSite() {
        return webSite;
    }

    public void setWebSite(StringFilter webSite) {
        this.webSite = webSite;
    }

    public LongFilter getPortId() {
        return portId;
    }

    public void setPortId(LongFilter portId) {
        this.portId = portId;
    }

    @Override
    public String toString() {
        return "CorrespondentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (correspondentName != null ? "correspondentName=" + correspondentName + ", " : "") +
                (faxNumber != null ? "faxNumber=" + faxNumber + ", " : "") +
                (address != null ? "address=" + address + ", " : "") +
                (telephoneOffice != null ? "telephoneOffice=" + telephoneOffice + ", " : "") +
                (telephoneAlternate != null ? "telephoneAlternate=" + telephoneAlternate + ", " : "") +
                (webSite != null ? "webSite=" + webSite + ", " : "") +
                (portId != null ? "portId=" + portId + ", " : "") +
            "}";
    }

}
