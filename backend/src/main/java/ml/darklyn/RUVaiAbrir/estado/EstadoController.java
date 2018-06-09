package ml.darklyn.RUVaiAbrir.estado;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EstadoController {
	
	@RequestMapping(value="/estado", method=RequestMethod.POST)
	public Estado adicionarEstado() {
		return null;
	}

}
