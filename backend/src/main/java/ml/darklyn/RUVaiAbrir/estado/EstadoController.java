package ml.darklyn.RUVaiAbrir.estado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EstadoController {
	
	@Autowired
	private EstadoService estadoService;
	
	@RequestMapping(value="/estado", method=RequestMethod.POST)
	public Estado adicionarEstado() {
		return null;
	}
	
	@RequestMapping(value="/estados", method=RequestMethod.GET )
	public Page<Estado> getAllTodayEstados(
			@RequestParam(name="page", defaultValue = "0", required = false) Integer page,
			@RequestParam(name="limit", defaultValue = "20", required = false) Integer limit) {
		
		Pageable pageable = PageRequest.of(page, limit);
		
		Page<Estado> pages = estadoService.getAllTodayEstados(pageable);
		
		return pages;
	}

}
