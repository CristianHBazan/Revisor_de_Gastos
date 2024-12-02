package bazan.revisor_de_gastos.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class Expense {

    private Long id;

    private String reason;

    private BigDecimal amount;

    private String date;

    public String toString(){
        return date + "    |    $" + amount + "    |    " + reason;
    }
}
