package org.lessons.java.pizzeriacurd.spring_la_mia_pizzeria_crud.controller;

import org.lessons.java.pizzeriacurd.spring_la_mia_pizzeria_crud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.lessons.java.pizzeriacurd.spring_la_mia_pizzeria_crud.model.Pizza;
import java.util.List;

@Controller
@RequestMapping("/pizzas")
public class PizzaController {

    @Autowired
    private PizzaRepository repository;

    @GetMapping
    public String index(@RequestParam(name = "keyword", required = false) String keyword, Model model) {

        // con questo comando è come fare una query SELECT * from pizzas e li trasforma
        // in una lista di tipo Pizza
        List<Pizza> pizzas;
        if (keyword != null) {
            pizzas = repository.findByNameContainingIgnoreCase(keyword);
        } else {
            pizzas = repository.findAll();
        }
        model.addAttribute("pizzas", pizzas);
        model.addAttribute("keyword", keyword);

        return "pizzas/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {

        // con questo comando è come fare una query SELECT * from pizzas where "id" =id
        Pizza pizza = repository.findById(id).get();

        if (pizza == null) {
            return "rederict:/pizzas/index";
        }
        model.addAttribute("pizza", pizza);

        return "pizzas/detail";
    }
}