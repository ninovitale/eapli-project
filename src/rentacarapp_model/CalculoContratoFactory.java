package rentacarapp_model;

public class CalculoContratoFactory {
    // Singleton
    private static CalculoContratoFactory inst = new CalculoContratoFactory();

    private CalculoContratoFactory() {}

    public static CalculoContratoFactory getInstance() {
        return inst;
    }

    public CalculoContratoStrategy getEstrategiaCalculo(int politicaDesconto) {
            switch (politicaDesconto) {
                case 1: {
                    System.out.format("Aplicando a política de descontos %d%n", politicaDesconto);
                    return new CalculoContrato_Strategy1();
                }
                case 2: {
                    System.out.format("Aplicando a política de descontos %d%n", politicaDesconto);
                    return new CalculoContrato_Strategy2();
                }
                default:
                    return null;
            }
    }
}