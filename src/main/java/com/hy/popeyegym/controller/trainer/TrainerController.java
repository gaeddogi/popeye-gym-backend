package com.hy.popeyegym.controller.trainer;

import com.hy.popeyegym.service.trainer.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class TrainerController {

    private final TrainerService service;


}
