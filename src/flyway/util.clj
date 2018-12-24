(ns flyway.util
  (:require [clojure.string :as str]))

(defn ^String get-file-extension [^String filename]
  (subs filename (inc (str/last-index-of filename "."))))
