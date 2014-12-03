(ns leiningen.flyway-repair
  (:require [flyway.flyway :refer [flyway]]))

(defn flyway-repair [project]
  (let [config (:flyway project)]
    (-> (flyway config) .repair)))


