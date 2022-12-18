package com.hy.popeyegym.domain.reservation;

import com.hy.popeyegym.domain.trainer.Trainer;
import com.hy.popeyegym.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;
    private LocalDateTime dateTime; // 수업일
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @Builder(access = AccessLevel.PRIVATE)
    private Reservation(User user, Trainer trainer, LocalDateTime dateTime, ReservationStatus status) {
        this.user = user;
        this.trainer = trainer;
        this.dateTime = dateTime;
        this.status = status;
    }

    /*생성 메서드*/
    public static Reservation create(User user, Trainer trainer, LocalDateTime dateTime) {
        return Reservation.builder()
                .user(user)
                .trainer(trainer)
                .dateTime(dateTime)
                .status(ReservationStatus.RESERVATION)
                .build();
    }

    /*비지니스 로직*/
    /**
     * 예약 취소
     */
    public void cancel() {
        status = ReservationStatus.CANCEL;
    }
}
