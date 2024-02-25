package edu.java.supplier.api;

import java.net.URL;
import java.time.OffsetDateTime;

public record LinkInfo(URL url, String title, OffsetDateTime lastUpdate) {
}
