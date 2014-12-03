(ns flyway.flyway
  (:import [org.flywaydb.core Flyway]
           [org.flywaydb.core.internal.util.jdbc DriverDataSource]
           [org.flywaydb.core.internal.info MigrationInfoDumper]))

(defn- dataSource [config]
  (let [{:keys [driver url username password]} config
        clazzLoader (.getContextClassLoader (Thread/currentThread))]
    (DriverDataSource. clazzLoader driver url username password (make-array java.lang.String 0))))

(defn flyway [config]
  (let [f (Flyway.)]
    (.. f (setDataSource (dataSource config)))
    f))

(defn dump-info [flyway]
  (-> flyway .info .all MigrationInfoDumper/dumpToAsciiTable))
