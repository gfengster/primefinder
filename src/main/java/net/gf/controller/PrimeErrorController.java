package net.gf.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Wrong url, internal error or unknown error occur, service will direct to here.
 * @author gfeng
 *
 */
@Controller
public class PrimeErrorController implements ErrorController {
	
	public PrimeErrorController() {
	}
	
	@GetMapping(value="/error")
	public String handleError(final HttpServletRequest request, final Model model) {
	    final Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
	    
	    model.addAttribute("server", request.getServerName());
	    model.addAttribute("port", request.getServerPort());
	    
	    if (status != null) {
	        Integer statusCode = Integer.valueOf(status.toString());
	    
	        if(statusCode == HttpStatus.NOT_FOUND.value()) {
	            return "error-404";
	        }
	        else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
	            return "error-500";
	        }
	    }
		return "error";
	}
	
	@Override
	public String getErrorPath() {
		return null;
	}

}
