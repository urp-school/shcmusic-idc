/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.ems.app;

import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.net.URL;
import java.util.*;

public class EmsApp {
  private static Map<String, String> driverClassNames = new HashMap<>();

  static {
    driverClassNames.put("oracle", "oracle.jdbc.OracleDriver");
    driverClassNames.put("postgresql", "org.postgresql.Driver");
    driverClassNames.put("mysql", "com.mysql.cj.jdbc.Driver");
    driverClassNames.put("h2", "org.h2.Driver");
    driverClassNames.put("jtds", "net.sourceforge.jtds.jdbc.Driver");
    driverClassNames.put("sqlserver", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
    driverClassNames.put("db2", "com.ibm.db2.jcc.DB2Driver");
  }

  public static EmsApp Instance = new EmsApp();

  public static String getName() {
    return Instance.name;
  }

  private String name;

  private Map<String, String> properties = new HashMap<>();

  public EmsApp() {
    super();
    readProperties();
  }

  public Map<String, String> getProperties() {
    return properties;
  }

  public void setProperties(Map<String, String> properties) {
    this.properties = properties;
  }

  public void setName(String name) {
    this.name = name;
  }

  private void readProperties() {
    List<URL> configs = getResources("META-INF/beangle/ems-app.properties");
    for (URL config : configs) {
      Properties p = new Properties();
      try {
        p.load(config.openStream());
      } catch (IOException e) {
        e.printStackTrace();
      }
      for (Object k : p.keySet()) {
        properties.put(k.toString(), p.getProperty(k.toString()));
      }
    }

    if (properties.containsKey("name")) {
      name = properties.get("name");
    } else {
      throw new RuntimeException("cannot find META-INF/beangle/ems-app.properties");
    }
    File appFile = new File(Ems.getInstance().getHome() + getPath() + ".xml");
    if (appFile.exists()) {
      properties.putAll(parseDataSource(appFile, "default"));
      properties.putAll(parseGraphql(appFile));
      properties.putAll(parseServer(appFile));
    }
  }

  public String getPath() {
    //app path starts with /
    String appPath = name.replace('-', '/');
    appPath = "/" + appPath.replace('.', '/');
    return appPath;
  }

  public String getSecret() {
    String secret = properties.get("secret");
    if (null == secret) return name;
    else return secret;
  }

  public static File getAppFile() {
    File file = new File(Ems.getInstance().getHome() + EmsApp.Instance.getPath() + ".xml");
    if (!file.exists()) {
      file = new File(Ems.getInstance().getHome() + EmsApp.Instance.getPath() + ".json");
    }
    return file;
  }

  public static Map<String, String> parseDataSource(File file, String dsname) {
    Map<String, String> properties = new HashMap<>();
    InputStream is = null;
    try {
      is = new FileInputStream(file);
      SAXReader reader = new SAXReader();
      Document document = reader.read(is);
      List nodes = document.selectNodes("/app/resources/datasource");
      Set<String> predefined = Set.of("user", "password", "driver", "props", "jdbcUrl");
      for (Object o : nodes) {
        if (o instanceof Node) {
          Node node = (Node) o;
          String name = node.valueOf("@name");
          if (name.equals(dsname)) {
            var user = node.selectSingleNode("user").getText();
            var password = node.selectSingleNode("password").getText();
            var url = node.selectSingleNode("jdbcUrl").getText();
            var driver = node.selectSingleNode("driver").getText();
            var maximumPoolSize = node.selectSingleNode("maximumPoolSize").getText();
            String prefix = "spring.datasource.";
            properties.put(prefix + "username", user);
            properties.put(prefix + "password", password);
            properties.put(prefix + "url", url);
            properties.put(prefix + "driver-class-name", driverClassNames.get(driver));
            if (!maximumPoolSize.isEmpty()) {
              properties.put(prefix + "hikari.maximum-pool-size", maximumPoolSize);
            }
            List propNodes = node.selectNodes("props/prop");
            for (Object po : propNodes) {
              Node pn = (Node) po;
              properties.put(prefix + pn.valueOf("@name"), pn.valueOf("@value"));
            }
            List children = node.selectNodes("*");
            for (Object o1 : children) {
              Node node1 = (Node) o1;
              if (!predefined.contains(node1.getName())) {
                properties.put(node1.getName(), node1.getText());
              }
            }
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      close(is);
    }
    return properties;
  }

  public static Map<String, String> parseServer(File file) {
    Map<String, String> properties = new HashMap<>();
    InputStream is = null;
    try {
      is = new FileInputStream(file);
      SAXReader reader = new SAXReader();
      Document document = reader.read(is);
      List servers = document.selectNodes("/app/server");
      for (Object s : servers) {
        Node ns = (Node) s;
        String port = ns.valueOf("@port");
        properties.put("server.port", port);
      }
    } catch (Throwable e) {
      e.printStackTrace();
    } finally {
      close(is);
      return properties;
    }
  }

  public static Map<String, String> parseGraphql(File file) {
    Map<String, String> properties = new HashMap<>();
    InputStream is = null;
    try {
      is = new FileInputStream(file);
      SAXReader reader = new SAXReader();
      Document document = reader.read(is);
      List graphql = document.selectNodes("/app/graphql");
      for (Object s : graphql) {
        Node ns = (Node) s;
        String client = ns.valueOf("@client");
        String secret = ns.valueOf("@secret");
        String path = ns.valueOf("@path");
        String graphiql = ns.valueOf("@graphiql");
        properties.put("graphql.client", client);
        properties.put("graphql.secret", secret);
        properties.put("spring.graphql.websocket.path", path);
        properties.put("spring.graphql.path", path);
        if (!graphiql.isEmpty()) {
          properties.put("spring.graphql.graphiql.enabled", String.valueOf(graphiql.equals("enabled")));
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      close(is);
      return properties;
    }
  }

  private static List<URL> getResources(String resourceName) {
    Enumeration<URL> em = null;
    try {
      em = Thread.currentThread().getContextClassLoader().getResources(resourceName);
    } catch (IOException e) {
      e.printStackTrace();
    }
    List<URL> urls = new ArrayList<>();
    while (null != em && em.hasMoreElements()) {
      urls.add(em.nextElement());
    }
    return urls;
  }

  private static void close(Closeable closeable) {
    try {
      if (closeable != null) {
        closeable.close();
      }
    } catch (IOException ioe) {
      // ignore
    }
  }
}
