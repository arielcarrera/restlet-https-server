package com.github.arielcarrera.restlet_https_server;

import java.io.File;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.Server;
import org.restlet.data.Parameter;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;
import org.restlet.util.Series;

public class RestletServer {
	public static void main(String[] args) throws Exception {
		Component component = new Component();

		String file = "keystore-dev.jks";
		String keystorePwd = "localhost";
		String keyPwd = "localhost";
		File keystoreFile = new File(file);

		if (keystoreFile.exists()) {
			Server sslServer = new Server(Protocol.HTTPS, 8443);
			component.getServers().add(sslServer);
			
			Series<Parameter> parameters = sslServer.getContext().getParameters();
			parameters.add("sslContextFactory",	"org.restlet.engine.ssl.DefaultSslContextFactory");
			component.getContext()
					.getParameters()
					.add("enabledCipherSuites",
							"TLS_DHE_RSA_WITH_AES_256_GCM_SHA384"
									+ " TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256"
									+ " TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256"
									+ " TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384"
									+ " TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256"
									+ " TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256"
									+ " TLS_RSA_WITH_AES_256_CBC_SHA"
									+ " TLS_ECDHE_ECDSA_WITH_3DES_EDE_CBC_SHA"
									+ " TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA"
									+ " TLS_ECDHE_RSA_WITH_3DES_EDE_CBC_SHA"
									+ " TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA"
									+ " TLS_DHE_RSA_WITH_3DES_EDE_CBC_SHA");

			parameters.add("keystorePath", keystoreFile.getAbsolutePath());
			parameters.add("keystorePassword", keystorePwd);
			parameters.add("keyPassword", keyPwd);
			parameters.add("keystoreType", "JKS");
			parameters.add("allowRenegotiate", "false");
		}

		component.getDefaultHost().attach(new Application() {
			@Override
			public Restlet createInboundRoot() {
				Router router = new Router();
				router.attach("/", ContactResource.class);

				return router;
			}
		});

	component.start();
	}
}
