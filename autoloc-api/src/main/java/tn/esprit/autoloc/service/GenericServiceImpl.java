package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.exception.ResourceNotFoundException;

import java.util.List;

@RequiredArgsConstructor
public abstract class GenericServiceImpl<T, ID> implements IGenericService<T, ID> {

    protected final JpaRepository<T, ID> repository;

    protected abstract String getEntityName();
    protected abstract ID getId(T entity);
    protected abstract void setFields(T target, T source);

    @Override
    public T create(T entity) {
        if (getId(entity) != null) {
            throw new IllegalArgumentException("L'identifiant doit être null pour une création");
        }
        if (!check(entity, true)) {
            throw new IllegalArgumentException("Les données de l'entité ne sont pas valides");
        }
        return repository.save(entity);
    }

    @Override
    public T findById(ID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(getEntityName(), String.valueOf(id)));
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public T update(ID id, T entity) {
        T existing = findById(id);
        if (!check(entity, false)) {
            throw new IllegalArgumentException("Les données de l'entité ne sont pas valides");
        }
        setFields(existing, entity);
        return repository.save(existing);
    }

    @Override
    public void deleteById(ID id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(getEntityName(), String.valueOf(id));
        }
        repository.deleteById(id);
    }
}
