package bazan.revisor_de_gastos.services;

import bazan.revisor_de_gastos.models.Expense;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ExpenseService {

    List<Expense> getExpenses(String month, String year);
    List<Expense> getTop10(String year);

    String getDailyTotal(String month, String year);
    String getYearlyTotal(String year);

    void toSave(Expense expense);
    void toDelete(Expense expense);
    void toModify(Expense expense, Long id);
}
