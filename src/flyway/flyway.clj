(ns flyway.flyway
  (:import [org.flywaydb.core Flyway]
           [org.flywaydb.core.internal.util.jdbc DriverDataSource]
           [org.flywaydb.core.internal.info MigrationInfoDumper]))

(defn- contextClassLoader []
  (.getContextClassLoader (Thread/currentThread)))

(defn- dataSource [config]
  (let [{:keys [driver url username password]} config]
    (DriverDataSource. (contextClassLoader)
                       driver
                       url
                       username
                       password
                       (make-array java.lang.String 0))))

(defn- set-locations [flyway config]
  (if-let [locations (:locations config)]
    (. flyway setLocations (into-array locations))))

(defn flyway [config]
  (let [f (Flyway.)]
    (do
      (. f (setDataSource (dataSource config)))
      (set-locations f config))
    f))

(defn clean [flyway]
  (. flyway clean))

(defn baseline [flyway]
  (. flyway baseline))

(defn validate [flyway]
  (. flyway validate))

(defn repair [flyway]
  (. flyway repair))

(defn migrate [flyway]
  (. flyway migrate))

(defn info [flyway]
  (println (MigrationInfoDumper/dumpToAsciiTable (.. flyway info all))))

