package ru.geekbrains.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.geekbrains.entities.Document;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    Optional<Document> findById(Long id);

    @Query("select d " +
            "from Document d " +
            "where d.object_type=:type_object and d.object_id = :id_object")
    List<Document> findByObject(@Param("type_object") String typeObject, @Param("id_object") Long idObject);

    @Query("select d " +
            "from Document d " +
            "where d.object_type=:type_object and d.object_id = :id_object and (d.title like :name or :name is null) and" +
            "(d.description like :description or :description is null)" +
            "and (d.id=:id or :id is null)")
    List<Document> findByObjectFilter(@Param("type_object") String typeObject, @Param("id_object") Long idObject,
                                      @Param("name") String name, @Param("description") String description, @Param("id") Long id);


}
