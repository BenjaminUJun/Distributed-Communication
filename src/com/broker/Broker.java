package broker;

import rmi.RMIBrokerInterface;
import rmi.RMIBrokerInterfaceImple;
import rmi.RMITLSClientSocketFactory;
import rmi.RMITLSServerSocketFactory;
import util.Constants;
import org.apache.log4j.Logger;  
import org.apache.log4j.PropertyConfigurator;

import java.rmi.AlreadyBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author Yu Jun 2015/5/15.
 *
 */
public class Broker {

	private Logger logger = Logger.getLogger(Broker.class);

    public static void main(String[] args) {


        PropertyConfigurator.configure(args[0] + Constants.BROKER_PRO_STRING);


        Broker broker = new Broker();
        broker.logger.debug("Broker starts...");
        broker.startRMI(args[0]);


    }

    public void startRMI(String path) {


        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }

        logger.debug("RMI starts...");

        RMIBrokerInterfaceImple rmiBrokerInterface = new RMIBrokerInterfaceImple(path);

        try {
            RMIBrokerInterface stub = (RMIBrokerInterface) UnicastRemoteObject.exportObject(rmiBrokerInterface, 0,
                    new RMITLSClientSocketFactory(path + Constants.SERVER_KEYSTORE_STRING, path + Constants.CLIENT_KEYSTORE_STRING, Constants.KEYSTOREPASSWORD),
                    new RMITLSServerSocketFactory(path + Constants.SERVER_KEYSTORE_STRING, path + Constants.CLIENT_KEYSTORE_STRING, Constants.KEYSTOREPASSWORD));
            logger.debug("UnicastRemoteObject.exportObject done");
            Registry registry = LocateRegistry.createRegistry(1100,
                    new RMITLSClientSocketFactory(path + Constants.SERVER_KEYSTORE_STRING, path + Constants.CLIENT_KEYSTORE_STRING, Constants.KEYSTOREPASSWORD),
                    new RMITLSServerSocketFactory(path + Constants.SERVER_KEYSTORE_STRING, path + Constants.CLIENT_KEYSTORE_STRING, Constants.KEYSTOREPASSWORD));
            registry.rebind(Constants.RMIBROKER_STRING, stub);
            logger.debug("LocateRegistry.getRegistry done");
            logger.debug("start binding...");  
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

