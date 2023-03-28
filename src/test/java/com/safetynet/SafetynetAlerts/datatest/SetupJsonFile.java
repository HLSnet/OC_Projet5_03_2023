package com.safetynet.safetynetalerts.datatest;

import java.io.*;

import static com.safetynet.safetynetalerts.constants.DBConstants.JSONFILE_TEST_BAK_PATHNAME;
import static com.safetynet.safetynetalerts.constants.DBConstants.JSONFILE_TEST_PATHNAME;


public class SetupJsonFile {


    public static void initialisation() {
        BufferedReader bufferedReader = null;
        FileWriter fileWriter = null;

        try {
            // On lit le fichier de json de réference
            bufferedReader = new BufferedReader(new FileReader(JSONFILE_TEST_BAK_PATHNAME));

            // On ouvre le fichier json qui servira pour les tests
            fileWriter = new FileWriter(JSONFILE_TEST_PATHNAME);

            // On copie le fichier json de référence dans le fichier servant aux tests
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
