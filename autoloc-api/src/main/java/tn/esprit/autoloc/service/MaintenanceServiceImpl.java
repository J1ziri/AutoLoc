package tn.esprit.autoloc.service;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.repository.IMaintenanceRepository;

@Service
public class MaintenanceServiceImpl extends GenericServiceImpl<Maintenance, Long> implements IMaintenanceService {

    public MaintenanceServiceImpl(IMaintenanceRepository repository) {
        super(repository);
    }

    @Override
    protected String getEntityName() {
        return "Maintenance";
    }

    @Override
    protected Long getId(Maintenance entity) {
        return entity.getIdMaintenance();
    }

    @Override
    protected void setFields(Maintenance target, Maintenance source) {
        target.setDateDebut(source.getDateDebut());
        target.setDateFin(source.getDateFin());
        target.setDescription(source.getDescription());
        target.setVehicule(source.getVehicule());
    }

    @Override
    public boolean check(Maintenance entity, boolean isNew) {
        if (entity == null) {
            return false;
        }
        if (isNew && entity.getIdMaintenance() != null) {
            return false;
        }
        return entity.getDateDebut() != null;
    }
}
