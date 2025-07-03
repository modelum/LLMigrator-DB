package reservas.infraestructura.rabbitMQ;

import reservas.dominio.Reserva;

public interface PublicadorEventos {
    void publicarCreacionReserva(Reserva reserva) throws Exception;
}
