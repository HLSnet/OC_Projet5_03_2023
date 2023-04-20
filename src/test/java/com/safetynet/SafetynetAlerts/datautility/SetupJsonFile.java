package com.safetynet.safetynetalerts.datautility;

import java.io.*;

/**
 * Classe contenant la méthode de classe reloadTestFile qui :
 *  recopie le fichier contenant les données à utiliser dans les tests
 * dans le fichier qui est utilisé pour les tests
 *
 * BUT: s'assurer que les tests unitaires utilisent à chaque fois les mêmes
 * données
 */
public class SetupJsonFile {

    public static void reloadTestFile(String fichierTestReference, String fichierTest) {
        BufferedReader bufferedReader = null;
        FileWriter fileWriter = null;

        try {
            // On lit le fichier de json de réference
            bufferedReader = new BufferedReader(new FileReader(fichierTestReference));

            // On ouvre le fichier json qui servira pour les tests
            fileWriter = new FileWriter(fichierTest);

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
