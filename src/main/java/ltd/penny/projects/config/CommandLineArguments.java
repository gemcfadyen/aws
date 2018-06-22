package ltd.penny.projects.config;

import java.util.Arrays;

public class CommandLineArguments {

    public String getPathToConfig(String[] args) {
        if (parameterKeyFound("-c", args)) {
            try {
                return args[getPositionOfKey(args, "-c") + 1];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new MissingCommandLineArgumentException("Full path to the config file required");
            }
        } else {
            throw new MissingCommandLineArgumentException("Full path to the config file required");
        }
    }

    private int getPositionOfKey(String[] args, String s) {
        return Arrays.asList(args).indexOf(s);
    }

    private boolean parameterKeyFound(String key, String[] args) {
        return getPositionOfKey(args, key) != -1;
    }
}
