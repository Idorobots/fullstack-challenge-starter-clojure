(ns fcs.validation
  (:require [clojure.spec.alpha :as s]))

(s/def :common/error map?)
(s/def :common/route map?)
(s/def :home-page/content string?)

(s/def ::app-db (s/keys :opt [:common/route :common/error :home-page/content]))
