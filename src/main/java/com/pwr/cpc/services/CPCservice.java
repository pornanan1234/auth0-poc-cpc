package com.pwr.cpc.services;


import java.util.ArrayList;
import java.util.List;
import com.pwr.cpc.Model.CPC;
import com.pwr.cpc.Model.CPCrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CPCservice {

    @Autowired
     CPCrepository  cpcRepository;

    public List getAllCPCs() {
        List CPCs = new ArrayList();
        cpcRepository.findAll().forEach(cpc -> CPCs.add(cpc));
        return CPCs;
    }

    public CPC getCPCById(int id) {
        return cpcRepository.findById(id).get();
    }



    public void saveOrUpdate(CPC cpc) {
        cpcRepository.save(cpc);
    }

    public void delete(int id) {
        cpcRepository.deleteById(id);
    }
}
