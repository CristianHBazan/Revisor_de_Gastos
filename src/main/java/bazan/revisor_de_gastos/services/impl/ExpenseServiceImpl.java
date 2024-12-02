package bazan.revisor_de_gastos.services.impl;

import bazan.revisor_de_gastos.entities.ExpenseEntity;
import bazan.revisor_de_gastos.models.Expense;
import bazan.revisor_de_gastos.repositories.ExpenseRepository;
import bazan.revisor_de_gastos.services.ExpenseService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Expense> getExpenses(String month, String year) {

        String date = "__/" + month + "/" + year;
        List<ExpenseEntity> expenseEntityList = expenseRepository.findAllByDatePattern(date);

        List<Expense> expenseList = new ArrayList<>();
        expenseEntityList.forEach(expenseEntity -> expenseList.add(modelMapper.map(expenseEntity, Expense.class)));
        return expenseList;
    }

    @Override
    public List<Expense> getTop10(String year) {

        String date = "__/__/" + year;
        List<ExpenseEntity> expenseEntityList = expenseRepository.findTop10ByDateLikeOrderByAmountDesc(date);


        List<Expense> expenseList = new ArrayList<>();
        expenseEntityList.forEach(expenseEntity -> expenseList.add(modelMapper.map(expenseEntity, Expense.class)));
        return expenseList;
    }

    @Override
    public String getDailyTotal(String month, String year) {

        String date = "__/" + month + "/" + year;
        List<ExpenseEntity> expenseEntityList = expenseRepository.findAllByDatePattern(date);

        BigDecimal total = BigDecimal.ZERO;
        for (ExpenseEntity expenseEntity : expenseEntityList){

            total = total.add(expenseEntity.getAmount());
        }
        return "$" + total.toString();
    }

    @Override
    public String getYearlyTotal(String year) {

        String date = "__/__/" + year;
        List<ExpenseEntity> expenseEntityList = expenseRepository.findAllByDatePattern(date);

        BigDecimal total = BigDecimal.ZERO;
        for (ExpenseEntity expenseEntity : expenseEntityList){

            total = total.add(expenseEntity.getAmount());
        }
        return "$" + total.toString();
    }

    @Override
    public void toSave(Expense expense) {

        ExpenseEntity expenseEntity = modelMapper.map(expense, ExpenseEntity.class);
        ExpenseEntity expenseEntitySaved = expenseRepository.save(expenseEntity);
    }

    @Override
    public void toDelete(Expense expense) {

        ExpenseEntity expenseEntity = modelMapper.map(expense, ExpenseEntity.class);
        expenseRepository.delete(expenseEntity);
    }

    @Override
    @Transactional
    public void toModify(Expense expense, Long id) {

        Optional<ExpenseEntity> expenseToFind = expenseRepository.findById(id);

        if (expenseToFind.isPresent()){

            ExpenseEntity expenseFinded = expenseToFind.get();
            expenseFinded.setReason(expense.getReason());
            expenseFinded.setAmount(expense.getAmount());
            expenseFinded.setDate(expense.getDate());
            expenseRepository.save(expenseFinded);
        }
    }
}
