/**
 * 
 */
package rmi;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.rmi.server.RMIServerSocketFactory;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * @author Yu Jun 2015/5/5.
 *
 */
public class RMITLSServerSocketFactory implements RMIServerSocketFactory {
    
	private SSLServerSocketFactory sslServerSocketFactory = null;

    public RMITLSServerSocketFactory(String _server_crt, String _client_crt, String _password) throws Exception {

        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");

            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");

            KeyStore keyStore = KeyStore.getInstance("JKS");
            KeyStore tkeyStore = KeyStore.getInstance("JKS");

            keyStore.load(new FileInputStream(_server_crt), _password.toCharArray());
            tkeyStore.load(new FileInputStream(_client_crt), _password.toCharArray());

            keyManagerFactory.init(keyStore, _password.toCharArray());
            trustManagerFactory.init(tkeyStore);

            sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);
            sslServerSocketFactory = sslContext.getServerSocketFactory();
        } catch (Exception err) {
            err.printStackTrace();
        }

    }
    
    @Override
    public ServerSocket createServerSocket(int i) throws IOException {

        SSLServerSocket sslServerSocket = (SSLServerSocket) sslServerSocketFactory.createServerSocket(i);
        sslServerSocket.setNeedClientAuth(true);

        return sslServerSocket;
    }

    public int hashCode() {
        return getClass().hashCode();
    }

    public boolean equeals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return true;
    }
}
