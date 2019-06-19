package com.cpi.communication.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Port entity.
 */
public class PortDTO implements Serializable {

    private Long id;

    private String portCode;

    private String portName;

    private String portNameChinese;

    private Long countryId;

    private String countryCountryName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPortCode() {
        return portCode;
    }

    public void setPortCode(String portCode) {
        this.portCode = portCode;
    }

    public String getPortName() {
        return portName;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }

    public String getPortNameChinese() {
        return portNameChinese;
    }

    public void setPortNameChinese(String portNameChinese) {
        this.portNameChinese = portNameChinese;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public String getCountryCountryName() {
        return countryCountryName;
    }

    public void setCountryCountryName(String countryCountryName) {
        this.countryCountryName = countryCountryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PortDTO portDTO = (PortDTO) o;
        if (portDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), portDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PortDTO{" +
            "id=" + getId() +
            ", portCode='" + getPortCode() + "'" +
            ", portName='" + getPortName() + "'" +
            ", portNameChinese='" + getPortNameChinese() + "'" +
            ", country=" + getCountryId() +
            ", country='" + getCountryCountryName() + "'" +
            "}";
    }
}
