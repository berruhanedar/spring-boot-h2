package com.berru.app.springbooth2.controller;

//İstemci tarafından gelen ilk requesti algılayan katman controllerdir


import com.berru.app.springbooth2.dto.DeleteResponseDTO;
import com.berru.app.springbooth2.dto.GenreDTO;
import com.berru.app.springbooth2.dto.NewGenreRequestDTO;
import com.berru.app.springbooth2.dto.UpdateGenreRequestDTO;
import com.berru.app.springbooth2.entity.Genre;
import com.berru.app.springbooth2.service.GenreService;
import jakarta.validation.Valid;
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
    public ResponseEntity<GenreDTO> save(@RequestBody @Valid NewGenreRequestDTO newGenreRequestDTO) {
        return genreService.save(newGenreRequestDTO);
    }


    @GetMapping
    public ResponseEntity<List<Genre>> list() {
        return genreService.list();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenreDTO> getById(@PathVariable int id) {
        return genreService.getById(id);
    }
    @PutMapping("/{id}")
    public ResponseEntity<GenreDTO> update(@PathVariable int id,
                                           @RequestBody @Valid UpdateGenreRequestDTO updateGenreRequestDTO) {
        return genreService.update(id, updateGenreRequestDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResponseDTO> delete(@PathVariable int id) {
        return genreService.delete(id);
    }




}
