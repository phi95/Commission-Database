package cd.logic.impl;

import java.sql.Connection;

import cd.CDException;
import cd.logic.LogicLayer;
import cd.object.ObjectLayer;
import cd.object.impl.ObjectLayerImpl;
import cd.persistence.PersistenceLayer;
import cd.persistence.impl.PersistenceLayerImpl;

public class LogicLayerImpl implements LogicLayer {
	private ObjectLayer objectLayer = null;
	
	public LogicLayerImpl( Connection conn )
    {
        objectLayer = new ObjectLayerImpl();
        PersistenceLayer persistenceLayer = new PersistenceLayerImpl( conn, objectLayer );
        objectLayer.setPersistence( persistenceLayer );
    }
    
    public LogicLayerImpl( ObjectLayer objectLayer )
    {
        this.objectLayer = objectLayer;
    }
}
