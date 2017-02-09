(defproject com.github.metaphor/lein-flyway "4.0.1"
  :description "Leiningen Plugin for Database Migration Tool Flyway"
  :url "https://github.com/metaphor/lein-flyway"
  :license {:name "Eclipse Public License - v 1.0"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :min-lein-version "2.0.0"
  :eval-in :leiningen

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.flywaydb/flyway-core "4.0.3"]]

  :profiles {:dev {:dependencies [[org.clojure/clojure "1.8.0"]
                                  [mysql/mysql-connector-java "5.1.36"]]
                   :plugins      [[com.jakemccrary/lein-test-refresh "0.15.0"]]
                   }}

  :test-refresh {:changes-only true}

  :scm {:url "git@github.com:metaphor/lein-flyway.git"}
  :pom-addition [:developers [:developer
                              [:name "Zhiheng Li"]
                              [:url "https://github.com/metaphor"]
                              [:email "zhiheng.li.metaphor@gmail.com"]
                              [:timezone "+8"]]])
