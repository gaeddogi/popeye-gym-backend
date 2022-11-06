package com.hy.popeyegym.domain.pt;

import com.hy.popeyegym.domain.trainer.Trainer;
import com.hy.popeyegym.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jdt.internal.compiler.ast.EqualExpression;

import javax.persistence.*;
@Slf4j
@Entity
@Getter
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
public class Pt {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pt_id")
    private Long id;

    private int quantity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    @Builder(access = AccessLevel.PRIVATE)
    private Pt(int quantity, User user, Trainer trainer) {
        this.quantity = quantity;
        this.user = user;
        this.trainer = trainer;
    }

    /*생성 메서드*/
    public static Pt create(User user, Trainer trainer, int quantity) {
        return Pt.builder()
                .quantity(quantity)
                .user(user)
                .trainer(trainer)
                .build();
    }

    /*비지니스 로직*/
    public void addQuantity(int times) {
        this.quantity += times;
    }

    /**
     * pt 예약
     *
     * quantity가 있으면 -1 한 뒤에 -> true 반환
     * 남은 quantity 없으면 -> false 반환
     */
    public boolean minus() {
        if (quantity <= 0) {
            return false;
        }
        --quantity;
        return true;
    }

    /**
     * pt 예약 취소
     */
    public void cancel() {
        ++quantity;
    }
}
