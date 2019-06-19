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
 * Criteria class for the CorrespondentContact entity. This class is used in CorrespondentContactResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /correspondent-contacts?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CorrespondentContactCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter correspondentContactName;

    private StringFilter eMail;

    private StringFilter telephoneOffice;

    private StringFilter telephone;

    private StringFilter webSite;

    private LongFilter correspondentId;

    public CorrespondentContactCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCorrespondentContactName() {
        return correspondentContactName;
    }

    public void setCorrespondentContactName(StringFilter correspondentContactName) {
        this.correspondentContactName = correspondentContactName;
    }

    public StringFilter geteMail() {
        return eMail;
    }

    public void seteMail(StringFilter eMail) {
        this.eMail = eMail;
    }

    public StringFilter getTelephoneOffice() {
        return telephoneOffice;
    }

    public void setTelephoneOffice(StringFilter telephoneOffice) {
        this.telephoneOffice = telephoneOffice;
    }

    public StringFilter getTelephone() {
        return telephone;
    }

    public void setTelephone(StringFilter telephone) {
        this.telephone = telephone;
    }

    public StringFilter getWebSite() {
        return webSite;
    }

    public void setWebSite(StringFilter webSite) {
        this.webSite = webSite;
    }

    public LongFilter getCorrespondentId() {
        return correspondentId;
    }

    public void setCorrespondentId(LongFilter correspondentId) {
        this.correspondentId = correspondentId;
    }

    @Override
    public String toString() {
        return "CorrespondentContactCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (correspondentContactName != null ? "correspondentContactName=" + correspondentContactName + ", " : "") +
                (eMail != null ? "eMail=" + eMail + ", " : "") +
                (telephoneOffice != null ? "telephoneOffice=" + telephoneOffice + ", " : "") +
                (telephone != null ? "telephone=" + telephone + ", " : "") +
                (webSite != null ? "webSite=" + webSite + ", " : "") +
                (correspondentId != null ? "correspondentId=" + correspondentId + ", " : "") +
            "}";
    }

}
