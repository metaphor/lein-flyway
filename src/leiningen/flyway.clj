(ns leiningen.flyway
  (:require [leiningen.core.eval :refer [eval-in-project]]))

(defn flyway [project subtask & args]
  (let [config (:flyway project)
        subtask-keyword (keyword subtask)]
    (eval-in-project
     (update-in project [:dependencies] conj ['lein-flyway "0.1.0-SNAPSHOT"])
     `(do
        (let [flyway# (flyway.flyway/flyway ~config)]
          (case ~subtask-keyword
            :clean (flyway.flyway/clean flyway#)
            :baseline (flyway.flyway/baseline flyway#)
            :validate (flyway.flyway/validate flyway#)
            :migrate (flyway.flyway/migrate flyway#)
            :info (flyway.flyway/info flyway#)
            :repair (flyway.flyway/repair flyway#))))
     '(require 'flyway.flyway))))


