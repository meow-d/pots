package database;

import java.io.*;
import java.lang.reflect.*;
import java.util.Map;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import data.*;

// last time we have one load function per class, and each class has a toString() function. this is much less feasible and elegant now that we have so many tables. so instead i'm using reflection.

public class CSV {
    static String dataPath = "data/";

    public static <T extends BaseItem> void save(Map<String, T> objects, String filePath) {
        try {
            filePath = dataPath + filePath;
            FileWriter fileWriter = new FileWriter(filePath);
            BufferedWriter writer = new BufferedWriter(fileWriter);

            if (!objects.isEmpty()) {
                for (Map.Entry<String, T> entry : objects.entrySet()) {
                    StringBuilder csvString = new StringBuilder();

                    T object = entry.getValue();
                    Field[] fields = object.getClass().getDeclaredFields();

                    for (Field field : fields) {
                        field.setAccessible(true);
                        csvString.append(field.get(object)).append(",");
                    }
                    // Remove the trailing comma
                    if (csvString.length() > 0) {
                        csvString.setLength(csvString.length() - 1);
                    }

                    writer.write(csvString.toString());
                    writer.newLine();
                }
            }

            writer.close();
        } catch (IOException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static <T extends BaseItem> void load(Map<String, T> objects, String filePath, Class<T> clazz) {
        try {
            filePath = dataPath + filePath;
            FileReader fileReader = new FileReader(filePath);
            BufferedReader reader = new BufferedReader(fileReader);

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                Constructor<?>[] constructors = clazz.getConstructors();
                Constructor<T> matchingConstructor = null;

                // Find the constructor that matches the number of parameters
                for (Constructor<?> constructor : constructors) {
                    if (constructor.getParameterCount() == data.length) {
                        matchingConstructor = (Constructor<T>) constructor;
                        break;
                    }
                }

                if (matchingConstructor != null) {
                    // Convert data array to Object array
                    Parameter[] parameterTypes = matchingConstructor.getParameters();
                    Object[] parameters = new Object[data.length];
                    System.arraycopy(data, 0, parameters, 0, data.length);

                    for (int i = 0; i < data.length; i++) {
                        parameters[i] = convertStringToType(data[i], parameterTypes[i].getType());
                    }

                    // Create a new instance using the matching constructor
                    T object = matchingConstructor.newInstance(parameters);
                    objects.put(data[0], object);
                }
            }

            reader.close();

        } catch (IOException | ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }

    private static Object convertStringToType(String value, Class<?> type) {
        if (type == String.class) {
            return value;
        } else if (type == int.class || type == Integer.class) {
            return Integer.parseInt(value);
        } else if (type == double.class || type == Double.class) {
            return Double.parseDouble(value);
        } else if (type == boolean.class || type == Boolean.class) {
            return Boolean.parseBoolean(value);

        } else if (type == Role.class) {
            return Role.valueOf(value);
        } else if (type == Status.class) {
            return Status.valueOf(value);

        } else if (type == Date.class) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
            try {
                return dateFormat.parse(value);
            } catch (ParseException e) {
                throw new IllegalArgumentException("Invalid date format: " + value, e);
            }
        }

        throw new IllegalArgumentException("Unsupported parameter type: " + type.getName());
    }
}
