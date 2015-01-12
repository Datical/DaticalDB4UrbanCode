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
def daticalDBProjectDir = getAbsPath(props['daticalDBProjectDir']);
def daticalDBAction = "diffChangelog";

def daticalDBServerReference = props['daticalDBServerReference'];
def daticalDBServerComparison = props['daticalDBServerComparison'];
def daticalDBChangeLog = getAbsPath(props['daticalDBChangeLog']);

def cmdArgs = ""
if (daticalDBChangeLog) {
	cmdArgs = [daticalDBCmd, '-drivers', daticalDBDriversDir,  '--project', daticalDBProjectDir,daticalDBAction, daticalDBServerReference, daticalDBServerComparison, daticalDBChangeLog];
} else {
	cmdArgs = [daticalDBCmd, '-drivers', daticalDBDriversDir,  '--project', daticalDBProjectDir,daticalDBAction, daticalDBServerReference, daticalDBServerComparison];
}	

def daticalDBLabels = props['daticalDBLabels'];
if (daticalDBLabels) {
	cmdArgs << "--assignLabels";
	cmdArgs << daticalDBLabels;
}

def daticalDBvm = props['daticalDBvm'];
if (daticalDBvm) {
	cmdArgs << "--vm";
	cmdArgs << daticalDBvm;
}
def daticalDBvmargs = props['daticalDBvmargs'];
if (daticalDBvmargs) {
	cmdArgs << "--vmargs";
	String[] myArray = daticalDBvmargs.split();
	for ( x in myArray ) {
		cmdArgs << x;
	}
	//cmdArgs << daticalDBvmargs;
}

int exitCode = cmdHelper.runCommand("Executing Datical DB", cmdArgs);

System.exit(exitCode);
