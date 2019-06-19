package com.cpi.communication.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Port.
 */
@Entity
@Table(name = "port")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Port implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "port_code")
    private String portCode;

    @Column(name = "port_name")
    private String portName;

    @Column(name = "port_name_chinese")
    private String portNameChinese;

    @ManyToOne
    @JsonIgnoreProperties("ports")
    private Country country;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPortCode() {
        return portCode;
    }

    public Port portCode(String portCode) {
        this.portCode = portCode;
        return this;
    }

    public void setPortCode(String portCode) {
        this.portCode = portCode;
    }

    public String getPortName() {
        return portName;
    }

    public Port portName(String portName) {
        this.portName = portName;
        return this;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }

    public String getPortNameChinese() {
        return portNameChinese;
    }

    public Port portNameChinese(String portNameChinese) {
        this.portNameChinese = portNameChinese;
        return this;
    }

    public void setPortNameChinese(String portNameChinese) {
        this.portNameChinese = portNameChinese;
    }

    public Country getCountry() {
        return country;
    }

    public Port country(Country country) {
        this.country = country;
        return this;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Port)) {
            return false;
        }
        return id != null && id.equals(((Port) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Port{" +
            "id=" + getId() +
            ", portCode='" + getPortCode() + "'" +
            ", portName='" + getPortName() + "'" +
            ", portNameChinese='" + getPortNameChinese() + "'" +
            "}";
    }
}
