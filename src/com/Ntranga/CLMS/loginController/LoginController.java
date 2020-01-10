package com.Ntranga.CLMS.loginController;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Ntranga.CLMS.Service.VendorService;
import com.Ntranga.CLMS.Service.WorkerService;
import com.Ntranga.CLMS.loginService.LoginService;
import com.Ntranga.core.UserDetails;
import com.Ntranga.core.configuration.SecurityHelper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Transactional
@RestController("loginController")
public class LoginController {

	private static Logger log = Logger.getLogger(LoginController.class);

	@Autowired
	private LoginService loginService;

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private WorkerService workerService;

	@Autowired
	private VendorService vendorService;
	
	@RequestMapping(value = "/validateUser.json", method = RequestMethod.POST)
	public  ResponseEntity<UserDetails> validateUser(@RequestBody String userDetailsJson) {		
		log.info("Entered in validateUser Method: "+userDetailsJson);	
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(userDetailsJson);
		log.debug("JSON Object : "+jo);
		UserDetails userDetails =  new UserDetails();
		if(userDetailsJson != null && jo.get("userName").getAsString() != null && jo.get("password").getAsString() != null){
			 userDetails = loginService.validateUser(jo.get("userName").getAsString(), jo.get("password").getAsString());
			 System.out.println(userDetails.getUserName());
		}else{
			userDetails.setUserId(0);
		}	
		
		return new ResponseEntity<UserDetails>(userDetails,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(ModelMap model, HttpServletRequest request) {
		System.out.println(" in index view controller");
		if (log.isDebugEnabled()) {
			log.debug("in index url going to welcome page(login)");
			log.debug("----index----");
		}
		model.addAttribute("msg", " ");
		return "loginPages/welcome";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(ModelMap model, HttpServletRequest request) {
		System.out.println(" in index login controller");
		if (log.isDebugEnabled()) {
			log.debug("in login url going to welcome page(login)");
		}
		System.out.println("in login url going to welcome page(login)");
		model.addAttribute("msg", " ");
		if (request.getParameter("session_expired") != null) {
			System.out.println(" in  login controller session_expired");
			return "common/sessiontimedout";
		} else {
			return "loginPages/welcome";
		}
	}

	@RequestMapping(value = "/loginfailed", method = RequestMethod.GET)
	public String loginFailed(@RequestParam(value = "error", required = false) String error, Model model,
			HttpServletRequest request) {
		System.out.println(" in  loginfailed controller ");
		log.error("logged user Authentication is failed");
		String message = SecurityHelper.getErrorMessage(request);
		model.addAttribute("msg", message);
		log.error("Error message is " + message);
		clearSession(request);
		return "loginPages/welcome";
	}

	@RequestMapping(value = "/welcome")
	public String welcome(Model model, HttpServletRequest request) throws JSONException {
		System.out.println(" in  welcome controller ");
		User user = null;
		String strRole = "";
		String strReturnView = "redirect";
		try {
			user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Collection<GrantedAuthority> c = user.getAuthorities();
			strRole = c.iterator().next().toString();
			System.out.println(user.getUsername() + "  ::  " + strRole);
			log.error("Login success with User Name:" + user.getUsername() + "Role Name" + strRole);
			HttpSession session = request.getSession();
			UserDetails useretails = new UserDetails();
			if (session.getAttribute(UserDetails.SESSION_KEY) == null) {
				log.warn("Login success........... User Name:" + user.getUsername());
				useretails = loginService.loadUserByUsername(user.getUsername());
				session.setAttribute(UserDetails.SESSION_KEY, useretails);
			} else {
				strRole = ((UserDetails) session.getAttribute(UserDetails.SESSION_KEY)).getRoleName();
			}
			String roleName = ((UserDetails) session.getAttribute(UserDetails.SESSION_KEY)).getRoleName();
			System.out.println(roleName);
			strReturnView = "loginPages/success";
			return strReturnView;
		} catch (Exception e) {
			log.error(e);
			return "redirect";
		}
	}

	@RequestMapping(value = "/sessionout")
	public String sessionTimedout(HttpServletRequest request, Model model) {
		System.out.println(" in  sessionout controller ");
		if (log.isDebugEnabled()) {
			log.debug("sessionout url -- sessionTimedout method");
		}
		// clear the current user session details
		clearSession(request);
		model.addAttribute("msg", "Session has expired");
		return "loginPages/welcome";
	}

	@RequestMapping(value = "/sessionoutpage")
	public String sessionTimedout1(HttpServletRequest request, Model model) {
		System.out.println(" in  sessionoutpage controller ");
		if (log.isDebugEnabled()) {
			log.debug("sessionout url -- sessionTimedout method");
		}
		// clear the current user session details
		clearSession(request);
		return "sessiontimedout";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(Model model, HttpServletRequest request) {
		// clear the current user session details
		clearSession(request);
		model.addAttribute("msg", "Successfully logged out");
		return "loginPages/welcome";
	}

	private void clearSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session != null) {
			session.invalidate();
		}
	}

	

	

}
