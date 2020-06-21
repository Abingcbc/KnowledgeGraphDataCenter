package org.sse.biservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.sse.biservice.model.Entity;
import org.sse.biservice.model.SingerDetail;
import org.sse.biservice.service.ESservice;
import org.sse.biservice.service.RedisService;
import org.sse.biservice.service.RestService;

import java.util.List;

@Slf4j
@RestController
public class SearchController {

    @Autowired
    ESservice eSservice;
    @Autowired
    RestService restService;
    @Autowired
    RedisService redisService;

    @PostMapping("/search/searchResult/{keyword}")
    public List<Entity> searchEntity(@PathVariable String keyword) {
        List<Entity> entities = (List<Entity>) redisService.get("searchResult-"+keyword);
        if (entities != null) {
            log.info("use cache");
            return entities;
        }
        entities = eSservice.searchSinger(keyword);
        redisService.set("searchResult-"+keyword, entities);
        return entities;
    }

    @PostMapping("/search/singerDetail/{name}")
    public SingerDetail getSingerDetail(@PathVariable String name) {
        SingerDetail singerDetail = (SingerDetail) redisService.get("singerDetail-"+name);
        if (singerDetail != null) {
            log.info("use cache");
            return singerDetail;
        }
        singerDetail = restService.requestSingerDetail(name);
        redisService.set("singerDetail-"+name, singerDetail);
        return singerDetail;
    }
}
