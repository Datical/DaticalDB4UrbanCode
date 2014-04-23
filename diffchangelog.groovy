import com.urbancode.air.CommandHelper;

final def inputPropsFile = new File(args[0])
final def outputPropsFile = new File(args[1])

final def props = new Properties()
try {
	props.load(new FileInputStream(inputPropsFile))
}
catch (IOException e) {
	throw new RuntimeException(e)
}

final def cwd = new File('.');
final def cmdHelper = new CommandHelper(cwd);



//--------------------------------------------------------------------------------------------------
def getAbsPath(def file) {
	def tempFile = null;
	if (file != null && file != "") {
		File temporaryFile = new File(file);
		tempFile = temporaryFile.getAbsolutePath();
	}
	return tempFile;
}
//path properties
def daticalDBCmd = getAbsPath(props['daticalDBCmd']);
def daticalDBDriversDir = getAbsPath(props['daticalDBDriversDir']);
def daticalDBAction = "newDBDef";

def daticalDBVendor = props['daticalDBVendor'];
def daticalDBRef = "";

if (daticalDBVendor == "Oracle") {
	daticalDBRef = "OracleDbDef";
} else if (daticalDBVendor == "MSSQL") {
	daticalDBRef = "SqlServerDbDef";
} else if (daticalDBVendor == "MySQL") {
	daticalDBRef = "MysqlDbDef";
} else if (daticalDBVendor = "PostgreSQL") {
	daticalDBRef = "PostgresqlDbDef";
} else if (daticalDBVendor = "DB2"){
	daticalDBRef = "Db2DbDef";
} else {
	// throw an error
	// return new ActionResult(false, "Invalid value for Datical DB Vendor: " + daticalDBVendor.toString());
}

def daticalDBDatabaseName = props['daticalDBDatabaseName'];

if (daticalDBVendor == ("MySQL") || daticalDBVendor == "PostgreSQL" || daticalDBVendor == "DB2" || daticalDBVendor == "MSSQL") {
	if (daticalDBDatabaseName == "" || daticalDBDatabaseName == null) {
		// throw an error
		// return new ActionResult(false, "For Database Vendor " + daticalDBVendor.toString() + ", Datical DB Database Name is required.");
	}
}

def daticalDBServiceName = props['daticalDBServiceName'];
if (daticalDBVendor.equals("Oracle")) {
	if (daticalDBSID == "" || daticalDBSID == null) {
		if (daticalDBServiceName == "" || daticalDBServiceName == null) {
			// throw error
			//return new ActionResult(false, "For Database Vendor " + daticalDBVendor.toString() + ", SID or Service Name is required.");
		}
	}
}

def cmdArgs = [];


cmdArgs << daticalDBLocation;
cmdArgs << "--project";
cmdArgs << props['daticalDBProjectDirectory'];
cmdArgs << "newDbDef";
cmdArgs << "DbDefClass";
cmdArgs << props['daticalDBRef'];
cmdArgs << "name";
cmdArgs << props['daticalDBStepName'];
cmdArgs << "username";
cmdArgs << props['daticalDBUsername'];
cmdArgs << "password";
cmdArgs << props['daticalDBPassword'];
cmdArgs << "hostname";
cmdArgs << props['daticalDBHost'];
cmdArgs << "port";
cmdArgs << props['daticalDBPort'];

def daticalDBDatabaseName = props['daticalDBDatabaseName'];
if (daticalDBDatabaseName != "" || daticalDBDatabaseName != null) {
	if (daticalDBVendor == "MSSQL") {
		cmdArgs << "databaseName";
	} else {
		cmdArgs << "database";
	}
	cmdArgs << daticalDBDatabaseName;
}

def daticalDBSID = props['daticalDBSID'];
if (daticalDBSID != "" || daticalDBSID != null) {
	cmdArgs << "sid";
	cmdArgs << daticalDBSID;
}

def daticalDBIsIntegratedSecurity = props['daticalDBIsIntegratedSecurity'];
if (daticalDBIsIntegratedSecurity != null || daticalDBIsIntegratedSecurity != "") {
	if (setParameter(daticalDBIsIntegratedSecurity.toString())) {
		cmdArgs << "isIntegratedSecurity";
		cmdArgs << daticalDBIsIntegratedSecurity.toString().toLowerCase();
	}
}

def daticalDBApplicationName = props['daticalDBApplicationName'];
if (daticalDBApplicationName != null || daticalDBApplicationName != "") {
	cmdArgs << "applicationname";
	cmdArgs << daticalDBApplicationName;
}

def daticalDBContext = props['daticalDBContext'];
if (daticalDBContext != null || daticalDBContext != "") {
	cmdArgs << "context";
	cmdArgs << daticalDBContext;
}

def daticalDBDefaultCatalogName = props['daticalDBDefaultCatalogName'];
if (daticalDBDefaultCatalogName != null || daticalDBDefaultCatalogName != "") {
	cmdArgs << "defaultCatalogName";
	cmdArgs << daticalDBDefaultCatalogName;
}

def daticalDBDefaultSchemaName = props['daticalDBDefaultSchemaName'];
if (daticalDBDefaultSchemaName != null || daticalDBDefaultSchemaName != "") {
	cmdArgs << "defaultSchemaName";
	cmdArgs << daticalDBDefaultSchemaName;
}

def daticalDBInstaneName = props['daticalDBInstanceName'];
if (daticalDBInstanceName != null || daticalDBInstanceName != "") {
	cmdArgs << "instancename";
	cmdArgs << daticalDBInstanceName;
}

def daticalDBServiceName = props['daticalDBServiceName'];
if (daticalDBServiceName != null || daticalDBServiceName != "") {
	cmdArgs << "serviceName";
	cmdArgs << daticalDBServiceName;
}



int exitCode = cmdHelper.runCommand("Executing Datical DB", cmdArgs);

System.exit(exitCode);
