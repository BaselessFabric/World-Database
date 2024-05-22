package org.example.dungeonsanddebugerss.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;

@Entity
@Table(name = "countrylanguage")
public class CountryLanguageEntity {
    @EmbeddedId
    private CountryLanguageEntityId id;

    @MapsId("countryCode")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @ColumnDefault("''")
    @JoinColumn(name = "CountryCode", nullable = false)
    private CountryEntity countryCode;

    @NotNull
    @ColumnDefault("'F'")
    @Lob
    @Column(name = "IsOfficial", nullable = false)
    private String isOfficial;

    @NotNull
    @ColumnDefault("0.0")
    @Column(name = "Percentage", nullable = false, precision = 4, scale = 1)
    private BigDecimal percentage;

    public CountryLanguageEntityId getId() {
        return id;
    }

    public void setId(CountryLanguageEntityId id) {
        this.id = id;
    }

    public CountryEntity getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(CountryEntity countryCode) {
        this.countryCode = countryCode;
    }

    public String getIsOfficial() {
        return isOfficial;
    }

    public void setIsOfficial(String isOfficial) {
        this.isOfficial = isOfficial;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    @Override
    public String toString() {
        return "CountrylanguageEntity{" +
                "id=" + id +
                ", countryCode=" + countryCode +
                ", isOfficial='" + isOfficial + '\'' +
                ", percentage=" + percentage +
                '}';
    }
}