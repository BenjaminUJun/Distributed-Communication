package server;

import java.io.IOException;
import java.util.Properties;

import util.Constants;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;

/**
 * @author Yu Jun 2015/5/16.
 *
 */
public class CORBAServer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Logger logger = Logger.getLogger(CORBAServer.class);
    	PropertyConfigurator.configure(args[0] + Constants.CORBASER_PRO_STRING);
    	
    	
    	try {
    		Runtime runtime = Runtime.getRuntime();
			runtime.exec("tnameserv -ORBInitialPort 1235");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			logger.error("tnameserv start error!");
		}
    	
    	Properties p = new java.util.Properties();
        p.setProperty("com.sun.CORBA.codeset.charsets", "0x05010001, 0x00010109"); // UTF-8, UTF-16
        p.setProperty("com.sun.CORBA.codeset.wcharsets", "0x00010109, 0x05010001"); // UTF-16, UTF-8
        
        CORBAServant servant = new CORBAServant(args[0]);
        logger.debug("servant instance created");
		String[] argv = {"-ORBInitialPort","1235"};
        try {
        	ORB orb = ORB.init(argv, p);
        	orb.connect(servant);
            org.omg.CORBA.Object objectRef = orb.resolve_initial_references("NameService");
            NamingContext namingContext = NamingContextHelper.narrow(objectRef);
            NameComponent nameComponent = new NameComponent("corba.hotelServer.Server", "");
            NameComponent[] path = {nameComponent};
            namingContext.rebind(path, servant);
            logger.debug("CORBA server is up...");
            Object syncObj = new Object();
            synchronized (syncObj) {
                syncObj.wait();
            }
        } catch (InvalidName invalidName) {
            invalidName.printStackTrace();
        } catch (org.omg.CosNaming.NamingContextPackage.InvalidName invalidName) {
            invalidName.printStackTrace();
        } catch (CannotProceed cannotProceed) {
            cannotProceed.printStackTrace();
        } catch (NotFound notFound) {
            notFound.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
	}

}
