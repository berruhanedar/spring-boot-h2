package com.berru.app.springbooth2.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateGenreRequestDTO {
    @Valid
    //@Valid, bir nesnenin belirli kurallara göre doğruluğunu kontrol eder. Örneğin, bir DTO (Data Transfer Object) sınıfındaki alanlar üzerinde @NotBlank, @Pattern gibi doğrulama anotasyonları kullanıldığında, @Valid bu doğrulama kurallarını tetikler.

    @NotNull(message = "name is mandatory")
    private String name;

}
