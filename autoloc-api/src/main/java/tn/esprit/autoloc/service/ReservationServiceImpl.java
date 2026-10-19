package tn.esprit.autoloc.service;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.repository.IReservationRepository;

@Service
public class ReservationServiceImpl extends GenericServiceImpl<Reservation, Long> implements IReservationService {

    public ReservationServiceImpl(IReservationRepository repository) {
        super(repository);
    }

    @Override
    protected String getEntityName() {
        return "Reservation";
    }

    @Override
    protected Long getId(Reservation entity) {
        return entity.getIdReservation();
    }

    @Override
    protected void setFields(Reservation target, Reservation source) {
        target.setDateDebut(source.getDateDebut());
        target.setDateFin(source.getDateFin());
        target.setStatut(source.getStatut());
        target.setVehicule(source.getVehicule());
        target.setClient(source.getClient());
        target.setContrat(source.getContrat());
    }

    @Override
    public boolean check(Reservation entity, boolean isNew) {
        if (entity == null) {
            return false;
        }
        if (isNew && entity.getIdReservation() != null) {
            return false;
        }
        return entity.getDateDebut() != null &&
                entity.getDateFin() != null &&
                entity.getStatut() != null;
    }
}
