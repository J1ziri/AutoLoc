package tn.esprit.autoloc.service;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.Utils;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.repository.IEquipementRepository;

@Service
public class EquipementServiceImpl extends GenericServiceImpl<Equipement, Long> implements IEquipementService {

    public EquipementServiceImpl(IEquipementRepository repository) {
        super(repository);
    }

    @Override
    protected String getEntityName() {
        return "Equipement";
    }

    @Override
    protected Long getId(Equipement entity) {
        return entity.getIdEquipement();
    }

    @Override
    protected void setFields(Equipement target, Equipement source) {
        target.setLibelle(source.getLibelle());
    }

    @Override
    public boolean check(Equipement entity, boolean isNew) {
        if (entity == null) {
            return false;
        }
        if (isNew && entity.getIdEquipement() != null) {
            return false;
        }
        return Utils.isNotEmpty(entity.getLibelle());
    }
}
