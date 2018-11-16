package com.cpi.communication.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A CorrespondentContact.
 */
@Entity
@Table(name = "correspondent_contact")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CorrespondentContact extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "correspondent_contact_name", nullable = false)
    private String correspondentContactName;

    @NotNull
    @Column(name = "e_mail", nullable = false)
    private String eMail;

    @Column(name = "telephone_office")
    private String telephoneOffice;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "web_site")
    private String webSite;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Correspondent correspondent;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCorrespondentContactName() {
        return correspondentContactName;
    }

    public CorrespondentContact correspondentContactName(String correspondentContactName) {
        this.correspondentContactName = correspondentContactName;
        return this;
    }

    public void setCorrespondentContactName(String correspondentContactName) {
        this.correspondentContactName = correspondentContactName;
    }

    public String geteMail() {
        return eMail;
    }

    public CorrespondentContact eMail(String eMail) {
        this.eMail = eMail;
        return this;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getTelephoneOffice() {
        return telephoneOffice;
    }

    public CorrespondentContact telephoneOffice(String telephoneOffice) {
        this.telephoneOffice = telephoneOffice;
        return this;
    }

    public void setTelephoneOffice(String telephoneOffice) {
        this.telephoneOffice = telephoneOffice;
    }

    public String getTelephone() {
        return telephone;
    }

    public CorrespondentContact telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getWebSite() {
        return webSite;
    }

    public CorrespondentContact webSite(String webSite) {
        this.webSite = webSite;
        return this;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public Correspondent getCorrespondent() {
        return correspondent;
    }

    public CorrespondentContact correspondent(Correspondent correspondent) {
        this.correspondent = correspondent;
        return this;
    }

    public void setCorrespondent(Correspondent correspondent) {
        this.correspondent = correspondent;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CorrespondentContact correspondentContact = (CorrespondentContact) o;
        if (correspondentContact.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), correspondentContact.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CorrespondentContact{" +
            "id=" + getId() +
            ", correspondentContactName='" + getCorrespondentContactName() + "'" +
            ", eMail='" + geteMail() + "'" +
            ", telephoneOffice='" + getTelephoneOffice() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", webSite='" + getWebSite() + "'" +
            "}";
    }
}
