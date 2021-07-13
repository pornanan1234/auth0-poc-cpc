package com.pwr.cpc.Controller;

import com.pwr.cpc.Model.CPC;
import com.pwr.cpc.services.CPCservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import java.security.Principal;
import java.util.List;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CPCController {

    @Autowired
    CPCservice cpcService;

    @GetMapping("/cpc")
    private List getAllMovies() {
        return cpcService.getAllCPCs();
    }

    @GetMapping("/cpcs/{id}")
    private CPC getCpc(@PathVariable("id") int id) {
        return cpcService.getCPCById(id);
    }

    @DeleteMapping("/cpcs/{id}")
    private void deleteCPC(@PathVariable("id") int id) {
        cpcService.delete(id);
    }

    @PostMapping("/cpcs")
    private int saveCpc (@RequestBody CPC cpc) {
        cpcService.saveOrUpdate(cpc);
        return cpc.getId();
    }
}

