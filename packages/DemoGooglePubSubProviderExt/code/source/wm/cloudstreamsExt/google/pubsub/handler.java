package wm.cloudstreamsExt.google.pubsub;		/* Step 1 ^^^^Ext Changes^^^^^^  Replace companyName with the folder namespace ^^^^^^^^^^^ */

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
// --- <<IS-END-IMPORTS>> ---

public final class handler

{
	// ---( internal utility methods )---

	final static handler _instance = new handler();

	static handler _newInstance() { return new handler(); }

	static handler _cast(Object o) { return (handler)o; }

	// ---( server methods )---




	public static final void interactionCallback (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(interactionCallback)>> ---
		// @sigtype java 3.5
		
		MetadataCallbackModel metadataCallbackModel = null;
		try {
			metadataCallbackModel = new MetadataCallbackModel();
			metadataCallbackModel.setIData(pipeline);
			String connectorID = metadataCallbackModel.getConnectorID();

			//Get the connector extension.
			ConnectorExtension connectorExtension = ConnectorExtensionRegistry.getConnectorExtensionRegistry().getConnectorExtension(connectorID);
			if(connectorExtension == null){
				return;
			}
			connectorExtension.getLogger().debug("Invoked interactionCallback for connector ID: " + connectorID 
					+ ", interaction name: " + metadataCallbackModel.getInteractionName());
			ConnectorExtensionMetadata connectorExtMetadata = connectorExtension.getConnectorExtensionMetadata();
			
			//get the cloned wrapper service. 
			FlowSvcImpl wrappedFlowServiceNode = ConnectorExtensionUtils.getWrapperService(metadataCallbackModel, connectorExtMetadata);
			
			if(wrappedFlowServiceNode instanceof FlowSvcImpl) {
				
				NSSignature signature = wrappedFlowServiceNode.getSignature();
				NSRecord input = signature.getInput();
				NSRecord output = signature.getOutput();
				
				//Populate custom NSRecord representing the business object.
				NSRecord businessObject = ConnectorExtensionUtils.createBusinessObjectRecord(metadataCallbackModel.getBusinessObject());
				if(input != null) {
					//If applicable, then update the input signature with real business object NSRecord.
					ConnectorExtensionUtils.searchAndReplaceRecord(input, businessObject, "*businessObject");
				}
				if(output != null){
					//If applicable, then update the output signature with real business object NSRecord.					
					ConnectorExtensionUtils.searchAndReplaceRecord(output, businessObject, "*businessObject");
				}
				//Updates the wrapper service mappings.
				ConnectorExtensionUtils.updateServiceStepsAndMappings(metadataCallbackModel, wrappedFlowServiceNode, connectorExtMetadata);	
				
				//Unlock the wrapper folder.
				ConnectorExtensionUtils.unlock(metadataCallbackModel);
			}
		} catch (Throwable e){
			if(metadataCallbackModel != null) { 
				//Delete the wrapper folder in case of any Throwable/exception. 
				ConnectorExtensionUtils.cleanup(metadataCallbackModel);
			}
			throw new ServiceException(e);
		}
		
		// --- <<IS-END>> ---

                
	}
}

