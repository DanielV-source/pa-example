package es.udc.paproject.backend.model.entities;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface InscriptionDao extends PagingAndSortingRepository<Inscription, Long> {

    Slice<Inscription> findByUserIdOrderByDateDesc(Long userId, Pageable pageable);

    Optional<Inscription> findByUserIdAndEventEventId(Long userId, Long eventID);
}
