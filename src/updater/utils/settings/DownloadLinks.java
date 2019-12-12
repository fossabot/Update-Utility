package updater.utils.settings;

import updater.Updater;

public class DownloadLinks {

    // Update Package Names (Applies to all software) (https://github.com/user/repo/releases/latest/ + downloadedFileName)
    private static String downloadedFileName = "Latest-Version.zip";

    /*
      Windows: Windows-Latest-Version.zip;
      Linux: Linux-Latest-Version.zip;
      OSX: OSX-Latest-Version.zip;
    */
    private static String windowsPrefix ="Windows-";
    private static String linuxPrefix ="Linux-";
    private static String osxPrefix ="OSX-";

    // Program download locations (https://github.com/user/repo/releases/latest/)
    private static String edge_eye_binaries_location = "https://github.com/Edge-Route-Networks/Edge-Eye/releases/latest/download/";

    public String getDownloadedFileName() { return downloadedFileName; }

    private String getOSSpecificLink() {
        if (System.getProperty("os.name").contains("Windows"))
            return downloadedFileName = windowsPrefix + downloadedFileName;

        else if (System.getProperty("os.name").contains("Linux"))
            return downloadedFileName = linuxPrefix + downloadedFileName;

        else if (System.getProperty("os.name").contains("OSX"))
            return downloadedFileName = osxPrefix + downloadedFileName;

        return null;
    }

    public String getDownloadLink() {
        // Keep switch, only creates one Updater() object per call.
        switch(new Updater().getProgram()) {
            case "Edge Eye":
                return edge_eye_binaries_location + this.getOSSpecificLink();
            default:
                return null;
        }
    }
}
