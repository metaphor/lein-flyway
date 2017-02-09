(defproject sample "0.1.0-SNAPSHOT"
  :description "sample"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.8.0"]]

  :profiles {:dev {:dependencies [[mysql/mysql-connector-java "5.1.36"]]}}

  ;; Usually you need put your migrations in resource classpath
  :resource-paths ["src-resources"]

  :plugins [[com.github.metaphor/lein-flyway "4.0.1"]]

  ;; Flyway Database Migration configuration
  :flyway {;; Optional - When a config path is passed, that config will be used in place of the below options.
           :config-path "flyway.properties"
           ;; Database connection
           :driver "com.mysql.jdbc.Driver"
           :url "jdbc:mysql://localhost/happyzoo"
           :user "root"
           :password ""

           :schemas ["schema1" "schema2"]
           ;; Migration locations
           :locations ["classpath:/migration1" "migrations2" "filesystem:/sql-migrations"]

           ;; Baseline
           :baseline-on-migrate true
           :baseline-version 5
           :baseline-description "Sample Migration"

           :table "my_schema_versions"

           :sql-migration-prefix "V"
           :sql-migration-separator "__"
           :sql-migration-suffix ".sql"
           :encoding "UTF-8"
           :placeholders {"token1" "token1-value"
                          "token2" "token2-value"}
           :placeholder-replacement false
           :placeholder-prefix "${"
           :placeholder-suffix  "}"
           :resolvers  ["com.mycompany.proj.CustomResolver" "com.mycompany.proj.AnotherResolver"]
           :callbacks ["com.mycompany.proj.CustomCallback" "com.mycompany.proj.AnotherCallback"]
           :target "1.1"
           :out-of-order false
           :validate-on-migrate true
           :clean-on-validation-error false})
