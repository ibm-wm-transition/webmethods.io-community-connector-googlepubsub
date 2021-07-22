package wm.cloudstreamsExt.google.pubsub;		/* Step 1 ^^^^Ext Changes^^^^^^  Replace companyName with the folder namespace ^^^^^^^^^^^ */

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
// --- <<IS-END-IMPORTS>> ---

public final class admin

{
	// ---( internal utility methods )---

	final static admin _instance = new admin();

	static admin _newInstance() { return new admin(); }

	static admin _cast(Object o) { return (admin)o; }
	
	private static final String connectorId = "com.softwareag.cloudstreams.google.pubsub_v1";		/* Step 2 ^^^^Ext Changes^^^^^^  Add connector id here from the target provider ^^^^^^^^^^^ */
	private static final String apiVersion = "v1";		// Step 3 ^^^Ext Changes^^^^^^ API Version of the offering
	private static final String companyAndOffering = "googlePubsub";	// Step 4 ^^^Ext Changes^^^^^^ companyName followed by offering
	private static final String interactionCallbackNamespace = "wm.cloudstreamsExt.google.pubsub.handler:interactionCallback";	// Step 5 ^^^Ext Changes^^^^^^ Namespace of the interactionCallback
	private static final String serviceName = "topics"; 	// Step 6 ^^^Ext Changes^^^^^^Service Name from provider/connector XML
	

	// ---( server methods )---




	public static final void shutdown (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(shutdown)>> ---
		// @sigtype java 3.5
		unregisterConnectorExtension(connectorId);
		// --- <<IS-END>> ---

                
	}



	public static final void startup (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(startup)>> ---
		// @sigtype java 3.5
		registerConnectorExtension(connectorId, new ConnectorExtension(getConnectorExtensionMetadata()));
		// --- <<IS-END>> ---

                
	}
	
	
	private static ConnectorExtensionMetadata getHandler() {
		ConnectorExtensionMetadata handler = new ConnectorExtensionMetadata();
		handler.setVersion(apiVersion);					
		handler.setLoggingCode(companyAndOffering);
		handler.setCallbackHandlerService(NSName.create(interactionCallbackNamespace));

		return handler;
	}
	
	private static ConnectorExtensionMetadata getConnectorExtensionMetadata() {

		ConnectorExtensionMetadata handler = getHandler();

		List<ConnectorExtensionInteraction> connectorExtensionInteractions = new ArrayList<ConnectorExtensionInteraction>();

		handler.setExtInteractions(serviceName, connectorExtensionInteractions);

		return handler;
	}
	
	private static void registerConnectorExtension(String connectorID, ConnectorExtension connectorExtension) throws ServiceException {
		IDataMap input = new IDataMap();
		input.put("connectorID", connectorID);		
		input.put("connectorExtension", connectorExtension);
		try {
			Service.doInvoke(NSName.create("wm.cloudstreams.ext.service.soap.handler:registerConnector"), input.getIData());
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	private static void unregisterConnectorExtension(String connectorID) throws ServiceException {
		IDataMap input = new IDataMap();
		input.put("connectorID", connectorID);
		try {
			Service.doInvoke(NSName.create("wm.cloudstreams.ext.service.soap.handler:unregisterConnector"), input.getIData());			
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}

