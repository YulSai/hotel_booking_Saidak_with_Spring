package com.company.hotel_booking.data.entity;

import com.company.hotel_booking.utils.managers.SqlManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Class describing the object ReservationInfo
 */
@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "reservation_info")
@SQLDelete(sql = SqlManager.SQL_RESERVATION_INFO_DELETE)
@Where(clause = "deleted = false")
public class ReservationInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "room_id")
    private Room room;
    @Column(name = "check_in")
    private LocalDate checkIn;
    @Column(name = "check_out")
    private LocalDate checkOut;
    @Column(name = "nights")
    private Long nights;
    @Column(name = "room_price")
    private BigDecimal roomPrice;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ReservationInfo reservationInfo = (ReservationInfo) o;
        return id != null && Objects.equals(id, reservationInfo.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "ReservationInfo{" +
                "id=" + id +
                ", reservation=" + reservation +
                ", room=" + room +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                ", nights=" + nights +
                ", roomPrice=" + roomPrice +
                '}';
    }
}