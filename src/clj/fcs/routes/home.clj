(ns fcs.routes.home
  (:require
   [fcs.layout :as layout]
   [clojure.java.io :as io]
   [fcs.middleware :as middleware]
   [ring.util.response]
   [ring.util.http-response :as response]))

(defn home-page [request]
  (layout/render request "home.html"))

(defn home-routes []
  [""
   {:middleware [middleware/wrap-csrf
                 middleware/wrap-formats]}
   ["/" {:get home-page}]
   ["/status" {:get (fn [_]
                    (-> (response/ok "# up and runnin'!")
                        (response/header "Content-Type" "text/plain; charset=utf-8")))}]])

