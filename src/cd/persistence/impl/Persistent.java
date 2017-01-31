package cd.persistence.impl;

import cd.persistence.Persistable;
import cd.persistence.PersistenceLayer;

public abstract class Persistent implements Persistable {
	private long id;
	private static PersistenceLayer persistenceLayer;
	
	public Persistent() {
        this.id = -1;
    }
    
    public Persistent( long id ) {
        this.id = id;
    }
	
	@Override
	public long getId() {
		return id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

	@Override
	public boolean isPersistent() {
		return id >= 0;
	}
	
	public static PersistenceLayer getPersistenceLayer() {
        return persistenceLayer;
    }

    public static void setPersistenceLayer(PersistenceLayer pLayer) {
        persistenceLayer = pLayer;
    }
}
