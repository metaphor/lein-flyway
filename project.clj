(defproject com.github.metaphor/lein-flyway "3.1-SNAPSHOT"
  :description "Leiningen Plugin for Database Migration Tool Flyway"
  :url "https://github.com/metaphor/lein-flyway"
  :min-lein-version "2.0.0"
  :eval-in :leiningen

  :dependencies [[org.flywaydb/flyway-core "3.1"]]

  :profiles {:dev {:dependencies [[mysql/mysql-connector-java "5.1.34"]]}})
