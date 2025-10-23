package univer.university.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import univer.university.entity.Category;

import java.util.List;

@Getter
@Setter
public class ReqCategory {
    private String name;
    private List<String> subCategoryNames;
}
