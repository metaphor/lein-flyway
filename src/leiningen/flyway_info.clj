(ns leiningen.flyway-info
  (:require [flyway.flyway :refer [flyway dump-info]]))

(defn flyway-info [project]
  (let [config (:flyway project)]
    (println (dump-info (flyway config)))))


