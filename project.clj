(defproject lein-flyway "0.1.0-SNAPSHOT"
  :description "Leiningen Plugin for Flyway"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :eval-in :leiningen

  :dependencies [[org.flywaydb/flyway-core "3.1"]]

  :profiles {:dev {:dependencies [[mysql/mysql-connector-java "5.1.34"]]}}
  )
