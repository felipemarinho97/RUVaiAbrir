package ml.darklyn.RUVaiAbrir.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.NO_CONTENT)
public class NoContentException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2217906807256444468L;

	public NoContentException(String message) {
		super(message);
	}

}
