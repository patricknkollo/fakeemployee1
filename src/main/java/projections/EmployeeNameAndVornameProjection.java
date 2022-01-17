package projections;

import org.springframework.beans.factory.annotation.Value;

public interface EmployeeNameAndVornameProjection {
	
	
	//@Value("#{target.numCMDid+ '  ' +target.clientid}")
	//@Value("${value.from.file}")
	@Value("#{target.firstname+ '  ' +target.name}")
	public String getEmployeeNameAndVorname();

}
