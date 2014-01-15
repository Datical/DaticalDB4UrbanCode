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

def exePath = props['exePath'];

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
def daticalDBDriversDir = getAbsPath(props['daticalDBDriversDir']);
def profile = getAbsPath(props['profile']);
def output = getAbsPath(props['output']);

def cmdArgs = [daticalDBInstallDir, '-drivers', daticalDBDriversDir, daticalDBAction, daticalDBServer];
cmdHelper.runCommand("Executing Datical DB", cmdArgs);
