package ml.darklyn.RUVaiAbrir.estado;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface EstadoReporitory extends CrudRepository<Estado, Long> {
	
	Page<Estado> getAllEstadoByDate(Date date, Pageable pageable);
}
