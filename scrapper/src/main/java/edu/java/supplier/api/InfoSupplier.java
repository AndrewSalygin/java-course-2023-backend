package edu.java.supplier.api;

import java.net.URL;

public interface InfoSupplier {

    LinkInfo fetchInfo(URL url);

    boolean isSupported(URL url);

    String getTypeSupplier();
}
