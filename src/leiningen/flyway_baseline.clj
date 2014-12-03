(ns leiningen.flyway-baseline
  (:require [flyway.flyway :refer [flyway]]))

(defn flyway-baseline [project]
  (let [config (:flyway project)]
    (-> (flyway config) .baseline)))


