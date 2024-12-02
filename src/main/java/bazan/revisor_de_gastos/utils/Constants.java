package bazan.revisor_de_gastos.utils;

import bazan.revisor_de_gastos.models.Months;

import java.util.ArrayList;
import java.util.List;

public class Constants {

    static public List<Months> monthsOfYear = new ArrayList<>();
    static {
        monthsOfYear.add(new Months("01", "Enero"));
        monthsOfYear.add(new Months("02", "Febrero"));
        monthsOfYear.add(new Months("03", "Marzo"));
        monthsOfYear.add(new Months("04", "Abril"));
        monthsOfYear.add(new Months("05", "Mayo"));
        monthsOfYear.add(new Months("06", "Junio"));
        monthsOfYear.add(new Months("07", "Julio"));
        monthsOfYear.add(new Months("08", "Agosto"));
        monthsOfYear.add(new Months("09", "Septiembre"));
        monthsOfYear.add(new Months("10", "Octubre"));
        monthsOfYear.add(new Months("11", "Noviembre"));
        monthsOfYear.add(new Months("12", "Diciembre"));
    }

}
