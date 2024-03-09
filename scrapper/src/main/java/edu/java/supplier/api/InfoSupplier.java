package edu.java.supplier.api;

import java.net.URL;
import java.util.regex.Pattern;

public interface InfoSupplier {

    LinkInfo fetchInfo(URL url);

    Pattern supportedPattern(URL link);
}
