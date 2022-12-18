package com.hy.popeyegym.web.domain.enroll;

import com.hy.popeyegym.web.domain.trainer.Trainer;
import com.hy.popeyegym.web.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Enroll {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enroll_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="trainer_id")
    private Trainer trainer;
    private LocalDateTime enrollDt;
    private int times; // 등록pt횟수

    @Builder(access = AccessLevel.PRIVATE)
    private Enroll(User user, Trainer trainer, LocalDateTime enrollDt, int times
    ) {
        this.user = user;
        this.trainer = trainer;
        this.enrollDt = enrollDt;
        this.times = times;
    }


    /**
     * 생성 메서드
     */
    public static Enroll create(User user, Trainer trainer, int times) {
        Enroll enroll = Enroll.builder()
                .enrollDt(LocalDateTime.now())
                .trainer(trainer)
                .user(user)
                .times(times)
                .build();
        return enroll;
    }
}
