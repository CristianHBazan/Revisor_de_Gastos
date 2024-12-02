package bazan.revisor_de_gastos.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Months {

    private String id;
    private String name;

    public String toString(){
        return name;
    }
}
