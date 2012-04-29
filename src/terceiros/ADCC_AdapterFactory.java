package terceiros;

import utils.Utils;

/**
 *
 * @author HBK
 */
public class ADCC_AdapterFactory {
    private static ADCC_AdapterFactory instance;

    private ADCC_AdapterFactory() {}

    public static ADCC_AdapterFactory getInstance() {
        if (instance == null)
            instance = new ADCC_AdapterFactory();

        return instance;
    }

    public SistemaADCCAdapter getADCCAdapter() {
        String str = "1 - Canada Express\n";
        str += "2 - Visão Light\n";
        str += "Escolha o sistema adequado: ";
        
        String opcao = Utils.readLineFromConsole(str);

        switch (Integer.parseInt(opcao)) {
            case 1: return new SistemaADCCAdapter_CanadaExpress();
            case 2: return new SistemaADCCAdapter_VisaoLight();
            default: return null;
        }
    }
}
