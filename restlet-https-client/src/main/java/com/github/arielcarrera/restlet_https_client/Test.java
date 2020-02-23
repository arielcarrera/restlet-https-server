package com.github.arielcarrera.restlet_https_client;

import java.io.File;
import java.io.IOException;

import org.restlet.Client;
import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;
import org.restlet.data.Protocol;

/**
 * Test: Restlet client
 * 
 * @author Ariel Carrera
 *
 */
public class Test {

	private static final String url = "https://localhost:8443/";

	public static void main(String[] args) {
		System.out.println("Sending HTTP GET request to " + url);
		// Add your HTTPS specifications
		String file = "keystore-dev.jks";
		String keystorePwd = "localhost";
		String keyPwd = "localhost";
		File keystoreFile = new File(file);

		if (keystoreFile.exists()) {
			Request request = new Request(Method.GET, url);
			System.setProperty("javax.net.ssl.trustStore", keystoreFile.getAbsolutePath());
			System.setProperty("javax.net.ssl.trustStorePassword", keystorePwd);
			System.setProperty("javax.net.ssl.keyStore", keystoreFile.getAbsolutePath());
			System.setProperty("javax.net.ssl.keyStorePassword", keystorePwd);

			Client client = new Client(new Context(), Protocol.HTTPS);
			client.getContext().getParameters().add("sslContextFactory",
					"org.restlet.engine.ssl.DefaultSslContextFactory");
			client.getContext().getParameters().add("keystoreType", "JKS");
			client.getContext().getParameters().add("keystorePath", keystoreFile.getAbsolutePath());
			client.getContext().getParameters().add("keystorePassword", keystorePwd);
			client.getContext().getParameters().add("keyPassword", keyPwd);

			Response resp = client.handle(request);

			System.out.println("Service response code: " + resp.getStatus());
			try {
				System.out.println("Service response body: " + resp.getEntity().getText());
			} catch (IOException e) {
				System.out.println("Error reading response");
			}

		} else {
			System.err.println("Error keystore not found");
		}

	}
}
