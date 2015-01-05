(ns flyway.flyway
  (:require [clojure.string :as str])
  (:import [org.flywaydb.core Flyway]
           [org.flywaydb.core.internal.util.jdbc DriverDataSource]
           [org.flywaydb.core.internal.info MigrationInfoDumper]))

(def ^:private supported-config-keys [:baseline-version
                                      :baseline-description
                                      :locations
                                      :table
                                      :schemas
                                      :sql-migration-prefix
                                      :sql-migration-separator
                                      :sql-migration-suffix
                                      :encoding
                                      :placeholders
                                      :placeholder-prefix
                                      :placeholder-suffix
                                      :resolvers
                                      :callbacks
                                      :target
                                      :out-of-order
                                      :validate-on-migrate
                                      :clean-on-validation-error
                                      :baseline-on-migrate])

(defn- contextClassLoader []
  (.getContextClassLoader (Thread/currentThread)))

(defn- dataSource [config]
  (let [{:keys [driver url user password]} config]
    (DriverDataSource. (contextClassLoader) driver url user password (make-array java.lang.String 0))))

(defn- to-setter [key]
  (str/join (cons "set" (map str/capitalize (str/split (name key) #"-")))))

(defn- invoke-setter [fw key & args]
  (clojure.lang.Reflector/invokeInstanceMember (to-setter key) fw (into-array args)))

(defn- set-prop [fw key value]
  (when-let [the-value value]
    (cond
     (vector? the-value) (invoke-setter fw key (into-array the-value))
     :else (invoke-setter fw key the-value))))

(defn flyway [config]
  (let [f (Flyway.)]
    (do
      (. f (setDataSource (dataSource config)))
      (doseq [config-key supported-config-keys]
        (set-prop f config-key (config-key config))))
    (identity f)))

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
