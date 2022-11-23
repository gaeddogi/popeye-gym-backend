package com.hy.popeyegym.service.reservation;

import com.hy.popeyegym.domain.reservation.Reservation;
import com.hy.popeyegym.domain.trainer.Trainer;
import com.hy.popeyegym.domain.user.User;
import com.hy.popeyegym.repository.reservation.ReservationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReservationServiceTest {

    @Autowired
    ReservationRepository reservationRepository;
    @PersistenceContext
    EntityManager em;


   /* @Test
    @Description("트레이너가 해당 날짜에 예약이 있는지 확인")
    public void 예약건_있는지_조회() throws Exception {
        //given
        Trainer trainer = new Trainer("test");
        em.persist(trainer);

        Reservation reservation = Reservation.create()
        reservationRepository.
        //when

        //then
    }*/

}