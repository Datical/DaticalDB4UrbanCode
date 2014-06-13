package com.urbancode.air

import java.security.cert.X509Certificate
import javax.net.ssl.ManagerFactoryParameters
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactorySpi
import javax.net.ssl.X509TrustManager
import java.security.*

public class XTrustProvider extends java.security.Provider {
    private final static String NAME = "XTrustJSSE";
    private final static String INFO =
    "XTrust JSSE Provider (implements trust factory with truststore validation disabled)";
    private final static double VERSION = 1.0D;

    public XTrustProvider() {
        super(NAME, VERSION, INFO);

        AccessController.doPrivileged(new CustomPrivilegedAction(this));
    }

    public static void install() {
        if (Security.getProvider(NAME) == null) {
            Security.insertProviderAt(new XTrustProvider(), 2);
            Security.setProperty("ssl.TrustManagerFactory.algorithm", TrustManagerFactoryImpl.getAlgorithm());
        }
    }
}

class TrustManagerFactoryImpl extends TrustManagerFactorySpi {
    public TrustManagerFactoryImpl() {
    }

    public static String getAlgorithm() {
        return "XTrust509";
    }

    protected void engineInit(KeyStore keystore)
    throws KeyStoreException {
    }

    protected void engineInit(ManagerFactoryParameters mgrparams)
    throws InvalidAlgorithmParameterException {
        throw new InvalidAlgorithmParameterException(
                XTrustProvider.NAME + " does not use ManagerFactoryParameters");
    }

    protected TrustManager[] engineGetTrustManagers() {
        return new CustomX509TrustManager() as TrustManager[];
    }
}

class CustomX509TrustManager implements X509TrustManager {
    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }

    public void checkClientTrusted(X509Certificate[] certs, String authType) {
    }

    public void checkServerTrusted(X509Certificate[] certs, String authType) {
    }
}

class CustomPrivilegedAction implements PrivilegedAction {
    def Provider provider
    public CustomPrivilegedAction(Provider provider) {
        this.provider = provider
    }
    public Object run() {
        provider.put("TrustManagerFactory." + TrustManagerFactoryImpl.getAlgorithm(),
                TrustManagerFactoryImpl.class.getName());
        return null;
    }
}