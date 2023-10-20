package com.fzoo.zoomanagementsystem.controller;

import com.fzoo.zoomanagementsystem.dto.LogRequest;
import com.fzoo.zoomanagementsystem.model.Log;
import com.fzoo.zoomanagementsystem.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/log")
public class LogController {

    private final LogService service;
    @PostMapping(path = "{animalId}")
    public void creatLog(
            @PathVariable("animalId") int id,
            @RequestBody LogRequest request
            ){

        service.add(id,request);
    }


    @GetMapping(path = "{animalId}")
    public List<Log> getLogByAnimal(@PathVariable("animalId") int id){
        return service.showLogByAnimal(id);
    }

}
