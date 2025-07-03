package eventos.infraestructura.rabbitMQ;

import eventos.dominio.Evento;

public interface PublicadorEventos {
  void publicarEventoCreacion(Evento evento);
  void publicarEventoModificacion(Evento evento);
  void publicarEventoCancelado(String entidadId);
}
