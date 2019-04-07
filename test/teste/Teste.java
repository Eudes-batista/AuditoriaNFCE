package teste;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class Teste {

    public static void main(String[] args) {
        
        DecimalFormat decimalFormat = new DecimalFormat("#####0.00");
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator('.');
        decimalFormat.setDecimalFormatSymbols(decimalFormatSymbols);
        
        System.out.println(decimalFormat.format(15));

    }
}
