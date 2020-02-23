package com.github.arielcarrera.restlet_https_server;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class ContactResource extends ServerResource {
	@Get
	public Contact getContact() {
		return new Contact("John");
	}

	@Delete
	public String deleteContact() {
		return "Contact deleted";
	}
}
