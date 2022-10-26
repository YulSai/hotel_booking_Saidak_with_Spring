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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class describing the object Reservation
 */
@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "reservations")
@SQLDelete(sql = SqlManager.SQL_RESERVATION_DELETE)
@Where(clause = "deleted = false")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "total_cost")
    private BigDecimal totalCost;

    @Column(name = "status_id")
    private Status status;

    @OneToMany(mappedBy = "reservation", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private List<ReservationInfo> details = new ArrayList<>();

    public void addDetails (ReservationInfo info){
        details.add(info);
        info.setReservation(this);
    }

    public enum Status {
        IN_PROGRESS(1L),
        CONFIRMED(2L),
        REJECTED(3L),
        DELETED(4L);

        private final Long id;

        public Long getId() {
            return id;
        }

        Status(Long id) {
            this.id = id;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Reservation reservation = (Reservation) o;
        return id != null && Objects.equals(id, reservation.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", user=" + user +
                ", totalCost=" + totalCost +
                ", status=" + status +
                '}';
    }
}