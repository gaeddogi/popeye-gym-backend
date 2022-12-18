package com.hy.popeyegym.domain.trainer;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Trainer {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trainer_id")
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private TrainerType type;

    @Builder(access = AccessLevel.PRIVATE)
    public Trainer(String name, TrainerType type) {
        this.name = name;
        this.type = type;
    }

    /**
     * 생성 메서드
     */
    public static Trainer create(String name, TrainerType type) {
        Trainer trainer = Trainer.builder()
                .name(name)
                .type(type)
                .build();

        return trainer;
    }
}
