(ns leiningen.flyway-validate
  (:require [flyway.flyway :refer [flyway]]))

(defn flyway-validate [project]
  (let [config (:flyway project)]
    (-> (flyway config) .validate)))


