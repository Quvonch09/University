package univer.university.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import univer.university.entity.Category;

@Getter
@Setter
public class ReqCategory {
    private String name;
    @NotBlank
    private Category subCategory;
}
