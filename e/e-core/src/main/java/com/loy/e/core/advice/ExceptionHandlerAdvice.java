package com.loy.e.core.advice;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.loy.e.common.util.Assert;
import com.loy.e.common.vo.ErrorResponseData;
import com.loy.e.core.exception.LoyException;
import com.loy.e.security.service.LoyLogService;
import com.loy.e.security.service.SystemKeyService;



/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
@ControllerAdvice

public class ExceptionHandlerAdvice {
	protected final Log logger = LogFactory.getLog(ExceptionHandlerAdvice.class);
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private LoyLogService loyLogService;
	@Autowired
	SystemKeyService systemKeyService;
	
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(LoyException.class)
	@ResponseBody 
	ErrorResponseData handleBadRequest(HttpServletRequest req, LoyException ex) {
		ErrorResponseData data = new ErrorResponseData();
		data.setErrorCode(ex.getErrorKey());
		String errorKey = ex.getErrorKey();
		String msg = messageSource.getMessage(errorKey, ex.getParams(), LocaleContextHolder.getLocale());
		data.setMsg(msg);
		return data;
	 } 
	
//	@ResponseStatus(HttpStatus.OK)
//	@ExceptionHandler(IncorrectCredentialsException.class)
//	@ResponseBody 
//	ErrorResponseData handleBadRequest(HttpServletRequest req, IncorrectCredentialsException ex) {
//		ErrorResponseData data = new ErrorResponseData();
//		String errorCode = "user_password_error";
//		data.setErrorCode(errorCode);
//		String msg = messageSource.getMessage(errorCode,null, UserUtils.getLocale());
//		data.setMsg(msg);
//		return data;
//	 } 
	
//	@ResponseStatus(HttpStatus.OK)
//	@ExceptionHandler(UnknownAccountException.class)
//	@ResponseBody 
//	ErrorResponseData handleBadRequest(HttpServletRequest req, UnknownAccountException ex) {
//		ErrorResponseData data = new ErrorResponseData();
//		String errorCode = "user_password_error";
//		data.setErrorCode(errorCode);
//		String msg = messageSource.getMessage(errorCode,null, UserUtils.getLocale());
//		data.setMsg(msg);
//		return data;
//	 } 
	
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(Throwable.class)
	@ResponseBody 
	ErrorResponseData handleBadRequest(HttpServletRequest req, Throwable ex) {
		String exceptionName = ex.getClass().getName();
		StringWriter sw=new StringWriter();  
        PrintWriter pw=new PrintWriter(sw);  
        ex.printStackTrace(pw);
        String stackTraceMsg = sw.toString();
        try{
        	 loyLogService.exception(systemKeyService.getSystemCode(),exceptionName,stackTraceMsg);
        }catch(Throwable e){
        	logger.error("",e);
        }
		logger.error("",ex);
		ErrorResponseData data = new ErrorResponseData();
		String msg = messageSource.getMessage(Assert.SYS_ERROR_CODE,null, LocaleContextHolder.getLocale());
		data.setMsg(msg);
		return data;
	 } 
}
