package well_tennis_club.projet.config;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import well_tennis_club.projet.exception.FailedAuthException;
import well_tennis_club.projet.exception.IdNotFoundException;
import well_tennis_club.projet.exception.InvalidTokenException;
import well_tennis_club.projet.exception.PasswordNotMatching;
import well_tennis_club.projet.tool.ApiErrorResponse;

import java.sql.SQLException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExcpetionHandler {
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ApiErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
		ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
		apiErrorResponse.setStatus(HttpStatus.CONFLICT.value());
		apiErrorResponse.setMessage("Violation de contrainte d'integrite");
		apiErrorResponse.setDescription("Duplication de clé unique, voir les logs pour plus d'informations");

		return ResponseEntity.status(HttpStatus.CONFLICT).body(apiErrorResponse);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		String errorMessage = ex.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(error -> error.getField() + ": " + error.getDefaultMessage())
				.collect(Collectors.joining("; "));

		ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
		apiErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		apiErrorResponse.setMessage("Échec de la validation");
		apiErrorResponse.setDescription(errorMessage);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiErrorResponse);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ApiErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
		ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
		apiErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		apiErrorResponse.setMessage("Type incompatible");
		apiErrorResponse.setDescription(ex.getName() + " devrait être de type " + ex.getRequiredType().getTypeName());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiErrorResponse);
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ApiErrorResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
		ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
		apiErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		apiErrorResponse.setMessage("Paramètre manquant");
		apiErrorResponse.setDescription("Le paramètre '" + ex.getParameterName() + "' est manquant");

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiErrorResponse);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ApiErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
		ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
		apiErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		apiErrorResponse.setMessage("Erreur de lecture");
		apiErrorResponse.setDescription("Le corps de la requete est mal formé (mauvais type, JSON incorrect,...), voir les logs pour plus d'informations");

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiErrorResponse);
	}

	@ExceptionHandler(SQLException.class)
	public ResponseEntity<ApiErrorResponse> handleSQLException(SQLException ex) {
		ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
		apiErrorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		apiErrorResponse.setMessage("Erreur SQL");
		apiErrorResponse.setDescription("Une erreur SQL est survenue voir les logs pour plus d'informations");

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiErrorResponse);
	}

	@ExceptionHandler(MissingRequestHeaderException.class)
	public ResponseEntity<ApiErrorResponse> handleMissingRequestHeaderException(MissingRequestHeaderException ex) {
		ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
		apiErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		apiErrorResponse.setMessage("Header manquant");
		apiErrorResponse.setDescription("Header manquant dans la requête : " + ex.getHeaderName());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiErrorResponse);
	}

	// ==================== PROJECT EXCEPTIONS ==================== //
	@ExceptionHandler(InvalidTokenException.class)
	public ResponseEntity<ApiErrorResponse> handleInvalidTokenException(InvalidTokenException ex) {
		ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
		apiErrorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
		apiErrorResponse.setMessage("Token invalide");
		apiErrorResponse.setDescription(ex.getMessage());

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiErrorResponse);
	}

	@ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity<ApiErrorResponse> handleIdNotFoundException(IdNotFoundException ex) {
		ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
		apiErrorResponse.setStatus(HttpStatus.NOT_FOUND.value());
		apiErrorResponse.setMessage("Identifiant non trouvé");
		apiErrorResponse.setDescription(ex.getMessage());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiErrorResponse);
	}

	@ExceptionHandler(FailedAuthException.class)
	public ResponseEntity<ApiErrorResponse> handleFailedAuthException(FailedAuthException ex) {
		ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
		apiErrorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
		apiErrorResponse.setMessage("Authentification echouée");
		apiErrorResponse.setDescription(ex.getMessage());

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiErrorResponse);
	}

	@ExceptionHandler(PasswordNotMatching.class)
	public ResponseEntity<ApiErrorResponse> handlePasswordNotMatching(PasswordNotMatching ex) {
		ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
		apiErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		apiErrorResponse.setMessage("Mot de passe incorrect");
		apiErrorResponse.setDescription(ex.getMessage());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiErrorResponse);
	}
}
