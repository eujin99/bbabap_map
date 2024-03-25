package com.example.kakaomaptest.controller;

import com.example.kakaomaptest.Charge;
import com.example.kakaomaptest.service.ChargeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ChargeController {

    private ChargeService chargeService;

    public ChargeController(ChargeService chargeService) {
        this.chargeService = chargeService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/listCharge")
    public String viewChargeList(@RequestParam(required = false) String searchType,
                                 @RequestParam(required = false) String searchKeyword,
                                 Model model) {
        List<Charge> charges;
        if(searchType != null && searchKeyword != null && !searchKeyword.isEmpty()) {
            charges = chargeService.searchCharges(searchType, searchKeyword);
        } else {
            charges = chargeService.getAllCharges();
        }
        model.addAttribute("allCharges", charges);
        return "listCharge";
    }


    @GetMapping("/viewCharge/{id}")
    public String viewCharge(@PathVariable("id") String id, Model model) {
        Charge charge = chargeService.getChargeById(id);
        model.addAttribute("charge", charge);

        return "viewCharge";
    }

    @GetMapping("/addViewCharge")
    public String addViewCharge() {
        return "addViewCharge";
    }

    @PostMapping("/addCharge")
    public String addCharge(@ModelAttribute Charge charge) {
        chargeService.saveCharge(charge);

        return "redirect:/listCharge";
    }

    @GetMapping("/updateViewCharge/{id}")
    public String updateViewCharge(@PathVariable("id") String id, Model model) {
        Charge charge = chargeService.getChargeById(id);
        model.addAttribute("charge", charge);

        return "updateViewCharge";
    }

    @PostMapping("/updateCharge/{id}")
    public String updateCharge(@PathVariable("id") String id, @ModelAttribute Charge charge) {
        chargeService.updateCharge(charge);

        return "redirect:/listCharge";
    }

    @GetMapping("/deleteCharge/{id}")
    public String deleteCharge(@PathVariable("id") String id) {
        chargeService.deleteCharge(id);

        return "redirect:/listCharge";
    }

}
