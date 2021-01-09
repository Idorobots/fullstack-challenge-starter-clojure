(ns fcs.core
  (:require
   [day8.re-frame.http-fx]
   [reagent.dom :as rdom]
   [re-frame.core :as rf]
   [fcs.ajax :as ajax]
   [fcs.events]
   [fcs.views :as views]
   [reitit.core :as reitit]
   [reitit.frontend.easy :as rfe]))

(defn navigate! [match _]
  (rf/dispatch [:common/navigate match]))

(def router
  (reitit/router
   [["/" {:name        :home
          :view        #'views/home-page
          :controllers [{:start (fn [_] (rf/dispatch [:page/init-home]))}]}]
    ["/about" {:name :about
               :view #'views/about-page}]
    ["/error" {:name :error
               :view #'views/error-page}]]))

(defn start-router! []
  (rfe/start!
   router
   navigate!
   {}))

(defn mount-components []
  (rf/clear-subscription-cache!)
  (rdom/render [#'views/page] (.getElementById js/document "app")))

(defn init! []
  (start-router!)
  (ajax/load-interceptors!)
  (mount-components))
