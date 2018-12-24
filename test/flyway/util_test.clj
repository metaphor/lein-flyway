(ns flyway.util-test
  (:require [clojure.test :as t :refer [deftest is are]]
            [flyway.util :as sut]))

(deftest get-file-extension
  (is (= "edn" (sut/get-file-extension "/opt/config.edn")))
  (is (= "properties" (sut/get-file-extension "/opt/config.properties")))
  (is (= "conf" (sut/get-file-extension "/opt/config.conf"))))
