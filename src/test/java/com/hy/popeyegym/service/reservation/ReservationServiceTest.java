package com.hy.popeyegym.service.reservation;

import com.hy.popeyegym.web.repository.reservation.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

//@SpringBootTest
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