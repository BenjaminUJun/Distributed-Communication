/**
 * 
 */
package rmi;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.rmi.server.RMIClientSocketFactory;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;

import util.Constants;


/**
 * @author Yu Jun 2015/5/5.
 *
 */
public class RMITLSClientSocketFactory implements RMIClientSocketFactory, Serializable {
	
	String server_crt = null;
    String client_crt = null;
    String password = null;

    public RMITLSClientSocketFactory(String _server_crt, String _client_crt, String _password) {

        this.server_crt = _server_crt;
        this.client_crt = _client_crt;
        this.password = _password;

    }

    @Override
    public Socket createSocket(String s, int i) throws IOException {

        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");

            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");

            KeyStore keyStore = KeyStore.getInstance("JKS");
            KeyStore tkeyStore = KeyStore.getInstance("JKS");

            keyStore.load(new FileInputStream(this.client_crt), this.password.toCharArray());
            tkeyStore.load(new FileInputStream(this.server_crt), this.password.toCharArray());

            keyManagerFactory.init(keyStore, Constants.KEYSTOREPASSWORD.toCharArray());
            trustManagerFactory.init(tkeyStore);

            sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);
            SSLSocket socket = (SSLSocket) sslContext.getSocketFactory().createSocket(s, i);
            return socket;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int hashCode() {
        return getClass().hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return true;
    }
}
