(ns leiningen.flyway-migrate
  (:require [flyway.flyway :refer [flyway]]
            [leiningen.core.eval :refer [eval-in-project]]))

(defn flyway-migrate [project]
  (let [config (:flyway project)]
    (-> (flyway config) .migrate)))


