(ns leiningen.flyway-clean
  (:require [flyway.flyway :refer [flyway]]))

(defn flyway-clean [project]
  (let [config (:flyway project)]
    (-> (flyway config) .clean)))


