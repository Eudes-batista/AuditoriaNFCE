package teste;

import java.util.Calendar;
import java.util.Date;

public class IcmsTeste {

    public static void main(String[] args) {

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        int dia = c.get(Calendar.DATE);
        int mes = c.get(Calendar.MONTH) + 1;
        int ano = c.get(Calendar.YEAR);
        
        System.out.println("ano = " + ano);
        System.out.println("dia = " + dia);
        System.out.println("mes = " + mes);
        
    }

}
