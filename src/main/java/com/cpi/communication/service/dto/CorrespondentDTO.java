package com.cpi.communication.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Correspondent entity.
 */
public class CorrespondentDTO implements Serializable {

    private Long id;

    @NotNull
    private String correspondentName;

    @NotNull
    private String faxNumber;

    private String address;

    private String telephoneOffice;

    private String telephoneAlternate;

    private String webSite;

    private String email;

    private Long portId;

    private String portPortName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCorrespondentName() {
        return correspondentName;
    }

    public void setCorrespondentName(String correspondentName) {
        this.correspondentName = correspondentName;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephoneOffice() {
        return telephoneOffice;
    }

    public void setTelephoneOffice(String telephoneOffice) {
        this.telephoneOffice = telephoneOffice;
    }

    public String getTelephoneAlternate() {
        return telephoneAlternate;
    }

    public void setTelephoneAlternate(String telephoneAlternate) {
        this.telephoneAlternate = telephoneAlternate;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public Long getPortId() {
        return portId;
    }

    public void setPortId(Long portId) {
        this.portId = portId;
    }

    public String getPortPortName() {
        return portPortName;
    }

    public void setPortPortName(String portPortName) {
        this.portPortName = portPortName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CorrespondentDTO correspondentDTO = (CorrespondentDTO) o;
        if (correspondentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), correspondentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CorrespondentDTO{" +
            "id=" + getId() +
            ", correspondentName='" + getCorrespondentName() + "'" +
            ", faxNumber='" + getFaxNumber() + "'" +
            ", address='" + getAddress() + "'" +
            ", telephoneOffice='" + getTelephoneOffice() + "'" +
            ", telephoneAlternate='" + getTelephoneAlternate() + "'" +
            ", webSite='" + getWebSite() + "'" +
            ", port=" + getPortId() +
            ", port='" + getPortPortName() + "'" +
            "}";
    }
}
