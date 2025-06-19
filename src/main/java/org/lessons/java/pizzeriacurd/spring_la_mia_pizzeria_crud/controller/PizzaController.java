package org.lessons.java.pizzeriacurd.spring_la_mia_pizzeria_crud.controller;

import org.lessons.java.pizzeriacurd.spring_la_mia_pizzeria_crud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.lessons.java.pizzeriacurd.spring_la_mia_pizzeria_crud.model.Pizza;
import java.util.List;

@Controller
@RequestMapping("/pizzas")
public class PizzaController {

    @Autowired
    private PizzaRepository repository;

    @GetMapping("/index")
    public String index(Model model) {

        // con questo comando è come fare una query SELECT * from pizzas e li trasforma
        // in una lista di tipo Pizza
        List<Pizza> pizzas = repository.findAll();
        model.addAttribute("pizzas", pizzas);

        return "pizzas/index";
    }
}