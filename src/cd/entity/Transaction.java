package cd.entity;

import cd.CDException;
import cd.persistence.Persistable;
import java.util.Date;
public interface Transaction extends Persistable {	

	/**
	 * Retrieves the date of the transaction.
	 * @return the transaction's date.
	 */
	public Date getDate();
	
}
