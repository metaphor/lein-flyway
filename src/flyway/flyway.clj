(ns flyway.flyway
  (:require [clojure.string :as str])
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

(defn to-setter [key]
  (str/join (cons "set" (map str/capitalize (str/split (name key) #"-")))))

(defn invoke-setter [fw key & args]
  (clojure.lang.Reflector/invokeInstanceMethod fw (to-setter key) (to-array args)))

(defn set-prop [fw key config]
  (when-let [value (key config)]
    (invoke-setter fw key value)))

(defn- set-locations [flyway config]
  (if-let [locations (:locations config)]
    (. flyway setLocations (into-array locations))))

(defn flyway [config]
  (let [f (Flyway.)]
    (do
      (. f (setDataSource (dataSource config)))
      (set-locations f config)
      (set-prop f :baseline-version config)
      (set-prop f :baseline-description config)
      (set-prop f :baseline-on-migrate config))
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





