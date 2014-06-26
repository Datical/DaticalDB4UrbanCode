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
def daticalDBAction = "rollback";
def daticalDBServer = props['daticalDBServer'];
def daticalDBVersion = props['daticalDBVersion'];
def daticalDBExportSQL = props['daticalDBExportSQL'];
def daticalDBOnlyExportSQL = props['daticalDBOnlyExportSQL'];

def genSQL = "";
if (daticalDBExportSQL == "true") {
	genSQL = "--genSQL";
}
def onlySQL = ""
if (daticalDBOnlyExportSQL == "true") {
	onlySQL = "--onlySQL";
}

def cmdArgs = [daticalDBCmd, '-drivers', daticalDBDriversDir, '--project', daticalDBProjectDir, daticalDBAction, genSQL, onlySQL, daticalDBServer, daticalDBVersion];

int exitCode = cmdHelper.runCommand("Executing Datical DB", cmdArgs);

System.exit(exitCode);
