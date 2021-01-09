(ns fcs.core-test
  (:require [cljs.test :refer-macros [is are deftest testing use-fixtures]]
            [pjstadig.humane-test-output]
            [re-frame.core :as rf]
            [reagent.core :as reagent :refer [atom]]
            [fcs.core :as rc]))

(defn fixture-re-frame []
  (let [restore-re-frame (atom nil)]
    {:before #(reset! restore-re-frame (rf/make-restore-fn))
     :after  #(@restore-re-frame)}))

(use-fixtures :each (fixture-re-frame))

(deftest test-home
  (testing "sanity"
    (println "test")
    (is (= true true))))
