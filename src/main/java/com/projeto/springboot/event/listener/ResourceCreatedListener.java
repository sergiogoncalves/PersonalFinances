package com.projeto.springboot.event.listener;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.projeto.springboot.event.ResourceCreatedEvent;

@Component
public class ResourceCreatedListener implements ApplicationListener<ResourceCreatedEvent> {

	@Override
	public void onApplicationEvent(ResourceCreatedEvent resourceCreatedEvent) {
		
		HttpServletResponse response = resourceCreatedEvent.getResponse();
		Long codigo = resourceCreatedEvent.getCodigo();
		
	 	URI uri = addHeaderLocation(codigo);
 	
 	    response.setHeader("Location", uri.toASCIIString());
		
	}

	private URI addHeaderLocation(Long codigo) {
		
		URI uri = ServletUriComponentsBuilder
 				.fromCurrentRequestUri().path("/{codigo}")
 					.buildAndExpand(codigo).toUri();
		return uri;
	}

}
