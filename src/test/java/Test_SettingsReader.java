import common.SettingsReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

public class Test_SettingsReader {

    final static String fileServerSet = "settingsServer.txt";
    final static String fileClientSet = "settingsClient.txt";
    final static String port = "port";
    final static String host = "host";

    @Test
    void test_fileServerSetExistAndCanRead(){
        File file = new File(fileServerSet);
        boolean result = file.exists() && file.isFile() && file.canRead();
        Assertions.assertTrue(result);
    }

    @Test
    void test_fileClientSetExistAndCanRead(){
        File file = new File(fileClientSet);
        boolean result = file.exists() && file.isFile() && file.canRead();
        Assertions.assertTrue(result);
    }

    @Test
    void test_readFromFileServerSet(){
        SettingsReader sr = new SettingsReader(fileServerSet);
        String result = sr.getSetting(port);
        Assertions.assertNotNull(result);
    }

    @Test
    void test_readFromFileClientSet_Port(){
        SettingsReader sr = new SettingsReader(fileClientSet);
        String result = sr.getSetting(port);
        Assertions.assertNotNull(result);
    }

    @Test
    void test_readFromFileClientSet_Host(){
        SettingsReader sr = new SettingsReader(fileClientSet);
        String result = sr.getSetting(host);
        Assertions.assertNotNull(result);
    }

}
