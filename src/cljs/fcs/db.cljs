(ns fcs.db
  (:require
   [clojure.spec.alpha :as s]
   [expound.alpha :as e]
   [re-frame.interceptor :as rfi]
   [re-frame.core :as rf]))

;; validation

(defn log-spec-error [new-db spec]
  (rf/console :group "*** Spec error when updating DB, rolling back ***")
  (e/expound spec new-db)
  (rf/console :groupEnd "*****************************"))

(defn rollback [context new-db db-spec]
  (do
    (log-spec-error new-db db-spec)
    (rfi/assoc-effect context :db (rfi/get-coeffect context :db))))

(defn validate [db-spec]
  (rfi/->interceptor
    :id :spec
    :after (fn [context]
             (let [new-db (rfi/get-effect context :db)]
               (if (and new-db (not (s/valid? db-spec new-db)))
                 (rollback context new-db db-spec)
                 context)))))

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
 :common/page-id
 :<- [:common/route]
 (fn [route _]
   (-> route :data :name)))

(rf/reg-sub
 :common/error
 (fn [db _]
   (-> db :common/error :response)))

(rf/reg-sub
 :home-page/content
 (fn [db _]
   (:home-page/content db)))
