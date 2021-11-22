package ai.ecma.appticketserver.entity;

import ai.ecma.appticketserver.entity.template.AbsEntity;
import ai.ecma.appticketserver.enums.SeatStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"event_session_id", "seat_name", "row", "section"}))
public class Ticket extends AbsEntity {

    @Column(name = "seat_name")
    private String seatName;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "event_session_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private EventSession eventSession;

    @Column(name = "status_enum")
    @Enumerated(EnumType.STRING)
    private SeatStatusEnum statusEnum;

    @Column(name = "booked_time")
    private Timestamp bookedTime;

    @Column(name = "sold_time")
    private Timestamp soldTime;

    private int row;
    private String section;

    public Ticket(String seatName, User user, EventSession eventSession, SeatStatusEnum statusEnum, Timestamp bookedTime, Timestamp soldTime, int row, String section, Double price, boolean ticketUsed) {
        this.seatName = seatName;
        this.user = user;
        this.eventSession = eventSession;
        this.statusEnum = statusEnum;
        this.bookedTime = bookedTime;
        this.soldTime = soldTime;
        this.row = row;
        this.section = section;
        this.price = price;
        this.ticketUsed = ticketUsed;
    }

    private Double price;

    @Column(name = "ticked_used")
    private boolean ticketUsed;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Payment payment;
}
