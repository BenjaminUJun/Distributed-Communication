/**
 * 
 */
package server;

import java.rmi.AlreadyBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import rmi.RMIInterface;
import rmi.RMIInterfaceImple;
import rmi.RMITLSClientSocketFactory;
import rmi.RMITLSServerSocketFactory;
import util.Constants;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * @author Yu Jun 2015/5/6.
 *
 */
public class RMIServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }
        

        Logger logger = Logger.getLogger(RMIServer.class);
        PropertyConfigurator.configure(args[0] + Constants.RMISER_PRO_STRING);
        logger.info("RMI server starts...");

        RMIInterfaceImple rmiInterface = new RMIInterfaceImple(args[0]);

        try {
            RMIInterface stub = (RMIInterface) UnicastRemoteObject.exportObject(rmiInterface, 0,
                    new RMITLSClientSocketFactory(args[0] + Constants.SERVER_KEYSTORE_STRING, args[0] + Constants.CLIENT_KEYSTORE_STRING, Constants.KEYSTOREPASSWORD),
                    new RMITLSServerSocketFactory(args[0] + Constants.SERVER_KEYSTORE_STRING, args[0] + Constants.CLIENT_KEYSTORE_STRING, Constants.KEYSTOREPASSWORD));
            logger.debug("UnicastRemoteObject.exportObject done");

            Registry registry = LocateRegistry.createRegistry(1099,
                    new RMITLSClientSocketFactory(args[0] + Constants.SERVER_KEYSTORE_STRING, args[0] + Constants.CLIENT_KEYSTORE_STRING, Constants.KEYSTOREPASSWORD),
                    new RMITLSServerSocketFactory(args[0] + Constants.SERVER_KEYSTORE_STRING, args[0] + Constants.CLIENT_KEYSTORE_STRING, Constants.KEYSTOREPASSWORD));
            logger.debug("LocateRegistry.createRegistry done");
            logger.debug("start binding...");
//            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(Constants.RMISERVER_STRING, stub);
            logger.debug("binding done");

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}
