package com.loginext.taxidriver.entity;

import com.loginext.taxidriver.enums.RideStatus;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "driver_assignment_status")
public class DriverAssignmentStatus {

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getDestLatitude() {
        return DestLatitude;
    }

    public void setDestLatitude(BigDecimal destLatitude) {
        DestLatitude = destLatitude;
    }

    public BigDecimal getDestLongitude() {
        return DestLongitude;
    }

    public void setDestLongitude(BigDecimal destLongitude) {
        DestLongitude = destLongitude;
    }

    public RideStatus getStatus() {
        return status;
    }

    public void setStatus(RideStatus status) {
        this.status = status;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "driver_id", referencedColumnName = "id")
    private Driver driver;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "dest_lat")
    private BigDecimal DestLatitude;

    @Column(name = "dest_long")
    private BigDecimal DestLongitude;

    @Column(name = "ride_status")
    @Enumerated(EnumType.STRING)
    private RideStatus status;
}
