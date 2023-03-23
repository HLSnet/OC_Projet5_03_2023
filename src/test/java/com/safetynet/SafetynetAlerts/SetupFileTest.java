package com.safetynet.SafetynetAlerts;

import java.io.*;

import static com.safetynet.SafetynetAlerts.constants.DBConstants.JSONFILE_TEST_BAK_PATHNAME;
import static com.safetynet.SafetynetAlerts.constants.DBConstants.JSONFILE_TEST_PATHNAME;

public class SetupFileTest {


    public static void initializeFileTest() {
        BufferedReader bufferedReader = null;
        FileWriter fileWriter = null;

        try {
            bufferedReader = new BufferedReader(new FileReader(JSONFILE_TEST_BAK_PATHNAME));
            fileWriter = new FileWriter(JSONFILE_TEST_PATHNAME);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                fileWriter.write(str);
                fileWriter.write("\n");
                fileWriter.flush();
            }
            bufferedReader.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
