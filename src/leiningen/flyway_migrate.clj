(ns leiningen.flyway-migrate
  (:require [leiningen.core.eval :refer [eval-in-project]]))

(defn flyway-migrate [project]
  (let [config (:flyway project)]
    (eval-in-project
     (update-in project [:dependencies] conj ['lein-flyway "0.1.0-SNAPSHOT"])
     `(do
        (-> (flyway.flyway/flyway ~config) .migrate))
     '(require 'flyway.flyway))))


