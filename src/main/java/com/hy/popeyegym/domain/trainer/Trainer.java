package com.hy.popeyegym.domain.trainer;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Trainer {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trainer_id")
    private Long id;
    private String name;

}
