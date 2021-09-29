package es.udc.paproject.backend.model.entities;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface SportEventDao extends PagingAndSortingRepository<SportEvent, Long> {


    @Query("SELECT u FROM SportEvent u WHERE" +
            "(?1 IS NULL OR u.province.provinceId = ?1) " +
            "AND (?2 IS NULL OR u.type.typeId = ?2) " +
            "AND (?3 IS NULL OR u.date >= ?3) " +
            "AND (?4 IS NULL OR u.date <= ?4) " +
            "ORDER BY u.date DESC")
    Slice<SportEvent> findSportEvents(Long provinceId, Long typeId, LocalDate date1, LocalDate date2, Pageable pageable);
}
