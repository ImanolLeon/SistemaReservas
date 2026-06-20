package com.Reservas.SistemaReservas.excepcion;

public class MensajesExcepction {
    private MensajesExcepction() {
    }

    public static final String USUARIO_NO_ENCONTRADO = "El usuario con  id : %s no se ha encontrado";
    public static final String  DATO_INVALIDO = "El dato es invalido : %s";
    public static final String VALOR_NO_ENCONTRADO = "Este valor no ha sido encontrado en la base de datos : %s";
    public static  final String RESERVA_NO_ENCONTRADA = "La reserva no es valida con el id : %s";
    public static final String CRUCE_HORARIO = "Hay cruce de horarios : %s";
}
