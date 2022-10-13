package com.company.hotel_booking.data.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Where;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * Class describing the object Room
 */
@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "rooms")
@Where(clause = "deleted = false")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "room_number")
    private String number;

    @Column(name = "room_type_id")
    private RoomType type;

    @Column(name = "room_capacity_id")
    private Capacity capacity;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "room_status_id")
    private RoomStatus status;

    @OneToMany(mappedBy = "room", cascade = CascadeType.REFRESH)
    private List<ReservationInfo> reservationInfo;

    public enum RoomStatus {
        AVAILABLE(1L),
        UNAVAILABLE(2L);

        private final Long id;

        public Long getId() {
            return id;
        }

        RoomStatus(Long id) {
            this.id = id;
        }
    }

    public enum RoomType {
        STANDARD(1L),
        COMFORT(2L),
        LUX(3L),
        PRESIDENT(4L);

        private final Long id;

        public Long getId() {
            return id;
        }

        RoomType(Long id) {
            this.id = id;
        }
    }

    public enum Capacity {
        SINGLE(1L),
        DOUBLE(2L),
        TRIPLE(3L),
        FAMILY(4L);

        private final Long id;

        public Long getId() {
            return id;
        }

        Capacity(Long id) {
            this.id = id;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Room room = (Room) o;
        return id != null && Objects.equals(id, room.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", type=" + type +
                ", capacity=" + capacity +
                ", price=" + price +
                ", status=" + status +
                '}';
    }
}