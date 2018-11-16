package com.cpi.communication.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Correspondent.
 */
@Entity
@Table(name = "correspondent")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Correspondent extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "correspondent_name", nullable = false)
    private String correspondentName;

    @NotNull
    @Column(name = "fax_number", nullable = false)
    private String faxNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "telephone_office")
    private String telephoneOffice;

    @Column(name = "telephone_alternate")
    private String telephoneAlternate;

    @Column(name = "web_site")
    private String webSite;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Port port;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCorrespondentName() {
        return correspondentName;
    }

    public Correspondent correspondentName(String correspondentName) {
        this.correspondentName = correspondentName;
        return this;
    }

    public void setCorrespondentName(String correspondentName) {
        this.correspondentName = correspondentName;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public Correspondent faxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
        return this;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public String getAddress() {
        return address;
    }

    public Correspondent address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephoneOffice() {
        return telephoneOffice;
    }

    public Correspondent telephoneOffice(String telephoneOffice) {
        this.telephoneOffice = telephoneOffice;
        return this;
    }

    public void setTelephoneOffice(String telephoneOffice) {
        this.telephoneOffice = telephoneOffice;
    }

    public String getTelephoneAlternate() {
        return telephoneAlternate;
    }

    public Correspondent telephoneAlternate(String telephoneAlternate) {
        this.telephoneAlternate = telephoneAlternate;
        return this;
    }

    public void setTelephoneAlternate(String telephoneAlternate) {
        this.telephoneAlternate = telephoneAlternate;
    }

    public String getWebSite() {
        return webSite;
    }

    public Correspondent webSite(String webSite) {
        this.webSite = webSite;
        return this;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public Port getPort() {
        return port;
    }

    public Correspondent port(Port port) {
        this.port = port;
        return this;
    }

    public void setPort(Port port) {
        this.port = port;
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
        Correspondent correspondent = (Correspondent) o;
        if (correspondent.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), correspondent.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Correspondent{" +
            "id=" + getId() +
            ", correspondentName='" + getCorrespondentName() + "'" +
            ", faxNumber='" + getFaxNumber() + "'" +
            ", address='" + getAddress() + "'" +
            ", telephoneOffice='" + getTelephoneOffice() + "'" +
            ", telephoneAlternate='" + getTelephoneAlternate() + "'" +
            ", webSite='" + getWebSite() + "'" +
            "}";
    }
}
