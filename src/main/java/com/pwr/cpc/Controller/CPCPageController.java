package com.pwr.cpc.Controller;


import com.pwr.cpc.Model.CPCrepository;
import com.pwr.cpc.Model.CPC;
import com.pwr.cpc.services.CPCservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class CPCPageController {

    private final CPCrepository cpcRepository;

    @Autowired
    private CPCservice cpcservice;

    @Autowired
    public CPCPageController(CPCrepository cpcRepository) {
        this.cpcRepository = cpcRepository;
    }


    @GetMapping("/")
    public String login() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String showCPCList(Model model) {
        model.addAttribute("CPCs",  cpcservice.getAllCPCs());
        return "index";
    }

    @GetMapping("/signup")
    public String showCPCForm(CPC CPC) {
        return "add-cpc";
    }

    @PostMapping("/addcpc")
    public String addCPC(@Valid CPC cpc, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-cpc";
        }

        cpcRepository.save(cpc);
        return "redirect:/index";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        CPC cpc = cpcRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid CPC Id:" + id));
        model.addAttribute("CPC", cpc);

        return "update-cpc";
    }

    @PostMapping("/update/{id}")
    public String updateCPC(@PathVariable("id") int id, @Valid CPC cpc, BindingResult result, Model model) {
        if (result.hasErrors()) {
            cpc.setId(id);
            return "update-cpc";
        }

        cpcRepository.save(cpc);

        return "redirect:/index";
    }

    @GetMapping("/delete/{id}")
    public String deleteCPC(@PathVariable("id") int id, Model model) {
        CPC cpc = cpcRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid CPC Id:" + id));
        cpcRepository.delete(cpc);

        return "redirect:/index";
    }
}