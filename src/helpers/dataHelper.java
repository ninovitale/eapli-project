package helpers;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class dataHelper {
    public static int diferencaDatas(Date dataInicio, Date dataFim) {
        GregorianCalendar tempoInicio = new GregorianCalendar();
        GregorianCalendar tempoFim = new GregorianCalendar();

        GregorianCalendar tempoActual = new GregorianCalendar();
        GregorianCalendar tempoBase = new GregorianCalendar();

        tempoInicio.setTime(dataInicio);
        tempoFim.setTime(dataFim);

        int multiplicadorDiferenca;

        // Verifica qual é a "maior" data
        if (dataInicio.compareTo(dataFim) < 0) {
            tempoBase.setTime(dataFim);
            tempoActual.setTime(dataInicio);
            multiplicadorDiferenca = 1;
            
        } else {
            tempoBase.setTime(dataInicio);
            tempoActual.setTime(dataFim);
            multiplicadorDiferenca = -1;
        }

        int resultadoAnos = 0;
        int resultadoMeses = 0;
        int resultadoDias = 0;

        while (tempoActual.get(GregorianCalendar.YEAR) < tempoBase.get(GregorianCalendar.YEAR) ||
                tempoActual.get(GregorianCalendar.MONTH) < tempoBase.get(GregorianCalendar.MONTH)) {

            int diaMaior = tempoActual.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
            resultadoMeses += diaMaior;
            tempoActual.add(GregorianCalendar.MONTH, 1);

        }

        resultadoMeses *= multiplicadorDiferenca;
        resultadoDias += (tempoFim.get(GregorianCalendar.DAY_OF_MONTH) - tempoInicio.get(GregorianCalendar.DAY_OF_MONTH));

        return resultadoAnos + resultadoMeses + resultadoDias;
    }

    public static int diferencaDatas(String dataInicio, String dataFim) {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date inicio, fim;
        
        try {
            inicio = df.parse(dataInicio);
            fim = df.parse(dataFim);

            return diferencaDatas(inicio, fim);
        } catch (ParseException ex) {
            Logger.getLogger(dataHelper.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }
    
    public static boolean validaData(String s) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);
        return sdf.parse(s, new ParsePosition(0)) != null;
    }
    
    // A data é retornaada no formato dd-MM-yyyy
    public static String conveterDataParaString(Date data) {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        return df.format(data);
    }

    public static Date converterStringParaData(String strDate) {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date data;
        
        try {
            data = df.parse(strDate);

            return data;
        } catch (ParseException ex) {
            Logger.getLogger(dataHelper.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
