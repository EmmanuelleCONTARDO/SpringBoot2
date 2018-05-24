package co.simplon.crud.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import co.simplon.crud.model.Foo;
import co.simplon.crud.service.FooService;

@Controller
@RequestMapping("/foos")
class FooController {

	@Inject
	FooService fooService;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Foo> getFooById(@PathVariable(value = "id") Long userId) {
		Optional<Foo> note = fooService.findbyId(userId);
		return note.isPresent() ? ResponseEntity.ok().body(note.get()) : ResponseEntity.notFound().build();
	}
	   
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<Foo> findAll() {
		List<Foo> foo = new ArrayList<Foo>();
		foo.add(new Foo(1L,"toto"));
		foo.add(new Foo(2L,"titi"));
		foo.add(new Foo(3L,"tutu"));		
		return foo;
	}		
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public Long create(@RequestBody Foo resource) {
		System.out.println(resource);
	    return 1L;
	}
	/*
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Foo read(@PathVariable("id") Long id) {
	  return new Foo(id, "fake read");
	}
	*/
	@RequestMapping(value = "/testParam", method = RequestMethod.GET)
	@ResponseBody
	//URL de test : http://localhost:8090/foos/testParam?param1=toto&param2=titi
	public String readParam(@RequestParam(value="param1", required=false) String param1,
	        				@RequestParam(value="param2", required=false) String param2) {
		return ("param1 : "+param1+"<br />param2 : "+param2);
	}
	
	@RequestMapping(value = "/testParamMap", method = RequestMethod.GET)
	@ResponseBody
	//URL de test : http://localhost:8090/foos/testParamMap?param1=value1&param2=value2....
	public String readParamMap(@RequestParam Map<String, String> parameters) {
		String output="";
		
		//Methode 1 pour parcourir la Map
		/*
		for(String key : parameters.keySet()) {
			parameters.get(key);
		}
		*/
		
		//Methode 2 pour parcourir la Map
		for(Entry<String,String> e : parameters.entrySet()) {
			output += (e.getKey() + " : " + e.getValue() + "<br />");
		}
		
		return output;
	}
	
	@RequestMapping(value = "/cookie", method = RequestMethod.GET)
	@ResponseBody
	public String readCookie(@CookieValue("monCookie") String cookie) {
		return ("Contenu du Cookie = "+cookie);
	}
	
	@RequestMapping(value = "/createCookie", method = RequestMethod.GET)
	@ResponseBody
	public void createCookie(HttpServletResponse response) {
		Cookie cookie = new Cookie("monCookie", "contenuDuCookie");
	    cookie.setPath("/");
	    cookie.setMaxAge(1000);
	    response.addCookie(cookie);
	}
}