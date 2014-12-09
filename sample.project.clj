(defproject sample "0.1.0-SNAPSHOT"
  :description "sample"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.7.0-alpha3"]
                 [mysql/mysql-connector-java "5.1.34"]]

  ;; Usually you need put your migrations in resource classpath
  :resource-paths ["src-resources"]

  :plugins [[lein-flyway "0.1.0-SNAPSHOT"]]

  ;; Flyway Database Migration configuration
  :flyway {
           ;; Database connection
           :driver "com.mysql.jdbc.Driver"
           :url "jdbc:mysql://localhost/happyzoo"
           :username "root"
           :password ""

           ;; Migration locations
           :locations ["db/other/location"]

           ;; Baseline
           :baseline-on-migrate false
           :baseline-version 0
           :baseline-description "Sample Migration"}
  )
