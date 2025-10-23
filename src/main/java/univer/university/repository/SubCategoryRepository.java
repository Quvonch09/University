package univer.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import univer.university.entity.Category;
import univer.university.entity.SubCategory;

import java.util.List;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    boolean existsByName(String name);

    List<SubCategory> findAllByCategoryId(Long id);
}
