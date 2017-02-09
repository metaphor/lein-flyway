(ns flyway.flyway
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [clojure.string :as str])
  (:import [java.io File PushbackReader StringReader]
           [java.util Properties]
           [org.apache.commons.io FilenameUtils]
           [org.flywaydb.core Flyway]
           [org.flywaydb.core.internal.util FileCopyUtils]
           [org.flywaydb.core.internal.util.jdbc DriverDataSource]
           [org.flywaydb.core.internal.info MigrationInfoDumper]))

(def ^{:private true
       :doc     "Refer to documentation for defaults. https://flywaydb.org/documentation/commandline/migrate"}
  supported-config-keys [:url
                         :driver
                         :user
                         :password
                         :schemas
                         :table
                         :locations
                         :jar-dirs
                         :sql-migration-prefix
                         :repeatable-sql-migration-prefix
                         :sql-migration-separator
                         :sql-migration-suffix
                         :encoding
                         :placeholder-replacement
                         :placeholders
                         :placeholder-prefix
                         :placeholder-suffix
                         :resolvers
                         :skip-default-resolvers
                         :callbacks
                         :skip-default-callbacks
                         :target
                         :out-of-order
                         :validate-on-migrate
                         :clean-on-validation-error
                         :ignore-future-migrations
                         :clean-disabled
                         :baseline-on-migrate
                         :baseline-version
                         :baseline-description])

(defn get-edn-config
  "Returns the value at `:flyway` in an edn map."
  [config-path]
  (if (.exists (io/file config-path))
    (with-open [in-file (PushbackReader. (io/reader config-path))]
      (:flyway (edn/read in-file)))
    (println "Config path does not exist:" config-path)))

(defn get-conf-config
  [config-path]
  (if (.exists (io/file config-path))
    (when-let [contents (try (FileCopyUtils/copyToString (io/reader (io/input-stream config-path)))
                            (catch Exception e
                              (println (.getMessage e))))]
     (let [props (Properties.)
           contents (str/replace contents "\\" "\\\\")]
       (try (.load props (StringReader. contents))
            props
            (catch Exception e
              (println (.getMessage e))))))
    (println "Config path does not exist:" config-path)))

(defn make-flyway-prop
  "Given a keyword, create a property for flyway to use in a `java.util.Properties` object."
  [k]
  (let [fkey (str/split (name k) #"[-_]")]
    (str/join "" (cons "flyway."
                       (cons (first fkey)
                             (map str/capitalize (rest fkey)))))))

(defn ^Properties make-properties
  [config-map]
  (let [refined-keys (select-keys config-map supported-config-keys)]
    (doto (Properties.)
      (#(doseq [[k v] refined-keys]
          (when v
            (cond
              (vector? v) (.setProperty % (make-flyway-prop k) (str/join "," v))
              (map? v)    (doseq [[k2 v2] v]
                            (.setProperty % (str (make-flyway-prop k) "." (name k2)) (str v2)))
              :else       (.setProperty % (make-flyway-prop k) (str v)))))))))

(defn ^Properties make-config
  "Given a config, if the config contains a `:config-path`, get the config from that path.
  The file can either be an edn file (config.edn) or a properties file defined in the flyway library (flyway.conf).
  When a edn file is found, there should be the key `:flyway` that has a map of the required properties.
  When a properties file exists, the properties should follow as seen in the flyway documentation."
  [config-map]
  (let [config-path (:config-path config-map)
        ext         (FilenameUtils/getExtension config-path)
        config      (cond
                      (= "edn" ext)             (get-edn-config config-path)
                      (or (= "conf" ext)
                          (= "properties" ext)) (get-conf-config config-path)
                      :else                     config-map)]
    (if (map? config)
      (make-properties config)
      config)))

(defn ^Flyway flyway [config]
  (let [props (make-config config)]
    (doto (Flyway.)
      (.configure props))))

(defn clean [^Flyway flyway]
  (. flyway clean))

(defn baseline [^Flyway flyway]
  (. flyway baseline))

(defn validate [^Flyway flyway]
  (. flyway validate))

(defn repair [^Flyway flyway]
  (. flyway repair))

(defn migrate [^Flyway flyway]
  (. flyway migrate))

(defn info [^Flyway flyway]
  (println (MigrationInfoDumper/dumpToAsciiTable (.. flyway info all))))
