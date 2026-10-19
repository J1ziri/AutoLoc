package tn.esprit.autoloc.service;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.repository.IContratRepository;

@Service
public class ContratServiceImpl extends GenericServiceImpl<Contrat, Long> implements IContratService {

    public ContratServiceImpl(IContratRepository repository) {
        super(repository);
    }

    @Override
    protected String getEntityName() {
        return "Contrat";
    }

    @Override
    protected Long getId(Contrat entity) {
        return entity.getIdContrat();
    }

    @Override
    protected void setFields(Contrat target, Contrat source) {
        target.setDateSignature(source.getDateSignature());
        target.setMontantTotal(source.getMontantTotal());
        target.setValide(source.isValide());
    }

    @Override
    public boolean check(Contrat entity, boolean isNew) {
        if (entity == null) {
            return false;
        }
        if (isNew && entity.getIdContrat() != null) {
            return false;
        }
        return entity.getDateSignature() != null && entity.getMontantTotal() != null;
    }
}
