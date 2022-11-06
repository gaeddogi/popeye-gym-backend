package com.hy.popeyegym.domain.reservation;

import com.hy.popeyegym.domain.trainer.Trainer;
import com.hy.popeyegym.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
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
    private LocalDate trainingDt; // 수업일
    private int startTime;
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @Builder(access = AccessLevel.PRIVATE)
    private Reservation(User user, Trainer trainer, LocalDate trainingDt, int startTime, ReservationStatus status) {
        this.user = user;
        this.trainer = trainer;
        this.trainingDt = trainingDt;
        this.startTime = startTime;
        this.status = status;
    }

    /*생성 메서드*/
    public static Reservation create(User user, Trainer trainer, LocalDate trainingDt, int startTime) {
        return Reservation.builder()
                .user(user)
                .trainer(trainer)
                .trainingDt(trainingDt)
                .startTime(startTime)
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
