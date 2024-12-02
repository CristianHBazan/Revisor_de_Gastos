package bazan.revisor_de_gastos.repositories;

import bazan.revisor_de_gastos.entities.ExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {

    @Query("SELECT e FROM ExpenseEntity e WHERE e.date LIKE :datePattern ORDER BY e.date")
    List<ExpenseEntity> findAllByDatePattern(@Param("datePattern") String datePattern);

    List<ExpenseEntity> findTop10ByDateLikeOrderByAmountDesc(String date);
}
