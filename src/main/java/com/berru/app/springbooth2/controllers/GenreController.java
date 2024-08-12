package com.berru.app.springbooth2.controllers;

//İstemci tarafından gelen ilk requesti algılayan katman controllerdir


import com.berru.app.springbooth2.entities.Genre;
import com.berru.app.springbooth2.services.GenreService;
import org.springframework.http.ResponseEntity; //Bu satır, HTTP yanıtlarını kapsülleyen ResponseEntity sınıfını projeye dahil eder. Bu sınıf, HTTP yanıtını (status code, body, headers) temsil eder ve HTTP isteklerine yanıt verirken kullanılır.
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController //Buranın bir controller sınıfı olduğunu belirtmek için kullanıyoruz
@RequestMapping("/genres") // Oluşturduğumuz url 'de farklılık olması için ekliyoruz

public class GenreController {
    final GenreService genreService;

    // Bu metot, sınıfın bir örneği oluşturulduğunda çağrılır. genreService türündeki bir nesneyi alır ve bu nesneyi sınıfın genreService değişkenine atar. Bu sayede, GenreService sınıfındaki metotlar GenreController sınıfı içinde kullanılabilir.
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @PostMapping
    public ResponseEntity<Genre> save(@RequestBody Genre genre) {
        return genreService.save(genre);
    }

    @GetMapping
    public ResponseEntity<List<Genre>> list() {
        return genreService.list();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genre> getById(@PathVariable int id) {
        return genreService.getById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Genre> update(@PathVariable int id, @RequestBody Genre genre) {
        return genreService.update(id, genre);
    }

    public ResponseEntity<Void> delete(@PathVariable int id) {
        return genreService.delete(id);
    }




}
