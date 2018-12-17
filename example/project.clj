(defproject sample "0.1.0-SNAPSHOT"
  :description "sample"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.9.0"]]

  :profiles {:dev {:dependencies [[mysql/mysql-connector-java "5.1.36"]]}}

  ;; Usually you need put your migrations in resource classpath
  :resource-paths ["src-resources"]

  :plugins [[com.github.metaphor/lein-flyway "6.0.0-SNAPSHOT"]]

  ;; Flyway Database Migration configuration
  :flyway {:driver "com.mysql.jdbc.Driver"
           :url "jdbc:mysql://127.0.0.1:8806/leinflyway"
           :user "root"
           :password "donotuseroot"})
