package asla_client.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Sebastian Norén <s.norén@gmail.com>
 */
public class WriteReadFiles {

    public WriteReadFiles() {
    }

    public void writeObjectFile(Object object, File file) {
        ObjectOutputStream objectOutput = null;
        try {
            objectOutput = new ObjectOutputStream(new FileOutputStream(file));
            objectOutput.writeObject(object);
            objectOutput.flush();
            objectOutput.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (objectOutput != null) {
                    objectOutput.flush();
                    objectOutput.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Object readObjectFile(File file) {
        ObjectInputStream objectInput = null;
        Object obj = null;
        if (file.exists()) {
            try {
                objectInput = new ObjectInputStream(new FileInputStream(file));
                obj = objectInput.readObject();
                objectInput.close();
                return obj;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (objectInput != null) {
                        objectInput.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                throw new FileNotFoundException("File Not found!");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return obj;
    }

    public void printTextFile(File file) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            while (reader.ready()) {
                System.out.println(reader.readLine());
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void writeStrings(List<String> strings, File file, boolean append) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new BufferedWriter(new FileWriter(file, append)), true);
            for (String strToWrite : strings) {
                writer.println(strToWrite);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        }
    }

    public void writeString(String string, File file, boolean append) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new BufferedWriter(new FileWriter(file, append)), true);
            writer.println(string);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        }
    }

    public List<String> readStringsFile(File file) {
        try {
            return Files.readAllLines(Paths.get(file.getAbsolutePath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
