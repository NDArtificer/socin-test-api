package br.com.socin.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.socin.domain.exception.BusinessException;
import br.com.socin.domain.exception.EntityNotFoundException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		var detail = "One or more properties are invalid, correct them and try again!";
		Problem problem = createProblem(ex, status, detail);
		List<Problem.Field> fields = new ArrayList<>();

		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			String name = ((FieldError) error).getField();
			String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());

			fields.add(new Problem.Field(name, message));
		}
		problem.setFields(fields);

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<Object> handleBusinessExecption(BusinessException ex, WebRequest request) {

		HttpStatus status = HttpStatus.BAD_REQUEST;
		var detail = ex.getMessage();
		Problem problem = createProblem(ex, status, detail);

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {

		HttpStatus status = HttpStatus.NOT_FOUND;
		var detail = ex.getMessage();
		Problem problem = createProblem(ex, status, detail);

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	private Problem createProblem(Exception ex, HttpStatus status, String detail) {

		var problem = new Problem();
		problem.setStatus(status.value());
		problem.setTimeStamp(OffsetDateTime.now());
		problem.setTitle(detail);

		return problem;
	}

}
