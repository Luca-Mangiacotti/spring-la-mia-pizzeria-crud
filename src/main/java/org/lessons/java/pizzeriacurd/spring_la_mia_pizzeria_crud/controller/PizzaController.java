package org.lessons.java.pizzeriacurd.spring_la_mia_pizzeria_crud.controller;

import org.lessons.java.pizzeriacurd.spring_la_mia_pizzeria_crud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.lessons.java.pizzeriacurd.spring_la_mia_pizzeria_crud.model.Pizza;
import java.util.List;

@Controller
@RequestMapping("/pizzas")
public class PizzaController {

    @Autowired
    private PizzaRepository repository;

    // INDEX DEI PRODOTTI (INDEX)

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

    // DETTAGLIO DI UN PRODOTTO (SHOW)

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {

        // con questo comando è come fare una query SELECT * from pizzas where "id" =id
        Pizza pizza = repository.findById(id).get();

        if (pizza == null) {
            return "rederict:/pizzas";
        }
        model.addAttribute("pizza", pizza);

        return "pizzas/detail";
    }

    // AGGIUNTA DI UN NUOVO PRODOTTO (CREATE)

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("pizza", new Pizza());
        return "pizzas/create";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("pizza") Pizza pizzaForm, BindingResult bindingResult, Model model) {
        // utilizziamo il bindingResult per far verificare al validatore se ci sono
        // stati errori di inserimento nel form
        // in caso ci fossero ci sarà un indirizzamento allo stesso form
        if (bindingResult.hasErrors()) {
            return "pizzas/create";
        }

        // nel caso non ci siano errori possiamo salvare il nostro prodotto tramite la
        // repository con il comando .save
        repository.save(pizzaForm);
        return "redirect:/pizzas";

    }

    // MODIFICA DI UN PRODOTTO (UPDATE)

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("pizza", repository.findById(id).get());
        return "pizzas/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("pizza") Pizza pizzaForm, BindingResult bindingResult, Model model) {
        // utilizziamo il bindingResult per far verificare al validatore se ci sono
        // stati errori di inserimento nel form
        // in caso ci fossero ci sarà un indirizzamento allo stesso form
        if (bindingResult.hasErrors()) {
            return "pizzas/edit";
        }

        // nel caso non ci siano errori possiamo salvare il nostro prodotto tramite la
        // repository con il comando .save
        repository.save(pizzaForm);
        return "redirect:/pizzas";

    }
}