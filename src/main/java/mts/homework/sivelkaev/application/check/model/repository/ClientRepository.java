package mts.homework.sivelkaev.application.check.model.repository;

import mts.homework.sivelkaev.application.check.model.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true, noRollbackFor = Exception.class)
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
    Optional<ClientEntity> findByPassportNumber(String passportNumber);
}
