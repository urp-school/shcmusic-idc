package net.openurp.shcmusic;

import org.beangle.ems.app.Ems;
import org.beangle.ems.app.EmsApp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;
import java.util.Set;

@SpringBootApplication
public class GraphqlApplication {
  public static void main(String[] args) {
    populateProperties();
    SpringApplication.run(GraphqlApplication.class, args);
  }

  private static void populateProperties() {
    Ems ems = Ems.Instance;
    EmsApp emsApp = EmsApp.Instance;
    Set<String> prefixes = Set.of("spring", "server", "graphql");
    for (Map.Entry<String, String> entry : emsApp.getProperties().entrySet()) {
      var key = entry.getKey();
      var idxDot = key.indexOf('.');
      if (idxDot > 0) {
        var p = key.substring(0, idxDot);
        if (prefixes.contains(p)) {
          System.setProperty(key, entry.getValue());
        }
      }
    }
  }
}
