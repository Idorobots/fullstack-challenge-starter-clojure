(ns fcs.handler-test
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.test.alpha :as st]
            [clojure.test.check.clojure-test :refer [defspec]]
            [clojure.test.check.properties :as prop]
            [clojure.test :refer :all]
            [fcs.config :as config]
            [fcs.handler :as handler]
            [fcs.middleware.formats :as formats]
            [fcs.validation]
            [muuntaja.core :as m]
            [mount.core :as mount]
            [ring.mock.request :as req]))

(defn fixture-mount []
  (fn [f]
    (mount/start #'config/env
                 #'handler/app-routes)
    (f)))

(defn fixture-spec []
  (fn [f]
    (st/instrument)
    (f)))

(use-fixtures :once (fixture-mount) (fixture-spec))

(defn parse-json [body]
  (m/decode formats/instance "application/json" body))

(deftest test-app
  (testing "main route"
    (let [response ((handler/app) (req/request :get "/"))]
      (is (= 200 (:status response)))))

  (testing "not-found route"
    (let [response ((handler/app) (req/request :get "/invalid"))]
      (is (= 404 (:status response))))))

(defspec test-content
  1000
  (prop/for-all [content (s/gen :home-page/content)]
                ;; Testing sanity some more...
                (s/valid? :home-page/content
                          content)))
