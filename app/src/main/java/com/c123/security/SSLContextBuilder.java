package com.c123.security;

import javax.net.ssl.*;
import java.security.KeyStore;
import java.security.SecureRandom;

public class SSLContextBuilder {

    private SSLContextBuilder() {}

    public static SSLContext build() throws Exception {
        SSLContext context = SSLContext.getInstance("TLSv1.2");
        KeyManager[] keyManagers = buildKeyManagers();
        TrustManager[] trustManagers = buildTrustManagers();
        context.init(keyManagers, trustManagers, new SecureRandom());
        return context;
    }

    private static KeyManager[] buildKeyManagers() throws Exception {
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(SSLContextBuilder.class.getClassLoader().getResourceAsStream("ssl/KeyStore.jks"), "abc123".toCharArray());

        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(keyStore, "abc123".toCharArray());

        return kmf.getKeyManagers();
    }

    private static TrustManager[] buildTrustManagers() throws Exception {
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(SSLContextBuilder.class.getClassLoader().getResourceAsStream("ssl/truststore.jks"), "abc123".toCharArray());

        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(keyStore);

        return tmf.getTrustManagers();
    }

}
