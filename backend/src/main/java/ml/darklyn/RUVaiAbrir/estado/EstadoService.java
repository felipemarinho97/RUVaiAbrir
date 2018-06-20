package ml.darklyn.RUVaiAbrir.estado;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EstadoService {
	
	@Autowired
	private EstadoReporitory estadoRepository;
	
	public Estado addEstado(Estado estado) {
		return estadoRepository.save(estado);
	}

	public Page<Estado> getAllTodayEstados(Pageable pageable) {
		return estadoRepository.getAllEstadoByDate(new Date(), pageable);
	}
	
	

}
