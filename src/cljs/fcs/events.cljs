(ns fcs.events
  (:require
   [ajax.core :as ajax]
   [re-frame.core :as rf]
   [reitit.frontend.easy :as rfe]
   [reitit.frontend.controllers :as rfc]))

;;dispatchers

(rf/reg-event-db
 :common/navigate
 (fn [db [_ match]]
   (let [old-match (:common/route db)
         new-match (assoc match :controllers
                          (rfc/apply-controllers (:controllers old-match) match))]
     (assoc db :common/route new-match))))

(rf/reg-fx
 :common/navigate-fx!
 (fn [[k & [params query]]]
   (rfe/push-state k params query)))

(rf/reg-event-fx
 :common/navigate!
 (fn [_ [_ url-key params query]]
   {:common/navigate-fx! [url-key params query]}))

(rf/reg-event-fx
 :common/error
 (fn [{:keys [db]} [_ error]]
   {:db (assoc db :common/error error)
    :common/navigate-fx! [:error]}))

(rf/reg-event-db
 :home-page/set-content
 (fn [db [_ content]]
   (assoc db :home-page/content content)))

(rf/reg-event-fx
 :home-page/fetch-status
 (fn [_ _]
   {:http-xhrio {:method          :get
                 :uri             "/status"
                 :response-format (ajax/raw-response-format)
                 :on-success       [:home-page/set-content]
                 :on-failure       [:common/error]}}))

(rf/reg-event-db
 :common/set-error
 (fn [db [_ error]]
   (assoc db :common/error error)))

(rf/reg-event-fx
 :home-page/init
 (fn [_ _]
   {:dispatch [:home-page/fetch-status]}))
