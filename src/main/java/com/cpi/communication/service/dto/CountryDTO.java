package com.cpi.communication.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Country entity.
 */
public class CountryDTO implements Serializable {

    private Long id;

    @NotNull
    private String countryName;

    @NotNull
    private String countryNameAbbr;

    private String countryNameChinese;

    private String dialCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryNameAbbr() {
        return countryNameAbbr;
    }

    public void setCountryNameAbbr(String countryNameAbbr) {
        this.countryNameAbbr = countryNameAbbr;
    }

    public String getCountryNameChinese() {
        return countryNameChinese;
    }

    public void setCountryNameChinese(String countryNameChinese) {
        this.countryNameChinese = countryNameChinese;
    }

    public String getDialCode() {
        return dialCode;
    }

    public void setDialCode(String dialCode) {
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

        CountryDTO countryDTO = (CountryDTO) o;
        if (countryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), countryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CountryDTO{" +
            "id=" + getId() +
            ", countryName='" + getCountryName() + "'" +
            ", countryNameAbbr='" + getCountryNameAbbr() + "'" +
            ", countryNameChinese='" + getCountryNameChinese() + "'" +
            ", dialCode='" + getDialCode() + "'" +
            "}";
    }
}
