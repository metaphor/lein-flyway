(ns flyway.flyway-test
  (:require [clojure.string :as str]
            [clojure.test :as t :refer [deftest is are]]
            [flyway.flyway :as sut])
  (:import java.util.Properties))

(def edn-file "test/config/config.edn")
(def conf-file "test/config/flyway.conf")

(deftest get-edn-config
  (is (not (nil?
            (sut/get-edn-config edn-file))))
  (is (= "Config path does not exist: \n"
         (with-out-str (sut/get-edn-config "")))))

(deftest get-conf-config
  (is (not (nil?
            (sut/get-conf-config conf-file))))
  (is (= "Config path does not exist: \n"
         (with-out-str (sut/get-conf-config "")))))

(deftest make-flyway-prop
  (is (= "flyway.test" (sut/make-flyway-prop :test)))
  (is (= "flyway.testThisThing" (sut/make-flyway-prop :test-this-thing)))
  (is (= "flyway.testThisThing" (sut/make-flyway-prop :test_this_thing)))
  (is (= "flyway.testThisThing" (sut/make-flyway-prop :testThisThing))))

(def config-map {:user                      "root"
                 :locations                 ["classpath:/migration1" "migrations2" "filesystem:/sql-migrations"]
                 :encoding                  "UTF-8"
                 :placeholders              {"token1" "token1-value"
                                             :token2 "token2-value"}

                 ;; invalid key
                 :improper-key              true})
(def ^Properties properties (sut/make-properties config-map))

(deftest make-properties
  (is (instance? Properties properties))
  (is (not (.getProperty properties "flyway.improperKey")))
  ;; the placeholders map will create 2 keys.
  (is (= 5 (count properties)))
  (is (= (:user config-map) (.getProperty properties "flyway.user")))
  (is (= (:encoding config-map) (.getProperty properties "flyway.encoding")))
  (is (= (str/join "," (:locations config-map)) (.getProperty properties "flyway.locations")))
  (is (= (get-in config-map [:placeholders "token1"]) (.getProperty properties "flyway.placeholders.token1")))
  (is (= (get-in config-map [:placeholders :token2]) (.getProperty properties "flyway.placeholders.token2"))))
