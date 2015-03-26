(defproject com.github.metaphor/lein-flyway "1.1-SNAPSHOT"
  :description "Leiningen Plugin for Database Migration Tool Flyway"
  :url "https://github.com/metaphor/lein-flyway"
  :license {:name "Eclipse Public License - v 1.0"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :min-lein-version "2.0.0"
  :eval-in :leiningen

  :dependencies [[org.flywaydb/flyway-core "3.2.1"]]

  :profiles {:dev {:dependencies [[mysql/mysql-connector-java "5.1.34"]]}}

  :plugins [[cider/cider-nrepl "0.9.0-SNAPSHOT"]]


  :scm {:url "git@github.com:metaphor/lein-flyway.git"}
  :pom-addition [:developers [:developer
                              [:name "Zhiheng Li"]
                              [:url "https://github.com/metaphor"]
                              [:email "zhiheng.li.metaphor@gmail.com"]
                              [:timezone "+8"]]])
