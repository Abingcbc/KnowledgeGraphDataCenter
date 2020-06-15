package org.sse.kgdatacenter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api")
public class DataController {

    @GetMapping("/one")
    public void getOneEntityRelations() {
    }

    @GetMapping("/two")
    public void getTwoEntityRelations() {

    }
}
