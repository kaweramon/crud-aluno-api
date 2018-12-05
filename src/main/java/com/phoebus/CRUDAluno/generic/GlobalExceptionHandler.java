package com.phoebus.CRUDAluno.generic;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler({DataIntegrityViolationException.class})
	public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
		String resourceNotAllowed = messageSource.getMessage("resource.not-allowed", null, LocaleContextHolder.getLocale());
		if (ex.getMessage().contains("CPF_UK"))
			resourceNotAllowed += " - CPF já cadastrado";
		if (ex.getMessage().contains("MATRICULA_UK"))
			resourceNotAllowed += " - Matrícula já cadastrada";
		List<ErrorMessage> listErrors = Arrays.asList(new ErrorMessage(resourceNotAllowed, 
				ExceptionUtils.getRootCauseMessage(ex)));
		return handleExceptionInternal(ex, listErrors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	public static class ErrorMessage {
		
		private String msgUser;
		private String msgDev;
		
		public ErrorMessage(String msgUser, String msgDev) {
			this.msgUser = msgUser;
			this.msgDev = msgDev;
		}

		public String getMsgUser() {
			return msgUser;
		}

		public void setMsgUser(String msgUser) {
			this.msgUser = msgUser;
		}

		public String getMsgDev() {
			return msgDev;
		}

		public void setMsgDev(String msgDev) {
			this.msgDev = msgDev;
		}
		
	}

	
}
