(ns fcs.core-test
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.test.alpha :as st]
            [cljs.test :refer-macros [is are deftest testing use-fixtures]]
            [clojure.test.check.clojure-test :refer-macros [defspec]]
            [clojure.test.check.properties :as prop]
            [fcs.core :as rc]
            [fcs.validation]
            [pjstadig.humane-test-output]
            [re-frame.core :as rf]
            [reagent.core :as reagent :refer [atom]]))

(defn fixture-re-frame []
  (let [restore-re-frame (atom nil)]
    {:before #(reset! restore-re-frame (rf/make-restore-fn))
     :after  #(@restore-re-frame)}))

(defn fixture-spec []
  {:before #(st/instrument)})

(use-fixtures :each (fixture-re-frame))
(use-fixtures :once (fixture-spec))

(deftest test-home
  (testing "sanity"
    (is (= true true))))

(defspec test-content
  1000
  (prop/for-all [content (s/gen :home-page/content)]
                ;; Testing sanity some more...
                (s/valid? :home-page/content
                          content)))
