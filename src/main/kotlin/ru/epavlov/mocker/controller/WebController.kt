package ru.epavlov.mocker.controller

import org.springframework.core.annotation.Order
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.ModelAndView

@Order(1)
@Controller
class WebController{

//    @GetMapping()
//    fun mainPage(): ModelAndView{
//        return ModelAndView("index")
//    }
}