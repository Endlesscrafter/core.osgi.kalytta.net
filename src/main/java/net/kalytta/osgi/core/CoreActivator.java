package net.kalytta.osgi.core;

import net.kalytta.osgi.logging.LoggingService;
import org.osgi.framework.*;

public class CoreActivator implements BundleActivator, ServiceListener {

    private LoggingService loggingService;

    public void start(BundleContext bundleContext) throws Exception {

        //LoggingService heranholen
        ServiceReference[] refs = bundleContext.getServiceReferences("net.kalytta.osgi.logging.LoggingService", null);
        if (refs != null) {
            System.out.println(refs[0].getBundle());
            loggingService = (LoggingService) bundleContext.getService(refs[0]);
        }
        if(loggingService == null){
            System.out.println("[Core] FATAL No Logging Service found!");
            throw new IllegalStateException();
        }

        loggingService.info("Core","module started.");

    }

    public void stop(BundleContext bundleContext) throws Exception {

        loggingService.info("Core","module stopped.");

    }

    /**
     * Implements ServiceListener.serviceChanged().
     * Prints the details of any service event from the framework.
     * @param event the fired service event.
     **/
    public void serviceChanged(ServiceEvent event)
    {
        String[] objectClass = (String[]) event.getServiceReference().getProperty("objectClass");

        if (event.getType() == ServiceEvent.REGISTERED)
        {
            System.out.println("[Core] Service of type " + objectClass[0] + " registered.");
        }
        else if (event.getType() == ServiceEvent.UNREGISTERING)
        {
            System.out.println("[Core] Service of type " + objectClass[0] + " unregistered.");
        }
        else if (event.getType() == ServiceEvent.MODIFIED)
        {
            System.out.println("[Core] Service of type " + objectClass[0] + " modified.");
        }
    }
}
