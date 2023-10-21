package com.fzoo.zoomanagementsystem.controller;

import com.fzoo.zoomanagementsystem.dto.LogRequest;
import com.fzoo.zoomanagementsystem.model.Log;
import com.fzoo.zoomanagementsystem.service.LogService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/log")
public class LogController {

    private final LogService service;

    @Operation(
            summary = "Create a log",
            description = "Create a log by animalID "
    )
    @PostMapping(path = "{animalId}")
    public void creatLog(
            @PathVariable("animalId") int id,
            @RequestBody LogRequest request
            ){

        service.add(id,request);
    }


    @Operation(
            summary = "List a log",
            description = "List a log by animalID"
    )
    @GetMapping(path = "{animalId}")
    public List<Log> getLogByAnimal(@PathVariable("animalId") int id){
        return service.showLogByAnimal(id);
    }

}
