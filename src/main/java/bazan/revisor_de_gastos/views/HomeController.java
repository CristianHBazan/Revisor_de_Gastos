package bazan.revisor_de_gastos.views;

import bazan.revisor_de_gastos.models.Expense;
import bazan.revisor_de_gastos.models.Months;
import bazan.revisor_de_gastos.services.ExpenseService;
import bazan.revisor_de_gastos.utils.Constants;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

@Component
public class HomeController implements Initializable {

    private String month;
    private String year;
    private Expense selectedExpense = new Expense();

    @Autowired
    private ExpenseService expenseService;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ListView<Expense> lstDay, lstTop10;

    @FXML
    private ListView<Months> lstMonths;

    @FXML
    private TextField txtReason, txtAmount, txtDate;

    @FXML
    private Button btnSave, btnDelete, btnClean, btnModify, btnInfo;

    @FXML
    private Label lblDailyTotal, lblDateMonth, lblWarning, lblDateYear, lblYearlyTotal;

    @FXML
    private Pane paneImg;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        LocalDate today = LocalDate.now();
        DecimalFormat decimalFormat = new DecimalFormat("00");
        month = decimalFormat.format(today.getMonthValue());
        year = String.valueOf(today.getYear());

        generalUpdate();
        chargeListMonths();


        lstDay.getSelectionModel().selectedItemProperty()
                .addListener((obs, old, newValue) ->{
                    if (newValue != null){
                        selectedExpense.setId(newValue.getId());
                        selectedExpense.setReason(newValue.getReason());
                        selectedExpense.setAmount(newValue.getAmount());
                        selectedExpense.setDate(newValue.getDate());
                        autocomplete();
                    }
        });

        lstMonths.getSelectionModel().selectedItemProperty()
                .addListener((obs, old, newValue) ->{
                    if (newValue != null){
                        month = newValue.getId();
                        clean();
                        updateDialy();
                    }
                });
    }

    @FXML
    private void aboutMe(){

        if (paneImg.isVisible()){

            paneImg.setVisible(false);
        } else {

            paneImg.setVisible(true);
        }

    }

    private void generalUpdate(){

        chargeTop10();
        updateDialy();
        lblYearlyTotal.setText(expenseService.getYearlyTotal(year));
        lblDateYear.setText(year);
    }

    @FXML
    private void modify(){

        if(checkFields()){

            Expense expense = new Expense();
            expense.setReason(txtReason.getText().trim());
            expense.setDate(txtDate.getText().trim());
            Double amount = Double.valueOf(txtAmount.getText().trim());
            expense.setAmount(BigDecimal.valueOf(amount));

            expenseService.toModify(expense, selectedExpense.getId());
            clean();
            generalUpdate();
        }
    }

    private void chargeTop10(){
        lstTop10.setItems(FXCollections.observableArrayList(expenseService.getTop10(year)));
    }

    @FXML
    private void plusYear(){

        year = String.valueOf(Integer.parseInt(year)+1);
        generalUpdate();
    }

    @FXML
    private void minusYear(){

        year = String.valueOf(Integer.parseInt(year)-1);
        generalUpdate();
    }

    @FXML
    private void delete(){

        expenseService.toDelete(selectedExpense);
        clean();
        generalUpdate();
    }

    private void chargeListMonths(){

        lstMonths.setItems(FXCollections.observableArrayList(Constants.monthsOfYear));
    }

    private void autocomplete() {

        clean();
        txtReason.setText(selectedExpense.getReason());
        txtAmount.setText(selectedExpense.getAmount().toString());
        txtDate.setText(selectedExpense.getDate());
        btnClean.setText("Nuevo");
        btnDelete.setVisible(true);
        btnSave.setVisible(false);
        btnModify.setVisible(true);
    }

    public void updateDialy(){

        lstDay.setItems(FXCollections.observableArrayList(expenseService.getExpenses(month, year)));
        lblDailyTotal.setText(expenseService.getDailyTotal(month, year));
        lblDateMonth.setText(Constants.monthsOfYear.get(Integer.parseInt(month) - 1) +"/"+year);
    }

    @FXML
    public void save(){

        if(checkFields()){

            Expense expense = new Expense();
            expense.setReason(txtReason.getText().trim());
            expense.setDate(txtDate.getText().trim());
            Double amount = Double.valueOf(txtAmount.getText().trim());
            expense.setAmount(BigDecimal.valueOf(amount));

            expenseService.toSave(expense);
            clean();
            generalUpdate();
        }
    }

    public boolean checkFields(){

        if (!txtReason.getText().trim().isEmpty() && !txtAmount.getText().trim().isEmpty() && !txtDate.getText().trim().isEmpty()){

            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                formatter.parse(txtDate.getText().trim());
            } catch (DateTimeParseException e) {

                lblWarning.setVisible(true);
                return false;
            }

            try {
                Double amount = Double.valueOf(txtAmount.getText().trim());
            } catch (Error e){

                lblWarning.setVisible(true);
                return false;
            }
            return true;
        }
        lblWarning.setVisible(true);
        return false;
    }

    @FXML
    public void clean(){

        txtReason.clear();
        txtAmount.clear();
        txtDate.clear();
        lblWarning.setVisible(false);
        btnDelete.setVisible(false);
        btnClean.setText("Limpiar");
        btnModify.setVisible(false);
        btnSave.setVisible(true);
    }
}
