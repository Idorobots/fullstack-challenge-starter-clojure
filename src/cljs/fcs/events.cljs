(ns fcs.events
  (:require
   [re-frame.core :as rf]
   [ajax.core :as ajax]
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
 :set-content
 (fn [db [_ content]]
   (assoc db :page-content content)))

(rf/reg-event-fx
 :fetch-status
 (fn [_ _]
   {:http-xhrio {:method          :get
                 :uri             "/status"
                 :response-format (ajax/raw-response-format)
                 :on-success       [:set-content]
                 :on-failure       [:common/error]}}))

(rf/reg-event-db
 :common/set-error
 (fn [db [_ error]]
   (assoc db :common/error error)))

(rf/reg-event-fx
 :page/init-home
 (fn [_ _]
   {:dispatch [:fetch-status]}))

;;subscriptions

(rf/reg-sub
 :common/route
 (fn [db _]
   (-> db :common/route)))

(rf/reg-sub
 :common/page
 :<- [:common/route]
 (fn [route _]
   (-> route :data :view)))

(rf/reg-sub
 :page-content
 (fn [db _]
   (:page-content db)))

(rf/reg-sub
 :common/error
 (fn [db _]
   (-> db :common/error :response)))
