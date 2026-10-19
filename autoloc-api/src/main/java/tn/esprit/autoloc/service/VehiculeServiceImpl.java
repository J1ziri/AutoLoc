package tn.esprit.autoloc.service;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.Utils;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repository.IVehiculeRepository;

@Service
public class VehiculeServiceImpl extends GenericServiceImpl<Vehicule, Long> implements IVehiculeService {

    public VehiculeServiceImpl(IVehiculeRepository repository) {
        super(repository);
    }

    @Override
    protected String getEntityName() {
        return "Vehicule";
    }

    @Override
    protected Long getId(Vehicule entity) {
        return entity.getIdVehicule();
    }

    @Override
    protected void setFields(Vehicule target, Vehicule source) {
        target.setImmatriculation(source.getImmatriculation());
        target.setMarque(source.getMarque());
        target.setModele(source.getModele());
        target.setCategorie(source.getCategorie());
        target.setTarifJournalier(source.getTarifJournalier());
        target.setStatut(source.getStatut());
        target.setAgence(source.getAgence());
    }

    @Override
    public boolean check(Vehicule entity, boolean isNew) {
        if (entity == null) {
            return false;
        }
        if (isNew && entity.getIdVehicule() != null) {
            return false;
        }
        return Utils.isNotEmpty(entity.getImmatriculation()) &&
                Utils.isNotEmpty(entity.getMarque()) &&
                Utils.isNotEmpty(entity.getModele()) &&
                entity.getCategorie() != null &&
                entity.getTarifJournalier() != null &&
                entity.getStatut() != null;
    }
}
