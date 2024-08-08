package com.berru.app.springbooth2.controllers;



import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController //Buranın bir controller sınıfı olduğunu belirtmek için kullanıyoruz
@RequestMapping("/genres")

public class GenreController {
    final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @PostMapping
    public Genre save(@RequestBody Genre genre) {
        return genreService.save(genre);
    }

    @GetMapping
    public List<Genre> list() {
        return genreService.list();
    }

    @GetMapping("/{id}")
    public Genre getById(@PathVariable int id) {
        return genreService.getById(id);
    }

    @PutMapping("/{id}")
    public Genre update(@PathVariable int id, @RequestBody Genre genre) {
        return genreService.update(id, genre);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        genreService.delete(id);
    }




}
